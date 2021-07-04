package com.pdclientmanager.controller;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.validateMockitoUsage;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.pdclientmanager.calendar.CalendarService;
import com.pdclientmanager.calendar.CaseEvent;
import com.pdclientmanager.config.WebConfigTest;
import com.pdclientmanager.model.form.CaseForm;
import com.pdclientmanager.model.form.CaseForm.CaseFormDtoBuilder;
import com.pdclientmanager.model.projection.AttorneyLightProjection;
import com.pdclientmanager.model.projection.CaseLightProjection;
import com.pdclientmanager.model.projection.CaseProjection;
import com.pdclientmanager.model.projection.JudgeProjection;
import com.pdclientmanager.repository.entity.ChargedCount;
import com.pdclientmanager.repository.entity.WorkingStatus;
import com.pdclientmanager.service.AttorneyService;
import com.pdclientmanager.service.CaseService;
import com.pdclientmanager.service.JudgeService;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration(value = "WebConfigTest.class")
@ContextConfiguration(classes = {WebConfigTest.class})
@TestInstance(Lifecycle.PER_CLASS)
public class CaseControllerTest {
    
    @Mock
    private CaseService caseServiceMock;
    
    @Mock
    private AttorneyService attorneyServiceMock;
    
    @Mock
    private JudgeService judgeServiceMock;
    
    @Mock
    private CalendarService calendarServiceMock;
    
    private MockMvc mockMvc;
    
    @InjectMocks
    CaseController controllerUnderTest;
    
    private ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
    
    private AttorneyLightProjection attorneyProjection;
    private List<AttorneyLightProjection> attorneyList;
    private JudgeProjection judgeProjection;
    private List<JudgeProjection> judgeList;
    private CaseForm caseForm;
    private CaseLightProjection caseLightProjection;
    private Page<CaseLightProjection> caseLightProjectionPage;
    private CaseProjection caseProjection;
    private Page<CaseProjection> caseProjectionPage;
    
