package com.pdclientmanager.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pdclientmanager.dao.GenericEmployeeDaoImpl;
import com.pdclientmanager.model.Investigator;

@ExtendWith(MockitoExtension.class)
class investigatorServiceTest {
    
    @Mock
    private GenericEmployeeDaoImpl<Investigator> investigatorDao;
    
    @Mock
    private Investigator investigator;
    
    @Mock
    private List<Investigator> investigatorList;
    
    @InjectMocks
    private InvestigatorServiceImpl investigatorService;
    
    @BeforeEach
    public void setupMock() {
        investigatorService = new InvestigatorServiceImpl();
        investigatorService.dao = investigatorDao;
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void createinvestigator_WithValidinvestigator_CallsDaoCreateMethod() {
        investigatorService.create(investigator);
        verify(investigatorDao).create(investigator);
    }
    
    @Test
    public void getById_WithValidId_Returnsinvestigator() {
        when(investigatorDao.getById(1L)).thenReturn(investigator);
        assertThat(investigatorService.getById(1L)).isEqualTo(investigator);
        verify(investigatorDao).getById(1L);
    }
    
    @Test
    public void getAll_ReturnsList() {
        when(investigatorDao.getAll()).thenReturn(investigatorList);
        assertThat(investigatorService.getAll()).isEqualTo(investigatorList);
        verify(investigatorDao).getAll();
    }
    
    @Test
    public void update_WithValidEntity_CallsDaoUpdateMethod() {
        investigatorService.update(investigator);
        verify(investigatorDao).update(investigator);
    }
    
    @Test
    public void delete_WithValidEntity_CallsDaoDeleteMethod() {
        investigatorService.delete(investigator);
        verify(investigatorDao).delete(investigator);
    }
    
    @Test
    public void deleteById_WithValidId_CallsDaoDeleteByIdMethod() {
        investigatorService.deleteById(1L);
        verify(investigatorDao).deleteById(1L);
    }
}
