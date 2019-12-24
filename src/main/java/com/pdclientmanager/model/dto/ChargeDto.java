package com.pdclientmanager.model.dto;

public class ChargeDto {

    private Long id;
    private String name;
    private String statute;
    
    public ChargeDto () {
        
    }

    public ChargeDto(Long id, String name, String statute) {
        this.id = id;
        this.name = name;
        this.statute = statute;
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

    public String getStatute() {
        return statute;
    }

    public void setStatute(String statute) {
        this.statute = statute;
    }
    
    @Override
    public String toString() {
        return statute + " - " + name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((statute == null) ? 0 : statute.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof ChargeDto))
            return false;
        ChargeDto other = (ChargeDto) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (statute == null) {
            if (other.statute != null)
                return false;
        } else if (!statute.equals(other.statute))
            return false;
        return true;
    }
    
    public static class ChargeDtoBuilder {
        
        private Long id = 1L;
        private String name = "Default ChargeDto";
        private String statute = "Default Statute";
        
        public ChargeDtoBuilder withId(Long id) {
            this.id = id;
            return this;
        }
        
        public ChargeDtoBuilder withName(String name) {
            this.name = name;
            return this;
        }
        
        public ChargeDtoBuilder withStatute(String statute) {
            this.statute = statute;
            return this;
        }
        
        public ChargeDto build() {
            return new ChargeDto(id, name, statute);
        }
    }
}
