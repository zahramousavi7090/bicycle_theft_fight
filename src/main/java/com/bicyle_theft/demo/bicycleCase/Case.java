package com.bicyle_theft.demo.bicycleCase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name="Cases")
public class Case {

    @Id
    @Column(name="id")
    private final UUID id;
    @Column(name = "name")
    private String name;
    @Column(name="status")
    private String status;
    @Column(name="isDeleted")
    private boolean isDeleted;

    public Case() {
        this.id=UUID.randomUUID();
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


    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Case{" + id +
                "," + name  +
                " }";
    }
}
