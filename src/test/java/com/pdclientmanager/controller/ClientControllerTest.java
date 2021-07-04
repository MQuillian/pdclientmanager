package com.pdclientmanager.controller;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.validateMockitoUsage;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
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
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.pdclientmanager.config.WebConfigTest;
import com.pdclientmanager.model.form.ClientForm;
import com.pdclientmanager.model.projection.ClientLightProjection;
import com.pdclientmanager.model.projection.ClientProjection;
import com.pdclientmanager.service.ClientService;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfigTest.class})
@TestInstance(Lifecycle.PER_CLASS)
public class ClientControllerTest {
    
    @Mock
    private ClientService clientServiceMock;
    
    private MockMvc mockMvc;
    
    @InjectMocks
    ClientController controllerUnderTest;
    
    private ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
    
    private ClientForm clientForm;
    private ClientLightProjection clientLightProjection;
    private List<ClientLightProjection> clientLightProjectionList;
    private ClientProjection clientProjection;
    
    @BeforeAll
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controllerUnderTest).build();
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
            .andExpect(view().name("clients/clientManagement"));
            
    }

    @Test
    public void showNewClientForm_ShouldAddFormAndRenderClientFormView() throws Exception {
        mockMvc.perform(get("/clients/add"))
            .andExpect(status().isOk())
            .andExpect(view().name("clients/clientForm"))
            .andExpect(model().attribute("clientForm", is(instanceOf(ClientForm.class))));
    }

    @Test
    public void saveClient_WithValidRequest_ShouldAddMessageAndRenderClientView() throws Exception {
        when(clientServiceMock.save(any(ClientForm.class))).thenReturn(1L);
        
        mockMvc.perform(post("/clients")
            .param("name", "Test Client")
            .param("custodyStatus", "IN_CUSTODY")
            .param("incarcerationDate", "2021-01-05")
            .param("releaseDate", ""))
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
            .andExpect(model().attribute("clientList", is(clientLightProjectionList)));
    }

    @Test
    public void showClient_WithValidRequest_ShouldAddClientAndRenderClientView() throws Exception {
        when(clientServiceMock.findById(1L, ClientProjection.class)).thenReturn(clientProjection);
        
        mockMvc.perform(get("/clients/1"))
            .andExpect(status().isOk())
            .andExpect(view().name("clients/showClient"))
            .andExpect(model().attribute("client", clientProjection));
    }
    
    @Test
    public void showUpdateClientForm_ShouldAddFormAndRenderFormView() throws Exception {
        when(clientServiceMock.findFormById(1L)).thenReturn(clientForm);
        
        mockMvc.perform(get("/clients/1/update"))
            .andExpect(status().isOk())
            .andExpect(view().name("clients/clientForm"))
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
