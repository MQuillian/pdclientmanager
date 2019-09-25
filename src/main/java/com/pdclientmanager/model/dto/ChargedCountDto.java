package com.pdclientmanager.model.dto;

public class ChargedCountDto {

    private Long id;
    private int countNumber;
    private ChargeDto charge;
    
    public ChargedCountDto() {
        
    }

    public ChargedCountDto(Long id, int countNumber, ChargeDto charge) {
        this.id = id;
        this.countNumber = countNumber;
        this.charge = charge;
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

    public ChargeDto getCharge() {
        return charge;
    }

    public void setCharge(ChargeDto charge) {
        this.charge = charge;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((charge == null) ? 0 : charge.hashCode());
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
        ChargedCountDto other = (ChargedCountDto) obj;
        if (charge == null) {
            if (other.charge != null)
                return false;
        } else if (!charge.equals(other.charge))
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
    
    public static class ChargedCountDtoBuilder {
        
        private Long id = 1L;
        private Integer countNumber = 1;
        private ChargeDto charge = new ChargeDto();
        
        public ChargedCountDtoBuilder withId(Long id) {
            this.id = id;
            return this;
        }
        
        public ChargedCountDtoBuilder withCountNumber(Integer countNumber) {
            this.countNumber = countNumber;
            return this;
        }
        
        public ChargedCountDtoBuilder withCharge(ChargeDto charge) {
            this.charge = charge;
            return this;
        }
        
        public ChargedCountDto build() {
            return new ChargedCountDto(id, countNumber, charge);
        }
    }
}