    @BeforeAll
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controllerUnderTest)
        		.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
        		.build();
    }
        
    @BeforeEach
    public void setupTestData() {
        attorneyProjection = factory.createProjection(AttorneyLightProjection.class);
        attorneyProjection.setId(1L);
        attorneyProjection.setName("Test Attorney");
        attorneyProjection.setWorkingStatus(WorkingStatus.ACTIVE);
        
        attorneyList = new ArrayList<>();
        attorneyList.add(attorneyProjection);
        
        judgeProjection = factory.createProjection(JudgeProjection.class);
        judgeProjection.setId(1L);
        judgeProjection.setName("Test Judge");
        judgeProjection.setWorkingStatus(WorkingStatus.ACTIVE);
        
        judgeList = new ArrayList<>();
        judgeList.add(judgeProjection);
        
        caseForm = new CaseFormDtoBuilder()
                .withChargedCountsIds(new TreeMap<>())
                .build();
        caseForm.addChargedCount(new ChargedCount.ChargedCountBuilder().build());
            
        caseLightProjection = factory.createProjection(CaseLightProjection.class);
        List<CaseLightProjection> caseLightProjectionList = new ArrayList<>();
        caseLightProjectionList.add(caseLightProjection);
        caseLightProjectionPage = new PageImpl<>(caseLightProjectionList);
        
        caseProjection = factory.createProjection(CaseProjection.class);
        caseProjection.setId(1L);
        List<CaseProjection> caseProjectionList = new ArrayList<>();
        caseProjectionPage = new PageImpl<>(caseProjectionList);
    }
    
    @AfterEach
    public void validate() {
        validateMockitoUsage();
    }
    
    @Test
    public void caseManagement_ShouldRenderCaseManagementView() throws Exception {
    	 	
    	mockMvc.perform(get("/cases"))
            .andExpect(status().isOk())
            .andExpect(view().name("cases/caseManagement"));
    }
    
    @Test
    public void showNewCaseForm_ShouldAddActiveEntitesToModelAndRenderFormView() throws Exception {
        when(attorneyServiceMock.findAllActive()).thenReturn(attorneyList);
        when(judgeServiceMock.findAllActive()).thenReturn(judgeList);
        
        mockMvc.perform(get("/cases/add"))
            .andExpect(status().isOk())
            .andExpect(view().name("cases/caseForm"))
            .andExpect(model().attribute("caseForm", instanceOf(CaseForm.class)))
            .andExpect(model().attribute("activeAttorneys", hasSize(1)))
            .andExpect(model().attribute("activeAttorneys", hasItem(
                    allOf(
                            hasProperty("id", is(1L)),
                            hasProperty("name", is("Test Attorney")),
                            hasProperty("workingStatus", is(WorkingStatus.ACTIVE))))))
            .andExpect(model().attribute("activeJudges", hasSize(1)))
            .andExpect(model().attribute("activeJudges", hasItem(
                    allOf(
                            hasProperty("id", is(1L)),
                            hasProperty("name", is("Test Judge")),
                            hasProperty("workingStatus", is(WorkingStatus.ACTIVE))))));
    }
    
    @Test 
    public void save_WhenValidNewCase_ShouldSaveCase() throws Exception {
        caseForm.setId(null);
       
        when(caseServiceMock.save(any(CaseForm.class)))
            .thenReturn(1L);
        mockMvc.perform(post("/cases")
            .param("dateOpened", "01/01/2010")
            .param("dateClosed", "")
            .param("caseNumber", "00J0001")
            .param("clientId", "1")
            .param("judgeId", "1")
            .param("attorneyId", "1")
            .param("chargedCountsIds[1]", "1"))
            .andExpect(status().isFound())
            .andExpect(view().name("redirect:/cases/1"))
            .andExpect(redirectedUrl("/cases/1"))
            .andExpect(flash().attribute("css", is("success")))
            .andExpect(flash().attribute("msg", is("Case saved successfully!")));
    }

    @Test
    public void showAllCases_ShouldRenderListCasesView() throws Exception {
        when(caseServiceMock.findAll(any(Pageable.class), eq(CaseLightProjection.class)))
            .thenReturn(caseLightProjectionPage);
        
        mockMvc.perform(get("/cases/list"))
            .andExpect(status().isOk())
            .andExpect(view().name("cases/listCases"))
            .andExpect(model().attribute("cases", is(caseLightProjectionPage.getContent())))
            .andExpect(model().attribute("page", is(caseLightProjectionPage.getNumber())))
            .andExpect(model().attribute("totalPages", is(caseLightProjectionPage.getTotalPages())))
            .andExpect(model().attribute("size", is(caseLightProjectionPage.getSize())));
            
    }
    
    @Test
    public void showCase_WithValidId_ShouldAddCaseToModelAndRenderCaseView() throws Exception {
    	List<CaseEvent> caseEvents = new ArrayList<>();
        caseEvents.add(new CaseEvent("TestId", caseProjection.getCaseNumber(), "Test Attorney",
        		"Test Description", "Test Summary", LocalDateTime.now(), LocalDateTime.now()));
    	
    	when(caseServiceMock.findById(1L, CaseProjection.class)).thenReturn(caseProjection);
    	when(calendarServiceMock.getListOfAllEventsByCaseNumber(caseProjection.getCaseNumber()))
    		.thenReturn(caseEvents);
    	
        mockMvc.perform(get("/cases/1"))
            .andExpect(status().isOk())
            .andExpect(view().name("cases/showCase"))
            .andExpect(model().attribute("courtCase", is(caseProjection)))
    		.andExpect(model().attribute("caseEvents", is(caseEvents)));
    }

    @Test
    public void showSearchedCases_WithValidRequest_ShouldAddCaseProjectionPageAndRenderCaseListView() throws Exception {
        when(caseServiceMock.findAllWithClientName(any(Pageable.class), eq("Test"), eq(CaseProjection.class)))
            .thenReturn(caseProjectionPage);
        
        mockMvc.perform(get("/cases/list/searchResults")
            .param("q", "Test"))
            .andExpect(status().isOk())
            .andExpect(view().name("cases/listCases"))
            .andExpect(model().attribute("cases", is(caseProjectionPage.getContent())))
            .andExpect(model().attribute("page", is(caseProjectionPage.getNumber())))
            .andExpect(model().attribute("totalPages", is(caseProjectionPage.getTotalPages())))
            .andExpect(model().attribute("size", is(caseProjectionPage.getSize())));
    }

    @Test
    public void showUpdateCaseForm_WhenValidRequest_ShouldRenderUpdateCaseForm() throws Exception {
        when(caseServiceMock.findFormById(1L)).thenReturn(caseForm);
        when(attorneyServiceMock.findAllActive()).thenReturn(attorneyList);
        when(judgeServiceMock.findAllActive()).thenReturn(judgeList);
        
        mockMvc.perform(get("/cases/1/update"))
            .andExpect(status().isOk())
            .andExpect(view().name("cases/caseForm"))
            .andExpect(model().attribute("caseForm", is(caseForm)))
            .andExpect(model().attribute("activeAttorneys", is(attorneyList)))
            .andExpect(model().attribute("activeJudges", is(judgeList)));
    }
    
    @Test
    public void showReassignmentForm_ShouldAddAttorneysToModelAndRenderReassignmentForm() throws Exception {
        when(attorneyServiceMock.findAllActive()).thenReturn(attorneyList);
        
        mockMvc.perform(get("/cases/reassignment"))
            .andExpect(status().isOk())
            .andExpect(view().name("cases/reassignment"))
            .andExpect(model().attribute("activeAttorneys", is(attorneyList)));
    }

    @Test
    public void reassignCaseload_WithValidRequestForOpenCases_ShouldReassignCasesAndRedirectToAttorneyView() throws Exception {
        mockMvc.perform(post("/cases/reassignment")
            .param("prevAssignedAttorneyId", "1")
            .param("newAssignedAttorneyId", "2")
            .param("reassignedCases", "openCases"))
            .andExpect(status().isFound())
            .andExpect(redirectedUrl("/attorneys/2"));
    }
    
    @Test void reassignCaseLoad_WithValidRequestForAllCases_ShouldReassignCasesAndRedirectToAttorneyView() throws Exception {
        mockMvc.perform(post("/cases/reassignment")
                .param("prevAssignedAttorneyId", "1")
                .param("newAssignedAttorneyId", "2")
                .param("reassignedCases", "aaCases"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/attorneys/2"));
    }

    @Test
    public void deleteCaseById_WithValidId_ShouldCallDeleteMethodAndAddMessage() throws Exception {
        mockMvc.perform(post("/cases/1/delete"))
            .andExpect(status().isFound())
            .andExpect(redirectedUrl("/cases"))
            .andExpect(flash().attribute("css", "success"))
            .andExpect(flash().attribute("msg", "Case is deleted!"));
    }
}
