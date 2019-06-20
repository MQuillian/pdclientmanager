package com.pdclientmanager.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;

@Entity
public class Charge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotEmpty(message = "Name")
    private String name;
    
    @NotEmpty(message = "Statute")
    private String statute;
    
    @ManyToMany(mappedBy = "chargedCounts")
    Set<Case> cases;
    
    public Charge() {
        
    }

    public Charge(Long id, String name, String statute) {
        this.id = id;
        this.name = name;
        this.statute = statute;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatute() {
        return statute;
    }

    public void setStatute(String statute) {
        this.statute = statute;
    }

    @Override
    public String toString() {
        return "Charge [id=" + id + ", name=" + name + ", statute=" + statute + "]";
    }
}
