package com.pdclientmanager.model.dto;

public class ChargedCountMinimalDto {

    private int countNumber;
    private Long chargeId;
    
    public ChargedCountMinimalDto() {
        
    }

    public ChargedCountMinimalDto(int countNumber, Long chargeId) {
        this.countNumber = countNumber;
        this.chargeId = chargeId;
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
        return true;
    }
    
    public static class ChargedCountMinimalDtoBuilder {
        
        private Integer countNumber = 1;
        private Long chargeId = 1L;
        
        public ChargedCountMinimalDtoBuilder withCountNumber(Integer countNumber) {
            this.countNumber = countNumber;
            return this;
        }
        
        public ChargedCountMinimalDtoBuilder withCharge(Long chargeId) {
            this.chargeId = chargeId;
            return this;
        }
        
        public ChargedCountMinimalDto build() {
            return new ChargedCountMinimalDto(countNumber, chargeId);
        }
    }

}
