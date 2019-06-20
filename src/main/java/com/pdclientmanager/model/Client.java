package com.pdclientmanager.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Client {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotEmpty(message = "Name")
    private String name;
    
    @NotNull(message = "Custody status")
    private CustodyStatus custodyStatus;
    
    @OneToMany(mappedBy = "client")
    private List<Case> cases;
    
    public Client() {
        
    }

    public Client(Long id, String name, CustodyStatus custodyStatus) {
        this.id = id;
        this.name = name;
        this.custodyStatus = custodyStatus;
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

    @Enumerated(EnumType.ORDINAL)
    public CustodyStatus getCustodyStatus() {
        return custodyStatus;
    }

    public void setCustodyStatus(CustodyStatus custodyStatus) {
        this.custodyStatus = custodyStatus;
    }

    @Override
    public String toString() {
        return "Client [id=" + id + ", name=" + name + ", custodyStatus=" + custodyStatus + "]";
    }
}
