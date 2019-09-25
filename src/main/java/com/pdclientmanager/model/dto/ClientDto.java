package com.pdclientmanager.model.dto;

import java.util.ArrayList;
import java.util.List;

import com.pdclientmanager.model.entity.CustodyStatus;

public class ClientDto {

    private Long id;
    private String name;
    private CustodyStatus custodyStatus;
    private List<CaseMinimalDto> cases;
    
    public ClientDto() {
        
    }

    public ClientDto(Long id, String name, CustodyStatus custodyStatus, List<CaseMinimalDto> cases) {
        this.id = id;
        this.name = name;
        this.custodyStatus = custodyStatus;
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

    public CustodyStatus getCustodyStatus() {
        return custodyStatus;
    }

    public void setCustodyStatus(CustodyStatus custodyStatus) {
        this.custodyStatus = custodyStatus;
    }

    public List<CaseMinimalDto> getCases() {
        return cases;
    }

    public void setCases(List<CaseMinimalDto> cases) {
        this.cases = cases;
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
        if (!(obj instanceof ClientDto))
            return false;
        ClientDto other = (ClientDto) obj;
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
    
    public static class ClientDtoBuilder {
        
        private Long id = 1L;
        private String name = "Default ClientDto";
        private CustodyStatus custodyStatus = CustodyStatus.IN_CUSTODY;
        private List<CaseMinimalDto> cases = new ArrayList<>();
        
        public ClientDtoBuilder withId(Long id) {
            this.id = id;
            return this;
        }
        
        public ClientDtoBuilder withName(String name) {
            this.name = name;
            return this;
        }
        
        public ClientDtoBuilder withCustodyStatus(CustodyStatus custodyStatus) {
            this.custodyStatus = custodyStatus;
            return this;
        }
        
        public ClientDtoBuilder withCases(List<CaseMinimalDto> cases) {
            this.cases = cases;
            return this;
        }
        
        public ClientDto build() {
            return new ClientDto(id, name, custodyStatus, cases);
        }
    }
}
