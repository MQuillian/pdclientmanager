package com.pdclientmanager.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.validateMockitoUsage;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.pdclientmanager.config.WebConfigTest;
import com.pdclientmanager.model.projection.ChargeProjection;
import com.pdclientmanager.model.projection.ClientLightProjection;
import com.pdclientmanager.repository.entity.CustodyStatus;
import com.pdclientmanager.service.ChargeService;
import com.pdclientmanager.service.ChargeServiceImpl;
import com.pdclientmanager.service.ClientService;
import com.pdclientmanager.service.ClientServiceImpl;
import com.pdclientmanager.util.json.CustomChargeProjectionSerializer;
import com.pdclientmanager.util.json.CustomClientLightProjectionSerializer;
import com.pdclientmanager.util.json.JsonUtils;


@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfigTest.class})
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public class AutocompleteControllerTest {

    @Profile("test")
    @Configuration
    static class ContextConfiguration {
        
        @Bean
        @Primary
        ClientService clientServiceMock() {
            return Mockito.mock(ClientServiceImpl.class);
        }
        
        @Bean
        @Primary
        ChargeService chargeServiceMock() {
            return Mockito.mock(ChargeServiceImpl.class);
        }
    }
    
    @Autowired
    private ClientService clientServiceMock;
    
    @Autowired
    private ChargeService chargeServiceMock;
    
    private MockMvc mockMvc;
    
    @Autowired
    WebApplicationContext wac;
    
    private ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
    
    private ClientLightProjection clientLightProjection;
    private List<ClientLightProjection> clientList;
    private ChargeProjection chargeProjection;
    private List<ChargeProjection> chargeList;
    
    @BeforeAll
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
    
    @BeforeEach
    public void setupTestData() {
        clientLightProjection = factory.createProjection(ClientLightProjection.class);
        clientLightProjection.setId(1L);
        clientLightProjection.setName("Test Client");
        clientLightProjection.setCustodyStatus(CustodyStatus.IN_CUSTODY);
        
        clientList = new ArrayList<>();
        clientList.add(clientLightProjection);
        
        chargeProjection = factory.createProjection(ChargeProjection.class);
        chargeProjection.setId(1L);
        chargeProjection.setName("Test Charge");
        chargeProjection.setStatute("Test Statute");
        
        chargeList = new ArrayList<>();
        chargeList.add(chargeProjection);
    }
    
    @AfterAll
    public void validate() {
        validateMockitoUsage();
    }
    
    @Test
    public void getClientsByName__WithValidName_WithValidAuth_ShouldReturnResponseEntityContainingClientProjections() throws Exception {
        when(clientServiceMock.findByName("Test")).thenReturn(clientList);
        MvcResult result = mockMvc.perform(get("/autocomplete/clientsByName").param("q", "Test"))
            .andExpect(status().isOk())
            .andReturn();
            
        assertThat(result.getResponse().getContentAsString())
            .isEqualTo(JsonUtils.convertToJsonString(ClientLightProjection.class,
                    clientList, new CustomClientLightProjectionSerializer()));
    }
    
    @Test
    public void getChargesByNameOrStatue_WithValidRequest_ShouldReturnResponseEntityContainingChargeProjections() throws Exception {
        when(chargeServiceMock.findByNameOrStatute("Test")).thenReturn(chargeList);
        MvcResult result = mockMvc.perform(get("/autocomplete/chargesByNameOrStatute").param("q", "Test"))
            .andExpect(status().isOk())
            .andReturn();
        
        assertThat(result.getResponse().getContentAsString())
            .isEqualTo(JsonUtils.convertToJsonString(ChargeProjection.class, 
                    chargeList, new CustomChargeProjectionSerializer()));
    }
}
