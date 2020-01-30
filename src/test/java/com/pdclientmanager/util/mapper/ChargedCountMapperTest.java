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
import com.pdclientmanager.model.dto.ChargedCountDto;
import com.pdclientmanager.model.entity.Charge;
import com.pdclientmanager.model.entity.ChargedCount;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfigTest.class})
public class ChargedCountMapperTest {

    @Autowired
    ChargedCountMapper chargedCountMapper;
    
    @Test
    public void mapper_ShouldMapDtoToEntity() {
        
        ChargedCountDto dto = new ChargedCountDto.ChargedCountDtoBuilder()
                .withCharge(new ChargeDto.ChargeDtoBuilder().build())
                .build();
        
        ChargedCount entity = chargedCountMapper.toChargedCountFromChargedCountDto(dto);
                
        assertThat(dto.getId()).isEqualTo(entity.getId());
        assertThat(dto.getCountNumber()).isEqualTo(entity.getCountNumber());
        assertThat(dto.getCharge().getId()).isEqualTo(entity.getCharge().getId());
    }
    
    @Test
    public void mapper_ShouldMapEntityToDto() {
        
        ChargedCount entity = new ChargedCount.ChargedCountBuilder()
                .withCharge(new Charge.ChargeBuilder().build())
                .build();
        
        ChargedCountDto dto = chargedCountMapper.toChargedCountDtoFromChargedCount(entity);
        
        assertThat(dto.getId()).isEqualTo(entity.getId());
        assertThat(dto.getCountNumber()).isEqualTo(entity.getCountNumber());
        assertThat(dto.getCharge().getId()).isEqualTo(entity.getCharge().getId());
    }
    
}
