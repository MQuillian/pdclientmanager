package com.pdclientmanager.controller;

import static org.mockito.Mockito.validateMockitoUsage;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.pdclientmanager.calendar.CalendarService;
import com.pdclientmanager.calendar.CaseEvent;
import com.pdclientmanager.config.WebConfigTest;
import com.pdclientmanager.repository.DemoDao;
import com.pdclientmanager.service.AttorneyService;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfigTest.class})
@TestInstance(Lifecycle.PER_CLASS)
public class HomeControllerTest {
    
    @Mock
    private DemoDao demoDaoMock;
    
    @Mock
    private CalendarService calendarServiceMock;
    
    @Mock
    private AttorneyService attorneyServiceMock;
    
    private MockMvc mockMvc;
    
    @InjectMocks
    HomeController controllerUnderTest;
    
    @Autowired
    FilterChainProxy springSecurityFilterChain;
    
    @BeforeAll
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controllerUnderTest)
        		.apply(SecurityMockMvcConfigurers.springSecurity(springSecurityFilterChain))
        		.build();
    }
    
    @AfterEach
    public void validate() {
        validateMockitoUsage();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void showHome_WithValidAuth_ShouldRenderHomePageView() throws Exception {
        when(calendarServiceMock.getListOfTwoWeeksEventsForCurrentUser()).thenReturn(new ArrayList<CaseEvent>());
        
        mockMvc.perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(view().name("homePage"));
    }
    
    @Test
    @WithMockUser(roles = "INVALID")
    public void showHome_WithInvalidAuth_ShouldReturnAccessDeniedView() throws Exception {
        mockMvc.perform(get("/"))
            .andExpect(status().isForbidden())
            .andExpect(forwardedUrl("/accessDenied.jsp"));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void resetData_ShouldAddMessageAndRedirectToHomePage() throws Exception {
        mockMvc.perform(post("/reset"))
            .andExpect(status().isFound())
            .andExpect(view().name("redirect:/"))
            .andExpect(redirectedUrl("/"))
            .andExpect(flash().attribute("css", "success"))
            .andExpect(flash().attribute("msg", "Data reset successfully"));
    }
}
