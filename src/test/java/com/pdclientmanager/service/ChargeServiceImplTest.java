package com.pdclientmanager.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import com.pdclientmanager.config.WebConfigTest;
import com.pdclientmanager.model.form.ChargeForm;
import com.pdclientmanager.model.projection.ChargeProjection;
import com.pdclientmanager.repository.ChargeRepository;
import com.pdclientmanager.repository.entity.Charge;
import com.pdclientmanager.util.mapper.ChargeMapper;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfigTest.class})
public class ChargeServiceImplTest {
    
    @Mock
    private ChargeRepository chargeRepositoryMock;
    
    @Mock
    private ChargeMapper chargeMapperMock;
    
    private ChargeService chargeService;
    
    private ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
    
    private ChargeForm form;
    private Charge charge;
    private ChargeProjection chargeProjection;
    private List<ChargeProjection> chargeList;
    
    @BeforeEach
    public void setUp() {

        initMocks(this);
        chargeService = new ChargeServiceImpl(chargeRepositoryMock, chargeMapperMock);
        
        form = new ChargeForm.ChargeFormBuilder().build();
        charge = new Charge.ChargeBuilder().build();
        chargeProjection = factory.createProjection(ChargeProjection.class);
        chargeList = Arrays.asList(chargeProjection);
        
    }
    
    @Test
    public void save_ShouldMapToEntityThenSaveAndReturnId() throws Exception {
        when(chargeMapperMock.toCharge(form)).thenReturn(charge);
        when(chargeRepositoryMock.save(charge)).thenReturn(charge);
        
        Long returnedId = chargeService.save(form);
        
        assertThat(returnedId).isEqualTo(charge.getId());
        verify(chargeMapperMock).toCharge(form);
        verify(chargeRepositoryMock).save(charge);
    }
    
    @Test
    public void findById_WithValidIdAndType_ShouldReturnObjectOfType() throws Exception {
        when(chargeRepositoryMock.findById(1L, ChargeProjection.class)).thenReturn(Optional.of(chargeProjection));
        
        ChargeProjection result = chargeService.findById(1L, ChargeProjection.class);
        
        assertThat(result).isEqualTo(chargeProjection);
    }

    @Test
    public void findFormById_WithValidId_ShouldReturnForm() throws Exception {
        when(chargeRepositoryMock.findById(1L, Charge.class)).thenReturn(Optional.of(charge));
        when(chargeMapperMock.toChargeForm(charge)).thenReturn(form);
        
        ChargeForm result = chargeService.findFormById(1L);
        
        assertThat(result).isEqualTo(form);
    }
    
    @Test
    public void findByNameOrStatute_WithValidQuery_ShouldReturnProjectionList() throws Exception {
        when(chargeRepositoryMock.findFirst10ByNameContainingOrStatuteContaining("test", "test")).thenReturn(chargeList);
        
        List<ChargeProjection> result = chargeService.findByNameOrStatute("test");
        
        assertThat(result).isEqualTo(chargeList);
    }
    
    @Test
    public void findAll_ShouldReturnChargeList() throws Exception {
        when(chargeRepositoryMock.findAllBy()).thenReturn(chargeList);
        
        List<ChargeProjection> result = chargeService.findAll();
        
        assertThat(result).isEqualTo(chargeList);
    }
    
    @Test
    public void delete_WithValidTarget_ShouldDelete() throws Exception {
        chargeService.delete(chargeProjection);
        
        verify(chargeRepositoryMock).deleteById(chargeProjection.getId());
    }

    @Test
    public void deleteById_WithValidId_ShouldDelete() throws Exception {
        chargeService.deleteById(1L);
        
        verify(chargeRepositoryMock).deleteById(1L);
    }
}
