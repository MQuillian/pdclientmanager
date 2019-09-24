package com.pdclientmanager.util.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import com.pdclientmanager.config.WebConfigTest;
import com.pdclientmanager.model.dto.JudgeDto;
import com.pdclientmanager.model.entity.Judge;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfigTest.class})
public class JudgeMapperTest {

    @Autowired
    JudgeMapper judgeMapper;
    
    @Test
    public void mapper_ShouldMapDtoToEntity() {
        
        JudgeDto dto = new JudgeDto.JudgeDtoBuilder()
                .build();
        
        Judge entity = judgeMapper.toJudge(dto);
        
        assertThat(dto.getId()).isEqualTo(entity.getId());
        assertThat(dto.getName()).isEqualTo(entity.getName());
    }
    
    @Test
    public void mapper_ShouldMapEntityToDto() {
        
        Judge entity = new Judge.JudgeBuilder()
                .build();
        
        JudgeDto dto = judgeMapper.toJudgeDto(entity);
        
        assertThat(entity.getId()).isEqualTo(dto.getId());
        assertThat(entity.getName()).isEqualTo(dto.getName());
    }
}
