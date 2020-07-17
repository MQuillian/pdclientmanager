package com.pdclientmanager.model.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.pdclientmanager.repository.entity.CustodyStatus;

public class ClientForm {

    private Long id;
    
    @NotEmpty
    private String name;
    
    @NotNull
    private CustodyStatus custodyStatus;
    
    public ClientForm() {
        
    }

    public ClientForm(Long id, String name, CustodyStatus custodyStatus) {
        this.id = id;
        this.name = name;
        this.custodyStatus = custodyStatus;
    }
    
    public boolean isNew() {
        return id == null;
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

    public CustodyStatus getCustodyStatus() {
        return custodyStatus;
    }

    public void setCustodyStatus(CustodyStatus custodyStatus) {
        this.custodyStatus = custodyStatus;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((custodyStatus == null) ? 0 : custodyStatus.hashCode());
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
        if (!(obj instanceof ClientForm))
            return false;
        ClientForm other = (ClientForm) obj;
        if (custodyStatus != other.custodyStatus)
            return false;
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
    
    public static class ClientFormBuilder {
        
        private Long id = 1L;
        private String name = "Default ClientForm";
        private CustodyStatus custodyStatus = CustodyStatus.IN_CUSTODY;
        
        public ClientFormBuilder withId(Long id) {
            this.id = id;
            return this;
        }
        
        public ClientFormBuilder withName(String name) {
            this.name = name;
            return this;
        }
        
        public ClientFormBuilder withCustodyStatus(CustodyStatus custodyStatus) {
            this.custodyStatus = custodyStatus;
            return this;
        }
        
        public ClientForm build() {
            return new ClientForm(id, name, custodyStatus);
        }
    }
}
