package com.pdclientmanager.controller;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.mockito.ArgumentMatchers.any;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
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
import com.pdclientmanager.model.form.AttorneyForm;
import com.pdclientmanager.model.form.AttorneyForm.AttorneyFormDtoBuilder;
import com.pdclientmanager.model.form.InvestigatorForm;
import com.pdclientmanager.model.form.InvestigatorForm.InvestigatorFormDtoBuilder;
import com.pdclientmanager.model.projection.AttorneyLightProjection;
import com.pdclientmanager.model.projection.AttorneyProjection;
import com.pdclientmanager.model.projection.InvestigatorProjection;
import com.pdclientmanager.repository.entity.WorkingStatus;
import com.pdclientmanager.service.AttorneyService;
import com.pdclientmanager.service.AttorneyServiceImpl;
import com.pdclientmanager.service.CaseService;
import com.pdclientmanager.service.CaseServiceImpl;
import com.pdclientmanager.service.InvestigatorService;
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
        AttorneyService attorneyServiceMock() {
            return Mockito.mock(AttorneyServiceImpl.class);
        }
        
        @Bean
        @Primary
        InvestigatorService investigatorServiceMock() {
            return Mockito.mock(InvestigatorServiceImpl.class);
        }
        
        @Bean
        @Primary
        CaseService caseService() {
            return Mockito.mock(CaseServiceImpl.class);
        }
    }
    
    @Autowired
    AttorneyService attorneyServiceMock;
    
    @Autowired
    InvestigatorService investigatorServiceMock;
    
    private MockMvc mockMvc;
    
    @Autowired
    WebApplicationContext wac;
    
    private ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
    
    private InvestigatorForm investigatorForm;
    private AttorneyForm attorneyForm;
    private AttorneyProjection attorney;
    private AttorneyLightProjection attorneyLight;
    private InvestigatorProjection investigator;
    private List<AttorneyProjection> attorneyList;
    private List<AttorneyLightProjection> attorneyLightList;
    private List<InvestigatorProjection> investigatorList;
    
    @BeforeAll
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
        
    @BeforeEach
    public void setupTestData() {
        
        
        attorneyForm = new AttorneyFormDtoBuilder().build();
        investigatorForm = new InvestigatorFormDtoBuilder().build();
        
        attorney = factory.createProjection(AttorneyProjection.class);
        attorney.setId(1L);
        attorney.setName("Active Attorney");
        attorney.setWorkingStatus(WorkingStatus.ACTIVE);
        attorneyList = new ArrayList<>();
        attorneyList.add(attorney);
        
        attorneyLight = factory.createProjection(AttorneyLightProjection.class);
        attorneyLight.setId(1L);
        attorneyLight.setName("Active Attorney");
        attorneyLight.setWorkingStatus(WorkingStatus.ACTIVE);
        attorneyLightList = new ArrayList<>();
        attorneyLightList.add(attorneyLight);
        
        investigator = factory.createProjection(InvestigatorProjection.class);
        investigator.setId(1L);
        investigator.setName("Active Investigator");
        investigator.setWorkingStatus(WorkingStatus.ACTIVE);
        investigatorList = new ArrayList<>();
        investigatorList.add(investigator);
    }
    
    @AfterAll
    public void validate() {
        validateMockitoUsage();
    }
    
    @Test
    public void employeeManagement_ShouldAddEmployeesToModelAndRenderEmployeeManagementView() throws Exception {
        
        when(attorneyServiceMock.findAllActive())
            .thenReturn(attorneyLightList);
        
        when(investigatorServiceMock.findAllActive())
            .thenReturn(investigatorList);
                
        mockMvc.perform(get("/employeeManagement"))
            .andExpect(status().isOk())
            .andExpect(view().name("employeeManagement"))
            .andExpect(forwardedUrl("/WEB-INF/views/employeeManagement.jsp"))
            .andExpect(model().attribute("activeInvestigators", contains(investigator)))
            .andExpect(model().attribute("activeAttorneys", contains(attorneyLight)));
    }
    
    @Test
    public void showNewAttorneyForm_ShouldAddAttorneyFormDtoAndActiveInvestigatorListToModelAndRenderFormView() throws Exception {
        
        when(investigatorServiceMock.findAllActive())
            .thenReturn(investigatorList);
        
        mockMvc.perform(get("/attorneys/add"))
            .andExpect(status().isOk())
            .andExpect(view().name("attorneys/attorneyForm"))
            .andExpect(forwardedUrl("/WEB-INF/views/attorneys/attorneyForm.jsp"))
            .andExpect(model().attribute("attorneyForm", instanceOf(AttorneyForm.class)))
            .andExpect(model().attribute("activeInvestigators", hasSize(1)))
            .andExpect(model().attribute("activeInvestigators", hasItem(
                    allOf(
                            hasProperty("id", is(1L)),
                            hasProperty("name", is("Active Investigator")),
                            hasProperty("workingStatus", is(WorkingStatus.ACTIVE)),
                            hasProperty("assignedAttorneys", is(investigator.getAssignedAttorneys()))))));
    }
    
    @Test 
    public void save_WhenValidNewAttorney_ShouldSaveAttorney() throws Exception {
        
        attorneyForm.setId(null);
        
        when(attorneyServiceMock.save(any(AttorneyForm.class)))
            .thenReturn(1L);
        
        mockMvc.perform(post("/attorneys")
            .param("name", "Active Attorney")
            .param("workingStatus", "ACTIVE")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(attorneyForm)))
            .andExpect(status().isFound())
            .andExpect(view().name("redirect:/attorneys/1"))
            .andExpect(redirectedUrl("/attorneys/1"))
            .andExpect(flash().attribute("css", is("success")))
            .andExpect(flash().attribute("msg", is("Attorney saved successfully!")));
    }
    
    @Test
    public void showAllAttorneys_ShouldRenderListAttorneysView() throws Exception {
        
        when(attorneyServiceMock.findAll())
            .thenReturn(attorneyList);
        
        mockMvc.perform(get("/attorneys"))
            .andExpect(status().isOk())
            .andExpect(view().name("attorneys/listAttorneys"))
            .andExpect(forwardedUrl("/WEB-INF/views/attorneys/listAttorneys.jsp"))
            .andExpect(model().attribute("attorneyList", hasSize(1)))
            .andExpect(model().attribute("attorneyList", hasItem(
                allOf(
                    hasProperty("id", is(1L)),
                    hasProperty("name", is("Active Attorney")),
                    hasProperty("workingStatus", is(WorkingStatus.ACTIVE))))));
    }
    
    @Test
    public void showAttorney_WhenValidRequest_ShouldRenderAttorneyView() throws Exception {
        
        when(attorneyServiceMock.findById(1L, AttorneyProjection.class))
            .thenReturn(attorney);
        
        mockMvc.perform(get("/attorneys/1"))
            .andExpect(status().isOk())
            .andExpect(view().name("attorneys/showAttorney"))
            .andExpect(forwardedUrl("/WEB-INF/views/attorneys/showAttorney.jsp"))
            .andExpect(model().attribute("attorney", samePropertyValuesAs(attorney)));
    }
    
    @Test
    public void showUpdateAttorneyForm_WhenValidRequest_ShouldRenderUpdateAttorneyForm() throws Exception {
        
        when(attorneyServiceMock.findFormById(1L))
            .thenReturn(attorneyForm);
        
        mockMvc.perform(get("/attorneys/1/update"))
            .andExpect(status().isOk())
            .andExpect(view().name("attorneys/attorneyForm"))
            .andExpect(forwardedUrl("/WEB-INF/views/attorneys/attorneyForm.jsp"))
            .andExpect(model().attribute("attorneyForm", samePropertyValuesAs(attorneyForm)));
    }
    
    @Test
    public void deleteAttorneyById_WhenValidRequest_ShouldDeleteAndAddMessage() throws Exception {
        
        Long targetId = 1L;
        
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
    public void deleteAttorneyById_WhenActiveCaseloadNotEmpty_ShouldReturnViewAndAddMessage() throws Exception {
        
        Long targetId = 1L;
        
        when(attorneyServiceMock.deleteById(targetId))
            .thenReturn(false);
        
        mockMvc.perform(post("/attorneys/" + targetId + "/delete")
                .param("id", targetId.toString()))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/attorneys"))
                .andExpect(redirectedUrl("/attorneys"))
                .andExpect(flash().attribute("css", is("danger")))
                .andExpect(flash().attribute("msg", is("The attorney's active caseload must be reassigned before deleting!")));
    }
    
    @Test
    public void showNewInvestigatorForm_ShouldAddInvestigatorFormDtoAndActiveAttorneyListToModelAndRenderFormView() throws Exception {
        
        when(attorneyServiceMock.findAllActive())
            .thenReturn(attorneyLightList);
        
        mockMvc.perform(get("/investigators/add"))
            .andExpect(status().isOk())
            .andExpect(view().name("investigators/investigatorForm"))
            .andExpect(forwardedUrl("/WEB-INF/views/investigators/investigatorForm.jsp"))
            .andExpect(model().attribute("investigatorForm", instanceOf(InvestigatorForm.class)))
            .andExpect(model().attribute("activeAttorneys", contains(attorneyLight)));
    }
    
    @Test
    public void saveInvestigator_WhenValidInvestigator_ShouldPersistInvestigator() throws Exception {
        
        investigatorForm.setId(null);
        
        when(investigatorServiceMock.save(any(InvestigatorForm.class)))
            .thenReturn(1L);
        
        mockMvc.perform(post("/investigators")
                .param("name", "Active Investigator")
                .param("workingStatus", "ACTIVE")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(investigatorForm)))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/investigators/1"))
                .andExpect(redirectedUrl("/investigators/1"))
                .andExpect(flash().attribute("css", is("success")))
                .andExpect(flash().attribute("msg", is("Investigator saved successfully!"))); 
    }
    
    @Test
    public void viewAllInvestigators_ShouldRenderListInvestigatorsView() throws Exception {
        
        when(investigatorServiceMock.findAll())
            .thenReturn(investigatorList);
        
        mockMvc.perform(get("/investigators"))
                .andExpect(status().isOk())
                .andExpect(view().name("investigators/listInvestigators"))
                .andExpect(forwardedUrl("/WEB-INF/views/investigators/listInvestigators.jsp"))
                .andExpect(model().attribute("investigatorList", contains(investigator)));
    }
    
    @Test
    public void showInvestigator_WhenValidRequest_ShouldRenderInvestigatorView() throws Exception {
        
        when(investigatorServiceMock.findById(1L, InvestigatorProjection.class))
        .thenReturn(investigator);
    
        mockMvc.perform(get("/investigators/1"))
            .andExpect(status().isOk())
            .andExpect(view().name("investigators/showInvestigator"))
            .andExpect(forwardedUrl("/WEB-INF/views/investigators/showInvestigator.jsp"))
            .andExpect(model().attribute("investigator", samePropertyValuesAs(investigator)));
    }
    
    @Test
    public void showUpdateInvestigatorForm_WhenValidRequest_ShouldRenderUpdateInvestigatorForm() throws Exception {
        
        when(investigatorServiceMock.findFormById(1L))
        .thenReturn(investigatorForm);
    
        mockMvc.perform(get("/investigators/1/update"))
            .andExpect(status().isOk())
            .andExpect(view().name("investigators/investigatorForm"))
            .andExpect(forwardedUrl("/WEB-INF/views/investigators/investigatorForm.jsp"))
            .andExpect(model().attribute("investigatorForm", samePropertyValuesAs(investigatorForm)));
    }
    
    @Test
    public void deleteInvestigatorById_WhenValidRequest_ShouldDeleteAndAddMessage() throws Exception {        
        Long targetId = investigator.getId();
        
        mockMvc.perform(post("/investigators/" + targetId + "/delete")
                .param("id", targetId.toString()))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/investigators"))
                .andExpect(redirectedUrl("/investigators"))
                .andExpect(flash().attribute("css", is("success")))
                .andExpect(flash().attribute("msg", is("Investigator is deleted!")));
    }
    
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
