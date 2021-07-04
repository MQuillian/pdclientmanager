package com.pdclientmanager.controller;

import static org.hamcrest.Matchers.is;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.pdclientmanager.config.SecurityConfigTest;
import com.pdclientmanager.config.WebConfigTest;
import com.pdclientmanager.model.form.JudgeForm;
import com.pdclientmanager.model.projection.JudgeProjection;
import com.pdclientmanager.repository.entity.WorkingStatus;
import com.pdclientmanager.service.JudgeService;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfigTest.class, SecurityConfigTest.class})
@TestInstance(Lifecycle.PER_CLASS)
public class JudgeControllerTest {
    
    @Mock
    JudgeService judgeServiceMock;

    @InjectMocks
    JudgeController controllerUnderTest;
    
    @Autowired
    FilterChainProxy springSecurityFilterChain;
    
    private MockMvc mockMvc;
    
    private ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
    
    private JudgeProjection judgeProjection;
    private List<JudgeProjection> judgeList;
    private JudgeForm judgeForm;
    
    @BeforeAll
    public void setup() {
    	MockitoAnnotations.initMocks(this); 
        this.mockMvc = MockMvcBuilders.standaloneSetup(controllerUnderTest)
        		.apply(SecurityMockMvcConfigurers.springSecurity(springSecurityFilterChain))
        		.build();
    }
        
    @BeforeEach
    public void setupTestData() {
        judgeProjection = factory.createProjection(JudgeProjection.class);
        judgeProjection.setId(1L);
        judgeProjection.setName("Test Judge");
        judgeProjection.setWorkingStatus(WorkingStatus.ACTIVE);
        
        judgeList = new ArrayList<>();
        judgeList.add(judgeProjection);
        
        judgeForm = new JudgeForm.JudgeFormBuilder().build();
    }
    
    @AfterEach
    public void validate() {
        validateMockitoUsage();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void showJudgeManagement_WithValidAuth_ShouldRenderJudgeManagementView() throws Exception {
        mockMvc.perform(get("/judges"))
            .andExpect(status().isOk())
            .andExpect(view().name("judges/judgeManagement"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void showNewJudgeForm_WithValidAuth_ShouldAddFormAndRenderJudgeFormView() throws Exception {
        mockMvc.perform(get("/judges/add"))
            .andExpect(status().isOk())
            .andExpect(view().name("judges/judgeForm"));    }
    
    @Test
    @WithMockUser(roles = "USER")
    public void showNewJudgeForm_WithInvalidAuth_ShouldReturnAccessDeniedView() throws Exception {
        mockMvc.perform(get("/judges/add"))
            .andExpect(status().isForbidden())
            .andExpect(forwardedUrl("/accessDenied.jsp"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void saveJudge_WithValidAuth_ShouldSaveAndRenderJudgeView() throws Exception {
        when(judgeServiceMock.save(any(JudgeForm.class))).thenReturn(1L);
        
        mockMvc.perform(post("/judges"))
            .andExpect(status().isFound())
            .andExpect(view().name("redirect:/judges/1"))
            .andExpect(redirectedUrl("/judges/1"))
            .andExpect(flash().attribute("css", "success"))
            .andExpect(flash().attribute("msg", "Judge saved successfully!"));
    }
    
    @Test
    @WithMockUser(roles = "USER")
    public void saveJudge_WithInvalidAuth_ShouldReturnAccessDeniedView() throws Exception {
        mockMvc.perform(post("/judges"))
            .andExpect(status().isForbidden())
            .andExpect(forwardedUrl("/accessDenied.jsp"));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void showAllJudges_WithValidAuth_ShouldAddJudgeListAndRenderListJudgesView() throws Exception {
        when(judgeServiceMock.findAll()).thenReturn(judgeList);
        
        mockMvc.perform(get("/judges/list"))
            .andExpect(status().isOk())
            .andExpect(view().name("judges/listJudges"))
            .andExpect(model().attribute("judgeList", is(judgeList)));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void showJudge_WithValidAuth_ShouldAddJudgeAndRenderShowJudgeView() throws Exception {
        when(judgeServiceMock.findById(1L, JudgeProjection.class)).thenReturn(judgeProjection);
        
        mockMvc.perform(get("/judges/1"))
            .andExpect(status().isOk())
            .andExpect(view().name("judges/showJudge"))
            .andExpect(model().attribute("judge", judgeProjection));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void updateJudge_WithValidAuth_ShouldAddFormAndRenderUpdateJudgeFormView() throws Exception {
        when(judgeServiceMock.findFormById(1L)).thenReturn(judgeForm);
        
        mockMvc.perform(get("/judges/1/update"))
            .andExpect(status().isOk())
            .andExpect(view().name("judges/judgeForm"))
            .andExpect(model().attribute("judgeForm", is(judgeForm)));
    }
    
    @Test
    @WithMockUser(roles = "USER")
    public void updateJudge_WithInvalidAuth_ShouldReturnAccessDeniedView() throws Exception {
        mockMvc.perform(get("/judges/1/update"))
            .andExpect(status().isForbidden())
            .andExpect(forwardedUrl("/accessDenied.jsp"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void deleteJudge_WithValidAuth_ShouldDeleteAndAddMessageAndRenderListJudgeView() throws Exception {
        mockMvc.perform(post("/judges/1/delete"))
            .andExpect(status().isFound())
            .andExpect(view().name("redirect:/judges/list"))
            .andExpect(redirectedUrl("/judges/list"))
            .andExpect(flash().attribute("css", "success"))
            .andExpect(flash().attribute("msg", "Judge deleted successfully!"));
    }
    
    @Test
    @WithMockUser(roles = "USER")
    public void deleteJudge_WithInvalidAuth_ShouldReturnAccessDeniedView() throws Exception {
        mockMvc.perform(post("/judges/1/delete"))
            .andExpect(status().isForbidden())
            .andExpect(forwardedUrl("/accessDenied.jsp"));
    }
}
