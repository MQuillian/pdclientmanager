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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pdclientmanager.config.WebConfigTest;
import com.pdclientmanager.model.form.CaseForm;
import com.pdclientmanager.model.form.ClientForm;
import com.pdclientmanager.model.form.CaseForm.CaseFormDtoBuilder;
import com.pdclientmanager.model.projection.AttorneyLightProjection;
import com.pdclientmanager.model.projection.CaseLightProjection;
import com.pdclientmanager.model.projection.CaseProjection;
import com.pdclientmanager.model.projection.ClientLightProjection;
import com.pdclientmanager.model.projection.ClientProjection;
import com.pdclientmanager.model.projection.JudgeProjection;
import com.pdclientmanager.repository.entity.ChargedCount;
import com.pdclientmanager.repository.entity.WorkingStatus;
import com.pdclientmanager.service.AttorneyService;
import com.pdclientmanager.service.AttorneyServiceImpl;
import com.pdclientmanager.service.CaseService;
import com.pdclientmanager.service.CaseServiceImpl;
import com.pdclientmanager.service.ClientService;
import com.pdclientmanager.service.ClientServiceImpl;
import com.pdclientmanager.service.JudgeService;
import com.pdclientmanager.service.JudgeServiceImpl;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfigTest.class})
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public class ClientControllerTest {
    
    @Profile("test")
    @Configuration
    static class ContextConfiguration {
        
        @Bean
        @Primary
        ClientService clientServiceMock() {
            return Mockito.mock(ClientServiceImpl.class);
        }
    }
    
    @Autowired
    private ClientService clientServiceMock;
    
    private MockMvc mockMvc;
    
    @Autowired
    WebApplicationContext wac;
    
    private ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
    
    private ClientForm clientForm;
    private ClientLightProjection clientLightProjection;
    private List<ClientLightProjection> clientLightProjectionList;
    private ClientProjection clientProjection;
    
    @BeforeAll
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
        
    @BeforeEach
    public void setupTestData() {
        clientForm = new ClientForm.ClientFormBuilder().build();
        
        clientLightProjection = factory.createProjection(ClientLightProjection.class);
        
        clientLightProjectionList = new ArrayList<>();
        clientLightProjectionList.add(clientLightProjection);
        
        clientProjection = factory.createProjection(ClientProjection.class);
    }
    
    @AfterEach
    public void validate() {
        validateMockitoUsage();
    }
    
    @Test
    public void clientManagement_ShouldRenderClientManagementView() throws Exception {
        mockMvc.perform(get("/clients"))
            .andExpect(status().isOk())
            .andExpect(view().name("clients/clientManagement"))
            .andExpect(forwardedUrl("/WEB-INF/views/clients/clientManagement.jsp"));
            
    }

    @Test
    public void showNewClientForm_ShouldAddFormAndRenderClientFormView() throws Exception {
        mockMvc.perform(get("/clients/add"))
            .andExpect(status().isOk())
            .andExpect(view().name("clients/clientForm"))
            .andExpect(forwardedUrl("/WEB-INF/views/clients/clientForm.jsp"))
            .andExpect(model().attribute("clientForm", is(instanceOf(ClientForm.class))));
    }

    @Test
    public void saveClient_WithValidRequest_ShouldAddMessageAndRenderClientView() throws Exception {
        when(clientServiceMock.save(any(ClientForm.class))).thenReturn(1L);
        
        mockMvc.perform(post("/clients")
            .param("name", "Test Client")
            .param("custodyStatus", "IN_CUSTODY"))
            .andExpect(status().isFound())
            .andExpect(view().name("redirect:/clients/1"))
            .andExpect(redirectedUrl("/clients/1"))
            .andExpect(flash().attribute("css", "success"))
            .andExpect(flash().attribute("msg", "Client saved successfully!"));
    }

    @Test
    public void showAllClients_ShouldRenderClientListView() throws Exception {
        when(clientServiceMock.findAllBy(ClientLightProjection.class)).thenReturn(clientLightProjectionList);
        
        mockMvc.perform(get("/clients/list"))
            .andExpect(status().isOk())
            .andExpect(view().name("clients/listClients"))
            .andExpect(forwardedUrl("/WEB-INF/views/clients/listClients.jsp"))
            .andExpect(model().attribute("clientList", is(clientLightProjectionList)));
    }

    @Test
    public void showClient_WithValidRequest_ShouldAddClientAndRenderClientView() throws Exception {
        when(clientServiceMock.findById(1L, ClientProjection.class)).thenReturn(clientProjection);
        
        mockMvc.perform(get("/clients/1"))
            .andExpect(status().isOk())
            .andExpect(view().name("clients/showClient"))
            .andExpect(forwardedUrl("/WEB-INF/views/clients/showClient.jsp"))
            .andExpect(model().attribute("client", clientProjection));
    }
    
    @Test
    public void showUpdateClientForm_ShouldAddFormAndRenderFormView() throws Exception {
        when(clientServiceMock.findFormById(1L)).thenReturn(clientForm);
        
        mockMvc.perform(get("/clients/1/update"))
            .andExpect(status().isOk())
            .andExpect(view().name("clients/clientForm"))
            .andExpect(forwardedUrl("/WEB-INF/views/clients/clientForm.jsp"))
            .andExpect(model().attribute("clientForm", clientForm));
    }

    @Test
    public void deleteClientById_WithValidId_ShouldDeleteClientAndAddMessage() throws Exception {
        mockMvc.perform(post("/clients/1/delete"))
            .andExpect(status().isFound())
            .andExpect(view().name("redirect:/clients"))
            .andExpect(redirectedUrl("/clients"))
            .andExpect(flash().attribute("css", "success"))
            .andExpect(flash().attribute("msg", "Client is deleted!"));
    }
}
