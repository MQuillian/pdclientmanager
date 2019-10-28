package com.pdclientmanager.model.dto;

import java.util.ArrayList;
import java.util.List;

import com.pdclientmanager.model.entity.CustodyStatus;

public class ClientFormDto {

    private Long id;
    private String name;
    private CustodyStatus custodyStatus;
    private List<Long> casesIds;
    
    public ClientFormDto() {
        
    }

    public ClientFormDto(Long id, String name, CustodyStatus custodyStatus, List<Long> casesIds) {
        this.id = id;
        this.name = name;
        this.custodyStatus = custodyStatus;
        this.casesIds = casesIds;
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

    public List<Long> getCasesIds() {
        return casesIds;
    }

    public void setCasesIds(List<Long> casesIds) {
        this.casesIds = casesIds;
    }

    @Override
    public String toString() {
        return "ClientFormDto [id=" + id + ", name=" + name + ", custodyStatus=" + custodyStatus + ", casesIds="
                + casesIds + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((casesIds == null) ? 0 : casesIds.hashCode());
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
        if (!(obj instanceof ClientFormDto))
            return false;
        ClientFormDto other = (ClientFormDto) obj;
        if (casesIds == null) {
            if (other.casesIds != null)
                return false;
        } else if (!casesIds.equals(other.casesIds))
            return false;
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
    
    public static class ClientFormDtoBuilder {
        
        private Long id = 1L;
        private String name = "Default ClientFormDto";
        private CustodyStatus custodyStatus = CustodyStatus.IN_CUSTODY;
        private List<Long> casesIds = new ArrayList<>();
        
        public ClientFormDtoBuilder withId(Long id) {
            this.id = id;
            return this;
        }
        
        public ClientFormDtoBuilder withName(String name) {
            this.name = name;
            return this;
        }
        
        public ClientFormDtoBuilder withCustodyStatus(CustodyStatus custodyStatus) {
            this.custodyStatus = custodyStatus;
            return this;
        }
        
        public ClientFormDtoBuilder withCasesIds(List<Long> casesIds) {
            this.casesIds = casesIds;
            return this;
        }
        
        public ClientFormDto build() {
            return new ClientFormDto(id, name, custodyStatus, casesIds);
        }
    }
}
