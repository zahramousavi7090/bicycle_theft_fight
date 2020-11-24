package com.bicyle_theft.demo.bicycleCase;

import com.bicyle_theft.demo.police.Police;
import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.Hidden;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Entity
@Table(name="Cases")
public class Case {

    @Id
    @Column(name="id")
    private final UUID id;
    @NotBlank
    @Column(name = "name")
    private String name;
    @Column(name="status")
    private String status;
    @Column(name="isDeleted")
    private boolean isDeleted;

    @Hidden
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "police_id",nullable = true)
    private Police police;


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

    public Police getPolice() {
        return police;
    }

    public void setPolice(Police police) {
        this.police = police;
    }

    @Override
    public String toString() {
        return "Case{" + id +
                "," + name  +
                " }";
    }
}
