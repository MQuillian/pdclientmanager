package com.pdclientmanager.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((statute == null) ? 0 : statute.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Charge))
            return false;
        Charge other = (Charge) obj;
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
        if (statute == null) {
            if (other.statute != null)
                return false;
        } else if (!statute.equals(other.statute))
            return false;
        return true;
    }
    
    public static class ChargeBuilder {
        
        Long id = 1L;
        String name = "Default Charge";
        String statute = "Default Statute";
        
        public ChargeBuilder withId(Long id) {
            this.id = id;
            return this;
        }
        
        public ChargeBuilder withName(String name) {
            this.name = name;
            return this;
        }
        
        public ChargeBuilder withStatute(String statute) {
            this.statute = statute;
            return this;
        }
        
        public Charge build() {
            return new Charge(id, name, statute);
        }
    }
}
