package com.pdclientmanager.repository.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@NamedEntityGraph(name = "Client.fullProjection",
        attributeNodes = {
                @NamedAttributeNode(value = "cases")
        })
public class Client {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotEmpty(message = "Name")
    private String name;
    
    @NotNull(message = "Custody status")
    private CustodyStatus custodyStatus;
    
    @NotNull(message = "Incarceration date")
    private LocalDate incarcerationDate;
    
    private LocalDate releaseDate;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
    private List<Case> cases;
    
    public Client() {
        
    }

    public Client(Long id, String name, CustodyStatus custodyStatus, LocalDate incarcerationDate,
    		LocalDate releaseDate, List<Case> cases) {
        this.id = id;
        this.name = name;
        this.custodyStatus = custodyStatus;
        this.incarcerationDate = incarcerationDate;
        this.releaseDate = releaseDate;
        this.cases = cases;
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
    
    public LocalDate getIncarcerationDate() {
    	return incarcerationDate;
    }
    
    public void setIncarcerationDate(LocalDate incarcerationDate) {
    	this.incarcerationDate = incarcerationDate;
    }
    
    public LocalDate getReleaseDate() {
    	return releaseDate;
    }
    
    public void setReleaseDate(LocalDate releaseDate) {
    	this.releaseDate = releaseDate;
    }
    
    public List<Case> getCases() {
        return cases;
    }
    
    public void setCases(List<Case> cases) {
        this.cases = cases;
    }

    @Override
    public String toString() {
        return "Client [id=" + id + ", name=" + name + ", custodyStatus=" + custodyStatus + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Client))
            return false;
        Client other = (Client) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
    
    public static class ClientBuilder {
        
        Long id = 1L;
        String name = "Default Client";
        CustodyStatus custodyStatus = CustodyStatus.IN_CUSTODY;
        LocalDate incarcerationDate = LocalDate.now();
        LocalDate releaseDate = null;
        List<Case> cases = new ArrayList<>();
        
        public ClientBuilder withId(Long id) {
            this.id = id;
            return this;
        }
        
        public ClientBuilder withName(String name) {
            this.name = name;
            return this;
        }
        
        public ClientBuilder withCustodyStatus(CustodyStatus custodyStatus) {
            this.custodyStatus = custodyStatus;
            return this;
        }
        
        public ClientBuilder withIncarcerationDate(LocalDate incarcerationDate) {
        	this.incarcerationDate = incarcerationDate;
        	return this;
        }
        
        public ClientBuilder withReleaseDate(LocalDate releaseDate) {
        	this.releaseDate = releaseDate;
        	return this;
        }
        
        public ClientBuilder withCases(List<Case> cases) {
            this.cases = cases;
            return this;
        }
        
        public Client build() {
            return new Client(id, name, custodyStatus, incarcerationDate, releaseDate, cases);
        }
    }
}
