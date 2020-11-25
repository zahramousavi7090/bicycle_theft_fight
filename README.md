# bicycle theft fight

bicycle theft fight allows you to launch an API that Distributes and manages reported bicycle theft cases among police officers.


## Prerequisites
- Java version 8
- maven 3.2.0
- spring framework version 2.4.0
- H2 database: H2 is in-memory database and don't need any spesific configuration.

## API documentation
I use swagger for API documentation  
http://localhost:8080/swagger-ui-custom.html

## How Does bicycle theft fight Work? 
I use two queue for this. one of them is queue of polices and another is queue of cases. open cases are in 'caseQueue' and free polices are in 'policeQueue'.  
As soon as a new case or an open case enters the queue, if a police officer is in queue then the case will be assigned to the police,
and the status of the case and the police will be changed to 'in progress' and 'engage' respectively.  
when the police finished the Case, status of the case chaned to 'close' and polic will be free and go to the policeQueue to assing to another Case.
## example of use
follow these steps for allocate a case to a police.
- 1 create the case  
API: http://localhost:8080/cases  
Method: POST  
body:
```json
{
  "name": "case1",
  "status": "open",
  "deleted": false
}
```


- 2 create the police  
API: http://localhost:8080/polices  
Method: POST  
body:
```json
{
  "name": "police1",
  "status": "free",
  "deleted": false
}
```
- 3 algorithm allocate case1 to police1 in background. and change case status to 'inProgress' and change police status to 'engage'.
- 4 see status of case  
API: http://localhost:8080/cases/find-cases-by-status  
method: GET  
query string: status=inProgress  
resault :
```json
[
  {
    "id": "15ed4ba1-6af8-450d-b566-39b98dc4647e",
    "name": "case1",
    "status": "inProgress",
    "deleted": false
  }
]
```
Note : Id of cases is universally unique identifier(UUID)
- 5 see status of police  
API: http://localhost:8080/polices/find-polices-by-status  
method: GET  
query string: status=engage  
resault:
```json
[
  {
    "id": "9e4ef436-29a4-41ac-9ebf-014f41888202",
    "name": "police1",
    "status": "engage",
    "cases": [
      {
        "id": "15ed4ba1-6af8-450d-b566-39b98dc4647e",
        "name": "case1",
        "status": "inProgress",
        "deleted": false
      }
    ],
    "deleted": false
  }
]
```
- 6 when case1 finished , you can change case's status to "close"  
API: http://localhost:8080/cases/close-case  
method: PATCH  
body:
```json
{
  "caseId": "15ed4ba1-6af8-450d-b566-39b98dc4647e"
  "policeId": "9e4ef436-29a4-41ac-9ebf-014f41888202"
}
```
then you can see status of case1 with it's id  
API: http://localhost:8080/cases/{id}  
method: GET  
path variable {id}: 15ed4ba1-6af8-450d-b566-39b98dc4647e  
resault :
```json
{
  "id": "15ed4ba1-6af8-450d-b566-39b98dc4647e",
  "name": "case1",
  "status": "close",
  "deleted": false
}
```
- 7 with do step 6 work changes status of police1 to "free" and goes to 'policeQueue'  
to see state of police1  
API: http://localhost:8080/polices/{id}  
method: GET  
path variable {id}: 9e4ef436-29a4-41ac-9ebf-014f41888202  
resault:
```json
{
  "id": "9e4ef436-29a4-41ac-9ebf-014f41888202",
  "name": "police1",
  "status": "free",
  "cases": [
    {
      "id": "15ed4ba1-6af8-450d-b566-39b98dc4647e",
      "name": "case1",
      "status": "close",
      "deleted": false
    }
  ],
  "deleted": false
}
```
 you can see status of police1 is "free" and it can get another case, if exist.
 
 
