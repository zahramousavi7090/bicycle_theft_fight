package com.bicyle_theft.demo.police;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "polices")
public class Police {
    @Id
    @Column(name = "id")
    private final UUID id;
    @Column(name = "name")
    private String name;
    @Column(name="status")
    private String status;
    @Column(name = "isDeleted")
    private boolean isDeleted;

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
    @Override
    public String toString() {
        return "Police{" + id +
                ", " + name +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
