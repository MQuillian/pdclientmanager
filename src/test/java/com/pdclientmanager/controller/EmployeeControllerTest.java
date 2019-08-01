package com.pdclientmanager.controller;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.validateMockitoUsage;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pdclientmanager.config.WebConfigTest;
import com.pdclientmanager.model.Attorney;
import com.pdclientmanager.model.EmploymentStatus;
import com.pdclientmanager.model.Investigator;
import com.pdclientmanager.service.AttorneyServiceImpl;
import com.pdclientmanager.service.EmployeeService;
import com.pdclientmanager.service.InvestigatorServiceImpl;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfigTest.class})
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public class EmployeeControllerTest {
    
    @Profile("test")
    @Configuration
    static class ContextConfiguration {
        
        @Bean
        @Primary
        EmployeeService<Attorney> attorneyServiceMock() {
            return Mockito.mock(AttorneyServiceImpl.class);
        }
        
        @Bean
        @Primary
        EmployeeService<Investigator> investigatorServiceMock() {
            return Mockito.mock(InvestigatorServiceImpl.class);
        }
    }
    
    @Autowired
    EmployeeService<Attorney> attorneyServiceMock;
    
    @Autowired
    EmployeeService<Investigator> investigatorServiceMock;
    
    private Investigator activeInvestigator;
    private Investigator inactiveInvestigator;
    private Attorney activeAttorney;
    private Attorney inactiveAttorney;
    private List<Investigator> investigatorList;
    private List<Investigator> activeInvestigatorList;
    private List<Attorney> attorneyList;
    private List<Attorney> activeAttorneyList;
    
    private MockMvc mockMvc;
    
    @Autowired
    WebApplicationContext wac;
    
    @BeforeAll
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
        
    @BeforeEach
    public void setupTestData() {
        // REFACTOR THIS WITH BUILDER PATTERN TO CLEAN IT UP!!!
        
        activeInvestigator = new Investigator(1L, "Test Inv 1", EmploymentStatus.ACTIVE, 
                new ArrayList<Attorney>());
        inactiveInvestigator = new Investigator(2L, "Test Inv 2", EmploymentStatus.INACTIVE, 
                new ArrayList<Attorney>());
        activeAttorney = new Attorney(1L, "Test Attorney 1", EmploymentStatus.ACTIVE, 
                activeInvestigator);
        inactiveAttorney = new Attorney(2L, "Test Attorney 2", EmploymentStatus.INACTIVE, 
                inactiveInvestigator);
        
        List<Attorney> activeInvestigatorAssignments = new ArrayList<>();
        activeInvestigatorAssignments.add(activeAttorney);
        activeInvestigator.setAssignedAttorneys(activeInvestigatorAssignments);
        
        investigatorList = new ArrayList<>();
        investigatorList.add(activeInvestigator);
        investigatorList.add(inactiveInvestigator);
        
        activeInvestigatorList = new ArrayList<>();
        activeInvestigatorList.add(activeInvestigator);
        
        attorneyList = new ArrayList<>();
        attorneyList.add(activeAttorney);
        attorneyList.add(inactiveAttorney);
        
        activeAttorneyList = new ArrayList<>();
        activeAttorneyList.add(activeAttorney);
    }
    
    @AfterAll
    public void validate() {
        validateMockitoUsage();
    }
    
    @Test
    public void employeeManagement_ShouldAddEmployeesToModelAndRenderEmployeeManagementView() throws Exception {
        
        when(attorneyServiceMock.getAllActive())
            .thenReturn(activeAttorneyList);
        
        when(investigatorServiceMock.getAllActive())
            .thenReturn(activeInvestigatorList);
                
        mockMvc.perform(get("/employeeManagement"))
            .andExpect(status().isOk())
            .andExpect(view().name("employeeManagement"))
            .andExpect(forwardedUrl("/WEB-INF/views/employeeManagement.jsp"))
            .andExpect(model().attribute("activeInvestigators", hasSize(1)))
            .andExpect(model().attribute("activeInvestigators", hasItem(
                    allOf(
                            hasProperty("id", is(1L)),
                            hasProperty("name", is("Test Inv 1")),
                            hasProperty("employmentStatus", is(EmploymentStatus.ACTIVE)),
                            hasProperty("assignedAttorneys", is(activeInvestigator.getAssignedAttorneys()))
                        )
            )))
            .andExpect(model().attribute("activeAttorneys", hasSize(1)))
            .andExpect(model().attribute("activeAttorneys", hasItem(
                    allOf(
                            hasProperty("id", is(1L)),
                            hasProperty("name", is("Test Attorney 1")),
                            hasProperty("employmentStatus", is(EmploymentStatus.ACTIVE)),
                            hasProperty("investigator", is(activeInvestigator))
                        )
            )));
    }
    
    @Test
    public void attorneyForm_ShouldAddAttorneyAndActiveInvestigatorListToModelAndRenderFormView() throws Exception {
        
        when(investigatorServiceMock.getAllActive())
            .thenReturn(activeInvestigatorList);
        
        mockMvc.perform(get("/attorneys/add"))
            .andExpect(status().isOk())
            .andExpect(view().name("attorneys/attorneyForm"))
            .andExpect(forwardedUrl("/WEB-INF/views/attorneys/attorneyForm.jsp"))
            .andExpect(model().attribute("attorneyForm", instanceOf(Attorney.class)))
            .andExpect(model().attribute("activeInvestigators", hasSize(1)))
            .andExpect(model().attribute("activeInvestigators", hasItem(
                    allOf(
                            hasProperty("id", is(1L)),
                            hasProperty("name", is("Test Inv 1")),
                            hasProperty("employmentStatus", is(EmploymentStatus.ACTIVE)),
                            hasProperty("assignedAttorneys", is(activeInvestigator.getAssignedAttorneys()))
                            )
                    )));
    }
    
    @SuppressWarnings("rawtypes")
    @Test 
    public void persistOrMergeAttorney_WhenValidNewAttorney_ShouldPersistAttorney() throws Exception {
        
        Attorney newAttorney = activeAttorney;
        newAttorney.setId(null);
        
//        when(attorneyServiceMock.creatingNew(any(Attorney.class)))
//            .thenReturn(true);
        
// Stubbing persist method to simulate generating id for newAttorney upon persisting
        doAnswer((Answer) invocation -> {
            Object arg0 = invocation.getArgument(0);
            Attorney testedAttorney = (Attorney) arg0;
            
            testedAttorney.setId(1L);
            return null;
        }).when(attorneyServiceMock).persist(any(Attorney.class));
        
        mockMvc.perform(post("/attorneys")
            .param("name", "Test Attorney 1")
            .param("employmentStatus", "ACTIVE")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(newAttorney)))
            .andExpect(status().isFound())
            .andExpect(view().name("redirect:/attorneys/1"))
            .andExpect(redirectedUrl("/attorneys/1"))
            .andExpect(flash().attribute("css", is("success")))
            .andExpect(flash().attribute("msg", is("Attorney added successfully!")));
    }
    
    @Test
    public void viewAllAttorneys_ShouldRenderListViewOfAllAttorneys() throws Exception {
        when(attorneyServiceMock.getAll())
            .thenReturn(attorneyList);
        
        mockMvc.perform(get("/attorneys"))
            .andExpect(status().isOk())
            .andExpect(view().name("attorneys/list"))
            .andExpect(forwardedUrl("/WEB-INF/views/attorneys/list.jsp"))
            .andExpect(model().attribute("attorneyList", hasSize(2)))
            .andExpect(model().attribute("attorneyList", hasItem(
                allOf(
                    hasProperty("id", is(1L)),
                    hasProperty("name", is("Test Attorney 1")),
                    hasProperty("employmentStatus", is(EmploymentStatus.ACTIVE)),
                    hasProperty("investigator", is(activeInvestigator))
                )
            )))
            .andExpect(model().attribute("attorneyList", hasItem(
                allOf(
                    hasProperty("id", is(2L)),
                    hasProperty("name", is("Test Attorney 2")),
                    hasProperty("employmentStatus", is(EmploymentStatus.INACTIVE)),
                    hasProperty("investigator", is(inactiveInvestigator))))));
    }
    
    @Test
    public void showAttorney_WhenValidId_ShouldRenderViewOfAttorney() throws Exception {
        when(attorneyServiceMock.getById(1L))
            .thenReturn(activeAttorney);
        
        mockMvc.perform(get("/attorneys/1"))
            .andExpect(status().isOk())
            .andExpect(view().name("attorneys/showIndividual"))
            .andExpect(forwardedUrl("/WEB-INF/views/attorneys/showIndividual.jsp"))
            .andExpect(model().attribute("attorney", samePropertyValuesAs(activeAttorney)));
    }
    
    @Test
    public void showUpdateAttorneyForm_WhenValidId_ShouldRenderUpdateAttorneyForm() throws Exception {
        when(attorneyServiceMock.getById(1L))
            .thenReturn(activeAttorney);
        
        mockMvc.perform(get("/attorneys/1/update"))
            .andExpect(status().isOk())
            .andExpect(view().name("attorneys/attorneyForm"))
            .andExpect(forwardedUrl("/WEB-INF/views/attorneys/attorneyForm.jsp"))
            .andExpect(model().attribute("attorneyForm", samePropertyValuesAs(activeAttorney)));
    }
    
    @Test
    public void deleteAttorneyById_WhenValidRequest_ShouldDeleteAndAddMessage() throws Exception {
        
        Long targetId = inactiveAttorney.getId();
        
        when(attorneyServiceMock.deleteById(targetId))
            .thenReturn(true);
        
        mockMvc.perform(post("/attorneys/" + targetId + "/delete")
                .param("id", targetId.toString()))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/attorneys"))
                .andExpect(redirectedUrl("/attorneys"))
                .andExpect(flash().attribute("css", is("success")))
                .andExpect(flash().attribute("msg", is("Attorney is deleted!")));
    }
    
    @Test
    public void deleteAttorneyById_WhenInvalidRequest_ShouldReturnViewAndAddMessage() throws Exception {
        
        Long targetId = inactiveAttorney.getId();
        
        when(attorneyServiceMock.deleteById(targetId))
            .thenReturn(false);
        
        mockMvc.perform(post("/attorneys/" + targetId + "/delete")
                .param("id", targetId.toString()))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/attorneys"))
                .andExpect(redirectedUrl("/attorneys"))
                .andExpect(flash().attribute("css", is("danger")))
                .andExpect(flash().attribute("msg", is("Attorney's caseload must be reassigned before deleting!")));
    }
    
    @Test
    public void investigatorForm_ShouldAddInvestigatorAndActiveAttorneyListToModelAndRenderFormView() throws Exception {
        
        when(attorneyServiceMock.getAllActive())
            .thenReturn(activeAttorneyList);
        
        mockMvc.perform(get("/investigators/add"))
            .andExpect(status().isOk())
            .andExpect(view().name("investigators/investigatorForm"))
            .andExpect(forwardedUrl("/WEB-INF/views/investigators/investigatorForm.jsp"))
            .andExpect(model().attribute("investigatorForm", instanceOf(Investigator.class)))
            .andExpect(model().attribute("activeAttorneys", hasSize(1)))
            .andExpect(model().attribute("activeAttorneys", hasItem(
                    allOf(
                            hasProperty("id", is(1L)),
                            hasProperty("name", is("Test Attorney 1")),
                            hasProperty("employmentStatus", is(EmploymentStatus.ACTIVE))
                    )
            )));
    }
    
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
