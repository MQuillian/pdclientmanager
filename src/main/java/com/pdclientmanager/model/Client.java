package com.pdclientmanager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "clients")
public class Client {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long id;
    
    @Column(name = "name")
    @NotEmpty(message = "Name")
    private String name;
    
    @Column(name = "in_custody")
    @NotEmpty(message = "In custody")
    private CustodyStatus custodyStatus;
    
    @Column(name = "FK_attorney")
    @NotNull(message = "Attorney")
    private Long attorneyId;
    
    public Client() {
        
    }

    public Client(Long id, String name, CustodyStatus custodyStatus, Long attorneyId) {
        this.id = id;
        this.name = name;
        this.custodyStatus = custodyStatus;
        this.attorneyId = attorneyId;
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

    public Long getAttorneyId() {
        return attorneyId;
    }

    public void setAttorneyId(Long attorneyId) {
        this.attorneyId = attorneyId;
    }
    
    

}
