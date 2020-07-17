package com.pdclientmanager.util.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import com.pdclientmanager.config.WebConfigTest;
import com.pdclientmanager.model.form.JudgeForm;
import com.pdclientmanager.repository.entity.Judge;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfigTest.class})
public class JudgeMapperTest {

    @Autowired
    JudgeMapper judgeMapper;
    
    private Judge judge;
    
    private JudgeForm form;

    @BeforeEach
    public void setup() {
        
        judge = new Judge.JudgeBuilder().build();
        
        form = new JudgeForm.JudgeFormBuilder().build();
    }
    
    @Test
    public void mapper_ShouldMapFormToEntity() {
        
        Judge entity = judgeMapper.toJudge(form);
        
        assertThat(entity.getId()).isEqualTo(form.getId());
        assertThat(entity.getName()).isEqualTo(form.getName());
        assertThat(entity.getWorkingStatus()).isEqualTo(form.getWorkingStatus());
    }
    
    @Test
    public void mapper_ShouldMapEntityToForm() {
        
        JudgeForm judgeForm = judgeMapper.toJudgeForm(judge);
        
        assertThat(judgeForm.getId()).isEqualTo(judge.getId());
        assertThat(judgeForm.getName()).isEqualTo(judge.getName());
        assertThat(judgeForm.getWorkingStatus()).isEqualTo(judge.getWorkingStatus());
    }
}
