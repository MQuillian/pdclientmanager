package com.pdclientmanager.model.dto;

import com.pdclientmanager.model.dto.ChargedCountDto.ChargedCountDtoBuilder;

public class ChargedCountMinimalDto {
    
    private Long id;
    private int countNumber;
    private Long chargeId;
    
    public ChargedCountMinimalDto() {
        
    }

    public ChargedCountMinimalDto(Long id, int countNumber, Long chargeId) {
        this.id = id;
        this.countNumber = countNumber;
        this.chargeId = chargeId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCountNumber() {
        return countNumber;
    }

    public void setCountNumber(int countNumber) {
        this.countNumber = countNumber;
    }

    public Long getChargeId() {
        return chargeId;
    }

    public void setCharge(Long chargeId) {
        this.chargeId = chargeId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((chargeId == null) ? 0 : chargeId.hashCode());
        result = prime * result + countNumber;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof ChargedCountDto))
            return false;
        ChargedCountMinimalDto other = (ChargedCountMinimalDto) obj;
        if (chargeId == null) {
            if (other.chargeId != null)
                return false;
        } else if (!chargeId.equals(other.chargeId))
            return false;
        if (countNumber != other.countNumber)
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
    
    public static class ChargedCountMinimalDtoBuilder {
        
        private Long id = 1L;
        private Integer countNumber = 1;
        private Long chargeId = 1L;
        
        public ChargedCountMinimalDtoBuilder withId(Long id) {
            this.id = id;
            return this;
        }
        
        public ChargedCountMinimalDtoBuilder withCountNumber(Integer countNumber) {
            this.countNumber = countNumber;
            return this;
        }
        
        public ChargedCountMinimalDtoBuilder withCharge(Long chargeId) {
            this.chargeId = chargeId;
            return this;
        }
        
        public ChargedCountMinimalDto build() {
            return new ChargedCountMinimalDto(id, countNumber, chargeId);
        }
    }

}
