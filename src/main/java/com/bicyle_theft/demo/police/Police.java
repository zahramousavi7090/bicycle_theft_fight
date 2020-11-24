package com.bicyle_theft.demo.police;

import com.bicyle_theft.demo.bicycleCase.Case;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.Hidden;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "polices")
public class Police {
    @Id
    @Column(name = "id")
    private final UUID id;
    @NotBlank
    @Column(name = "name")
    private String name;
    @Column(name="status")
    private String status;
    @Column(name = "isDeleted")
    private boolean isDeleted;

    @Hidden
    @JsonManagedReference
    @OneToMany(mappedBy = "police")
    private Set<Case> cases;


    public Police() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Set<Case> getCases() {
        return cases;
    }

    public void setCases(Set<Case> cases) {
        this.cases = cases;
    }


    @Override
    public String toString() {
        return "Police{" + id +
                ", " + name +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
