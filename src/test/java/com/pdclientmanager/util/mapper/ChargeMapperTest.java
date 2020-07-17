package com.pdclientmanager.util.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import com.pdclientmanager.config.WebConfigTest;
import com.pdclientmanager.model.form.ChargeForm;
import com.pdclientmanager.repository.entity.Charge;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfigTest.class})
public class ChargeMapperTest {

    @Autowired
    ChargeMapper chargeMapper;
    
    @Test
    public void mapper_ShouldMapFormToEntity() {
        
        ChargeForm form = new ChargeForm.ChargeFormBuilder().build();
        
        Charge entity = chargeMapper.toCharge(form);
        
        assertThat(form.getId()).isEqualTo(entity.getId());
        assertThat(form.getName()).isEqualTo(entity.getName());
        assertThat(form.getStatute()).isEqualTo(entity.getStatute());
    }
}
