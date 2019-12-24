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
import com.pdclientmanager.model.dto.AttorneyDto;
import com.pdclientmanager.model.dto.AttorneyFormDto;
import com.pdclientmanager.model.dto.AttorneyFormDto.AttorneyFormDtoBuilder;
import com.pdclientmanager.model.dto.AttorneyMinimalDto;
import com.pdclientmanager.model.dto.InvestigatorDto;
import com.pdclientmanager.model.dto.InvestigatorFormDto;
import com.pdclientmanager.model.dto.InvestigatorFormDto.InvestigatorFormDtoBuilder;
import com.pdclientmanager.model.dto.InvestigatorMinimalDto;
import com.pdclientmanager.model.entity.WorkingStatus;
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
    
    private InvestigatorDto activeInvestigatorDto;
    private InvestigatorDto inactiveInvestigatorDto;
    private InvestigatorMinimalDto investigatorMinimalDto;
    private InvestigatorFormDto investigatorFormDto;
    private AttorneyDto activeAttorneyDto;
    private AttorneyDto inactiveAttorneyDto;
    private AttorneyMinimalDto attorneyMinimalDto;
    private AttorneyFormDto attorneyFormDto;
    private List<AttorneyDto> allAttorneyDtoList;
    private List<AttorneyDto> activeAttorneyDtoList;
    private List<AttorneyMinimalDto> attorneyMinimalDtoList;
    private List<InvestigatorDto> allInvestigatorDtoList;
    private List<InvestigatorDto> activeInvestigatorDtoList;
    
    @BeforeAll
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
        
    @BeforeEach
    public void setupTestData() {
        
        activeInvestigatorDto = new InvestigatorDto.InvestigatorDtoBuilder()
                .withId(1L)
                .withName("Active Investigator")
                .withWorkingStatus(WorkingStatus.ACTIVE)
                .build();
        
        inactiveInvestigatorDto = new InvestigatorDto.InvestigatorDtoBuilder()
                .withId(2L)
                .withName("Inactive Investigator")
                .withWorkingStatus(WorkingStatus.INACTIVE)
                .build();
        
        investigatorMinimalDto = new InvestigatorMinimalDto.InvestigatorMinimalDtoBuilder()
                .build();
        
        activeAttorneyDto = new AttorneyDto.AttorneyDtoBuilder()
                .withId(1L)
                .withName("Active Attorney")
                .withWorkingStatus(WorkingStatus.ACTIVE)
                .withInvestigator(investigatorMinimalDto)
                .build();
        
        inactiveAttorneyDto = new AttorneyDto.AttorneyDtoBuilder()
                .withId(2L)
                .withName("Inactive Attorney")
                .withWorkingStatus(WorkingStatus.INACTIVE)
                .withInvestigator(investigatorMinimalDto)
                .build();
        
        attorneyMinimalDto = new AttorneyMinimalDto.AttorneyMinimalDtoBuilder()
                .build();
        
        List<AttorneyMinimalDto> assignedAttorneys = new ArrayList<>();
        assignedAttorneys.add(attorneyMinimalDto);
        activeInvestigatorDto.setAssignedAttorneys(assignedAttorneys);
        
        allAttorneyDtoList = new ArrayList<>();
        allAttorneyDtoList.add(activeAttorneyDto);
        allAttorneyDtoList.add(inactiveAttorneyDto);
        
        activeAttorneyDtoList = new ArrayList<>();
        activeAttorneyDtoList.add(activeAttorneyDto);
        
        attorneyMinimalDtoList = new ArrayList<>();
        attorneyMinimalDtoList.add(attorneyMinimalDto);
        
        allInvestigatorDtoList = new ArrayList<>();
        allInvestigatorDtoList.add(activeInvestigatorDto);
        allInvestigatorDtoList.add(inactiveInvestigatorDto);
        
        activeInvestigatorDtoList = new ArrayList<>();
        activeInvestigatorDtoList.add(activeInvestigatorDto);
        
        attorneyFormDto = new AttorneyFormDtoBuilder().build();
        investigatorFormDto = new InvestigatorFormDtoBuilder().build();
    }
    
    @AfterAll
    public void validate() {
        validateMockitoUsage();
    }
    
    @Test
    public void employeeManagement_ShouldAddEmployeesToModelAndRenderEmployeeManagementView() throws Exception {
        
        when(attorneyServiceMock.findAllActive())
            .thenReturn(activeAttorneyDtoList);
        
        when(investigatorServiceMock.findAllActive())
            .thenReturn(activeInvestigatorDtoList);
                
        mockMvc.perform(get("/employeeManagement"))
            .andExpect(status().isOk())
            .andExpect(view().name("employeeManagement"))
            .andExpect(forwardedUrl("/WEB-INF/views/employeeManagement.jsp"))
            .andExpect(model().attribute("activeInvestigators", hasSize(1)))
            .andExpect(model().attribute("activeInvestigators", contains(activeInvestigatorDto)))
            .andExpect(model().attribute("activeAttorneys", hasSize(1)))
            .andExpect(model().attribute("activeAttorneys", contains(activeAttorneyDto)));
    }
    
    @Test
    public void showNewAttorneyForm_ShouldAddAttorneyFormDtoAndActiveInvestigatorListToModelAndRenderFormView() throws Exception {
        
        when(investigatorServiceMock.findAllActive())
            .thenReturn(activeInvestigatorDtoList);
        
        mockMvc.perform(get("/attorneys/add"))
            .andExpect(status().isOk())
            .andExpect(view().name("attorneys/attorneyForm"))
            .andExpect(forwardedUrl("/WEB-INF/views/attorneys/attorneyForm.jsp"))
            .andExpect(model().attribute("attorneyForm", instanceOf(AttorneyFormDto.class)))
            .andExpect(model().attribute("activeInvestigators", hasSize(1)))
            .andExpect(model().attribute("activeInvestigators", hasItem(
                    allOf(
                            hasProperty("id", is(1L)),
                            hasProperty("name", is("Active Investigator")),
                            hasProperty("workingStatus", is(WorkingStatus.ACTIVE)),
                            hasProperty("assignedAttorneys", is(activeInvestigatorDto.getAssignedAttorneys()))))));
    }
    
    @Test 
    public void save_WhenValidNewAttorney_ShouldSaveAttorney() throws Exception {
        
        attorneyFormDto.setId(null);
        
        when(attorneyServiceMock.save(any(AttorneyFormDto.class)))
            .thenReturn(1L);
        
        mockMvc.perform(post("/attorneys")
            .param("name", "Active Attorney")
            .param("workingStatus", "ACTIVE")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(attorneyFormDto)))
            .andExpect(status().isFound())
            .andExpect(view().name("redirect:/attorneys/1"))
            .andExpect(redirectedUrl("/attorneys/1"))
            .andExpect(flash().attribute("css", is("success")))
            .andExpect(flash().attribute("msg", is("Attorney saved successfully!")));
    }
    
    @Test
    public void showAllAttorneys_ShouldRenderListAttorneysView() throws Exception {
        
        when(attorneyServiceMock.findAll())
            .thenReturn(allAttorneyDtoList);
        
        mockMvc.perform(get("/attorneys"))
            .andExpect(status().isOk())
            .andExpect(view().name("attorneys/listAttorneys"))
            .andExpect(forwardedUrl("/WEB-INF/views/attorneys/listAttorneys.jsp"))
            .andExpect(model().attribute("attorneyList", hasSize(2)))
            .andExpect(model().attribute("attorneyList", hasItem(
                allOf(
                    hasProperty("id", is(1L)),
                    hasProperty("name", is("Active Attorney")),
                    hasProperty("workingStatus", is(WorkingStatus.ACTIVE)),
                    hasProperty("investigator", is(investigatorMinimalDto))))))
            .andExpect(model().attribute("attorneyList", hasItem(
                allOf(
                    hasProperty("id", is(2L)),
                    hasProperty("name", is("Inactive Attorney")),
                    hasProperty("workingStatus", is(WorkingStatus.INACTIVE)),
                    hasProperty("investigator", is(investigatorMinimalDto))))));
    }
    
    @Test
    public void showAttorney_WhenValidRequest_ShouldRenderAttorneyView() throws Exception {
        
        when(attorneyServiceMock.findById(1L))
            .thenReturn(activeAttorneyDto);
        
        mockMvc.perform(get("/attorneys/1"))
            .andExpect(status().isOk())
            .andExpect(view().name("attorneys/showAttorney"))
            .andExpect(forwardedUrl("/WEB-INF/views/attorneys/showAttorney.jsp"))
            .andExpect(model().attribute("attorney", samePropertyValuesAs(activeAttorneyDto)));
    }
    
    @Test
    public void showUpdateAttorneyForm_WhenValidRequest_ShouldRenderUpdateAttorneyForm() throws Exception {
        
        when(attorneyServiceMock.findFormById(1L))
            .thenReturn(attorneyFormDto);
        
        mockMvc.perform(get("/attorneys/1/update"))
            .andExpect(status().isOk())
            .andExpect(view().name("attorneys/attorneyForm"))
            .andExpect(forwardedUrl("/WEB-INF/views/attorneys/attorneyForm.jsp"))
            .andExpect(model().attribute("attorneyForm", samePropertyValuesAs(attorneyFormDto)));
    }
    
    @Test
    public void deleteAttorneyById_WhenValidRequest_ShouldDeleteAndAddMessage() throws Exception {
        
        Long targetId = inactiveAttorneyDto.getId();
        
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
        
        Long targetId = inactiveAttorneyDto.getId();
        
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
            .thenReturn(activeAttorneyDtoList);
        
        mockMvc.perform(get("/investigators/add"))
            .andExpect(status().isOk())
            .andExpect(view().name("investigators/investigatorForm"))
            .andExpect(forwardedUrl("/WEB-INF/views/investigators/investigatorForm.jsp"))
            .andExpect(model().attribute("investigatorForm", instanceOf(InvestigatorFormDto.class)))
            .andExpect(model().attribute("activeAttorneys", hasSize(1)))
            .andExpect(model().attribute("activeAttorneys", hasItem(
                    allOf(
                            hasProperty("id", is(1L)),
                            hasProperty("name", is("Active Attorney")),
                            hasProperty("workingStatus", is(WorkingStatus.ACTIVE)),
                            hasProperty("investigator", is(investigatorMinimalDto))
                    )
            )));
    }
    
    @Test
    public void saveInvestigator_WhenValidInvestigator_ShouldPersistInvestigator() throws Exception {
        
        investigatorFormDto.setId(null);
        
        when(investigatorServiceMock.save(any(InvestigatorFormDto.class)))
            .thenReturn(1L);
        
        mockMvc.perform(post("/investigators")
                .param("name", "Active Investigator")
                .param("workingStatus", "ACTIVE")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(investigatorFormDto)))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/investigators/1"))
                .andExpect(redirectedUrl("/investigators/1"))
                .andExpect(flash().attribute("css", is("success")))
                .andExpect(flash().attribute("msg", is("Investigator saved successfully!"))); 
    }
    
    @Test
    public void viewAllInvestigators_ShouldRenderListInvestigatorsView() throws Exception {
        
        when(investigatorServiceMock.findAll())
            .thenReturn(allInvestigatorDtoList);
        
        mockMvc.perform(get("/investigators"))
                .andExpect(status().isOk())
                .andExpect(view().name("investigators/listInvestigators"))
                .andExpect(forwardedUrl("/WEB-INF/views/investigators/listInvestigators.jsp"))
                .andExpect(model().attribute("investigatorList", hasSize(2)))
                .andExpect(model().attribute("investigatorList", hasItem(
                        allOf(
                                hasProperty("id", is(1L)),
                                hasProperty("name", is("Active Investigator")),
                                hasProperty("workingStatus", is(WorkingStatus.ACTIVE)),
                                hasProperty("assignedAttorneys", is(activeInvestigatorDto.getAssignedAttorneys()))))))
                .andExpect(model().attribute("investigatorList", hasItem(
                        allOf(
                                hasProperty("id", is(2L)),
                                hasProperty("name", is("Inactive Investigator")),
                                hasProperty("workingStatus", is(WorkingStatus.INACTIVE)),
                                hasProperty("assignedAttorneys", is(inactiveInvestigatorDto.getAssignedAttorneys()))))));
    }
    
    @Test
    public void showInvestigator_WhenValidRequest_ShouldRenderInvestigatorView() throws Exception {
        
        when(investigatorServiceMock.findById(1L))
        .thenReturn(activeInvestigatorDto);
    
        mockMvc.perform(get("/investigators/1"))
            .andExpect(status().isOk())
            .andExpect(view().name("investigators/showInvestigator"))
            .andExpect(forwardedUrl("/WEB-INF/views/investigators/showInvestigator.jsp"))
            .andExpect(model().attribute("investigator", samePropertyValuesAs(activeInvestigatorDto)));
    }
    
    @Test
    public void showUpdateInvestigatorForm_WhenValidRequest_ShouldRenderUpdateInvestigatorForm() throws Exception {
        
        when(investigatorServiceMock.findFormById(1L))
        .thenReturn(investigatorFormDto);
    
        mockMvc.perform(get("/investigators/1/update"))
            .andExpect(status().isOk())
            .andExpect(view().name("investigators/investigatorForm"))
            .andExpect(forwardedUrl("/WEB-INF/views/investigators/investigatorForm.jsp"))
            .andExpect(model().attribute("investigatorForm", samePropertyValuesAs(investigatorFormDto)));
    }
    
    @Test
    public void deleteInvestigatorById_WhenValidRequest_ShouldDeleteAndAddMessage() throws Exception {        
        Long targetId = inactiveInvestigatorDto.getId();
        
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
