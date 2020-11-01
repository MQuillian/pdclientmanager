package com.pdclientmanager.controller;

import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.validateMockitoUsage;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.AfterEach;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.pdclientmanager.config.WebConfigTest;
import com.pdclientmanager.model.form.ChargeForm;
import com.pdclientmanager.model.projection.ChargeProjection;
import com.pdclientmanager.repository.entity.Charge;
import com.pdclientmanager.service.ChargeService;
import com.pdclientmanager.service.ChargeServiceImpl;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfigTest.class})
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public class ChargeControllerTest {
    
    @Profile("test")
    @Configuration
    static class ContextConfiguration {
        
        @Bean
        @Primary
        ChargeService chargeServiceMock() {
            return Mockito.mock(ChargeServiceImpl.class);
        }
    }
    
    @Autowired
    private ChargeService chargeServiceMock;
    
    private MockMvc mockMvc;
    
    @Autowired
    WebApplicationContext wac;
    
    private ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
    
    private ChargeForm chargeForm;
    private ChargeProjection chargeProjection;
    private List<ChargeProjection> chargeProjectionList;
    
    @BeforeAll
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(SecurityMockMvcConfigurers.springSecurity()).build();
    }
        
    @BeforeEach
    public void setupTestData() {
        chargeForm = new ChargeForm.ChargeFormBuilder()
            .withName("Test Charge")
            .withStatute("0101010101")
            .build();
        
        chargeProjection = factory.createProjection(ChargeProjection.class);
        
        chargeProjectionList = new ArrayList<>();
        chargeProjectionList.add(chargeProjection);
    }
    
    @AfterEach
    public void validate() {
        validateMockitoUsage();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void showChargeManagement_ShouldRenderChargeManagementView() throws Exception {
        mockMvc.perform(get("/charges"))
            .andExpect(status().isOk())
            .andExpect(view().name("charges/chargeManagement"))
            .andExpect(forwardedUrl("/WEB-INF/views/charges/chargeManagement.jsp"));
    }
    
    @Test
    @WithMockUser(roles = "ADMIN")
    public void showNewChargeForm_WithValidAuth_ShouldAddFormToModelAndRenderFormView() throws Exception {
        mockMvc.perform(get("/charges/add"))
            .andExpect(status().isOk())
            .andExpect(view().name("charges/chargeForm"))
            .andExpect(forwardedUrl("/WEB-INF/views/charges/chargeForm.jsp"))
            .andExpect(model().attribute("chargeForm", instanceOf(ChargeForm.class)));
    }
    
    @Test
    @WithMockUser(roles = "USER")
    public void showNewChargeForm_WithInvalidAuth_ShouldReturnAccessDeniedView() throws Exception {
        mockMvc.perform(get("/charges/add"))
            .andExpect(status().isForbidden())
            .andExpect(forwardedUrl("/accessDenied.jsp"));
    }
    
    @Test
    @WithMockUser(roles = "ADMIN")
    public void saveCharge_WithValidForm_WithValidAuth_ShouldSaveFormAndRedirectToChargeView() throws Exception {
        when(chargeServiceMock.save(any(ChargeForm.class))).thenReturn(1L);
        
        mockMvc.perform(post("/charges")
            .param("name", "Test Charge")
            .param("statute", "0101010101"))
            .andExpect(status().isFound())
            .andExpect(view().name("redirect:/charges/1"))
            .andExpect(redirectedUrl("/charges/1"));
    }
    
    @Test
    @WithMockUser(roles = "USER")
    public void saveCharge_WithInvalidAuth_ShouldReturnAccessDeniedView() throws Exception {
        mockMvc.perform(post("/charges"))
            .andExpect(status().isForbidden())
            .andExpect(forwardedUrl("/accessDenied.jsp"));
    }
    
    @Test
    @WithMockUser(roles = "USER")
    public void showAllCharges_ShouldAddChargeListAndRenderListChargesView() throws Exception {
        when(chargeServiceMock.findAll()).thenReturn(chargeProjectionList);
        
        mockMvc.perform(get("/charges/list"))
            .andExpect(status().isOk())
            .andExpect(view().name("charges/listCharges"))
            .andExpect(forwardedUrl("/WEB-INF/views/charges/listCharges.jsp"))
            .andExpect(model().attribute("chargeList", is(chargeProjectionList)));
    }
    
    @Test
    @WithMockUser(roles = "USER")
    public void showCharge_WithValidId_ShouldAddChargeAndRenderShowChargeView() throws Exception {
        when(chargeServiceMock.findById(1L, ChargeProjection.class))
            .thenReturn(chargeProjection);
        
        mockMvc.perform(get("/charges/1"))
            .andExpect(status().isOk())
            .andExpect(view().name("charges/showCharge"))
            .andExpect(forwardedUrl("/WEB-INF/views/charges/showCharge.jsp"))
            .andExpect(model().attribute("charge", is(chargeProjection)));
    }
    
    @Test
    @WithMockUser(roles = "USER")
    public void showCharge_WithInvalidID_ShouldAddErrorMessageAndRenderChargesListView() throws Exception {
        when(chargeServiceMock.findById(1L, ChargeProjection.class)).thenThrow(new EntityNotFoundException());
        
        mockMvc.perform(get("/charges/1"))
            .andExpect(status().isFound())
            .andExpect(view().name("redirect:/charges/list"))
            .andExpect(redirectedUrl("/charges/list"))
            .andExpect(flash().attribute("css", "danger"))
            .andExpect(flash().attribute("msg", "Charge could not be found"));
    }
    
    @Test
    @WithMockUser(roles = "ADMIN")
    public void updateCharge_WithValidAuth_ShouldAddChargeFormAndRenderChargeFormView() throws Exception {
        when(chargeServiceMock.findFormById(1L)).thenReturn(chargeForm);
        
        mockMvc.perform(get("/charges/1/update"))
            .andExpect(status().isOk())
            .andExpect(view().name("charges/chargeForm"))
            .andExpect(forwardedUrl("/WEB-INF/views/charges/chargeForm.jsp"))
            .andExpect(model().attribute("chargeForm", is(chargeForm)));
    }
    
    @Test
    @WithMockUser(roles = "USER")
    public void updateCharge_WithInvalidAuth_ShouldReturnAccessDeniedView() throws Exception {
        mockMvc.perform(post("/charges/1/update"))
            .andExpect(status().isForbidden())
            .andExpect(forwardedUrl("/accessDenied.jsp"));
    }
    
    @Test
    @WithMockUser(roles = "ADMIN")
    public void deleteCharge_WithValidRequest_WithValidAuth_ShouldAddMessageAndRenderChargeListView() throws Exception {
        mockMvc.perform(post("/charges/1/delete"))
            .andExpect(status().isFound())
            .andExpect(view().name("redirect:/charges/list"))
            .andExpect(redirectedUrl("/charges/list"))
            .andExpect(flash().attribute("css", "success"))
            .andExpect(flash().attribute("msg", "Charge deleted successfully!"));
    }
    
    @Test
    @WithMockUser(roles = "USER")
    public void deleteCharge_WithInvalidAuth_ShouldReturnAccessDeniedView() throws Exception {
        mockMvc.perform(post("/charges/1/delete"))
            .andExpect(status().isForbidden())
            .andExpect(forwardedUrl("/accessDenied.jsp"));
    }
}