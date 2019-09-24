package com.pdclientmanager.util.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import com.pdclientmanager.config.WebConfigTest;
import com.pdclientmanager.model.dto.ChargeDto;
import com.pdclientmanager.model.entity.Charge;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfigTest.class})
public class ChargeMapperTest {

    @Autowired
    ChargeMapper chargeMapper;
    
    @Test
    public void mapper_ShouldMapDtoToEntity() {
        
        ChargeDto dto = new ChargeDto.ChargeDtoBuilder()
                .build();
        
        Charge entity = chargeMapper.toCharge(dto);
        
        assertThat(dto.getId()).isEqualTo(entity.getId());
        assertThat(dto.getName()).isEqualTo(entity.getName());
        assertThat(dto.getStatute()).isEqualTo(entity.getStatute());
    }
    
    @Test
    public void mapper_ShouldMapEntityToDto() {
        
        Charge entity = new Charge.ChargeBuilder()
                .build();
        
        ChargeDto dto = chargeMapper.toChargeDto(entity);
        
        assertThat(entity.getId()).isEqualTo(dto.getId());
        assertThat(entity.getName()).isEqualTo(dto.getName());
        assertThat(entity.getStatute()).isEqualTo(dto.getStatute());
    }
}
