package com.pdclientmanager.controller;

import static org.mockito.Mockito.validateMockitoUsage;
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
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.pdclientmanager.calendar.CalendarService;
import com.pdclientmanager.calendar.CaseEvent;
import com.pdclientmanager.config.WebConfigTest;
import com.pdclientmanager.repository.DemoDao;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfigTest.class})
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public class HomeControllerTest {
    
    @Profile("test")
    @Configuration
    static class ContextConfiguration {
        
        @Bean
        @Primary
        DemoDao demoDaoMock() {
            return Mockito.mock(DemoDao.class);
        }
        
        @Bean
        @Primary
        CalendarService calendarServiceMock() {
            return Mockito.mock(CalendarService.class);
        }
    }
    
    @Autowired
    private DemoDao demoDaoMock;
    
    @Autowired
    private CalendarService calendarServiceMock;
    
    private MockMvc mockMvc;
    
    @Autowired
    WebApplicationContext wac;
    
    @BeforeAll
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(SecurityMockMvcConfigurers.springSecurity()).build();
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
            .andExpect(view().name("homePage"))
            .andExpect(forwardedUrl("/WEB-INF/views/homePage.jsp"));
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
    public void searchPage_ShouldRenderSearchPageView() throws Exception {
        mockMvc.perform(get("/searchPage"))
            .andExpect(status().isOk())
            .andExpect(view().name("searchPage"))
            .andExpect(forwardedUrl("/WEB-INF/views/searchPage.jsp"));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void individualStats_ShouldRenderIndividualStatsView() throws Exception {
        mockMvc.perform(get("/individualStats"))
            .andExpect(status().isOk())
            .andExpect(view().name("individualStats"))
            .andExpect(forwardedUrl("/WEB-INF/views/individualStats.jsp"));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void officeStats_ShouldRenderOfficeStatsView() throws Exception {
        mockMvc.perform(get("/officeStats"))
            .andExpect(status().isOk())
            .andExpect(view().name("officeStats"))
            .andExpect(forwardedUrl("/WEB-INF/views/officeStats.jsp"));
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
