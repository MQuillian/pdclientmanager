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
import com.pdclientmanager.model.Attorney;

@ExtendWith(MockitoExtension.class)
class AttorneyServiceTest {

    //To test- setDao, create, getById, getAll, update, delete, deleteById
    
    //Basically just test exception handling? Not really worth testing pointless echoed things from mocked POJOs....
    
    @Mock
    private GenericEmployeeDaoImpl<Attorney> attorneyDao;
    
    @Mock
    private Attorney attorney;
    
    @Mock
    private List<Attorney> attorneyList;
    
    @InjectMocks
    private AttorneyServiceImpl attorneyService;
    
    @BeforeEach
    public void setupMock() {
        attorneyService = new AttorneyServiceImpl();
        attorneyService.dao = attorneyDao;
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void createAttorney_WithValidAttorney_CallsDaoCreateMethod() {
        attorneyService.create(attorney);
        verify(attorneyDao).create(attorney);
    }
    
    @Test
    public void getById_WithValidId_ReturnsAttorney() {
        when(attorneyDao.getById(1L)).thenReturn(attorney);
        assertThat(attorneyService.getById(1L)).isEqualTo(attorney);
        verify(attorneyDao).getById(1L);
    }
    
    @Test
    public void getAll_ReturnsList() {
        when(attorneyDao.getAll()).thenReturn(attorneyList);
        assertThat(attorneyService.getAll()).isEqualTo(attorneyList);
        verify(attorneyDao).getAll();
    }
    
    @Test
    public void update_WithValidEntity_CallsDaoUpdateMethod() {
        attorneyService.update(attorney);
        verify(attorneyDao).update(attorney);
    }
    
    @Test
    public void delete_WithValidEntity_CallsDaoDeleteMethod() {
        attorneyService.delete(attorney);
        verify(attorneyDao).delete(attorney);
    }
    
    @Test
    public void deleteById_WithValidId_CallsDaoDeleteByIdMethod() {
        attorneyService.deleteById(1L);
        verify(attorneyDao).deleteById(1L);
    }
}
