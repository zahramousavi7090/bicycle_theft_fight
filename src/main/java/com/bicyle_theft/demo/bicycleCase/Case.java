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


    public boolean isDeleted() {
        return isDeleted;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public String toString() {
        return "Case{" + id +
                "," + name  +
                " }";
    }
}
