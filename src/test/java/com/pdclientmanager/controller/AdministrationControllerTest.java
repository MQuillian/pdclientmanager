package com.pdclientmanager.controller;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
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
import java.util.Arrays;
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
import com.pdclientmanager.model.form.UserForm;
import com.pdclientmanager.repository.entity.User;
import com.pdclientmanager.service.UserService;
import com.pdclientmanager.service.UserServiceImpl;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfigTest.class})
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public class AdministrationControllerTest {

    @Profile("test")
    @Configuration
    static class ContextConfiguration {
        
        @Bean
        @Primary
        UserService userServiceMock() {
            return Mockito.mock(UserServiceImpl.class);
        }
    }
    
    @Autowired
    UserService userServiceMock;
    
    private MockMvc mockMvc;
    
    @Autowired
    WebApplicationContext wac;
    
    private UserForm basicUserForm;
    private User basicUser;
    private List<User> userList;
    
    @BeforeAll
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(SecurityMockMvcConfigurers.springSecurity()).build();
    }
    
    @BeforeEach
    public void setupTestData() {
        basicUserForm = new UserForm.UserFormBuilder()
            .withId(1L)
            .withUsername("testuser")
            .withEmail("user@pdcm.org")
            .withPassword("userpass")
            .withMatchingPassword("userpass")
            .withRoles(Arrays.asList("USER"))
            .build();
        
        basicUser = new User.UserBuilder()
             .withId(1L)
             .withUsername("Test User")
             .build();
        
        userList = new ArrayList<>();
        userList.add(basicUser);
    }
    
    @AfterAll
    public void validate() {
        validateMockitoUsage();
    }
    
    @Test
    @WithMockUser(roles = "ADMIN")
    public void showAdministrationHome_WithValidAuth_ShouldReturnSystemManagementView() throws Exception {
        mockMvc.perform(get("/admin"))
            .andExpect(status().isOk())
            .andExpect(view().name("administration/systemManagement"))
            .andExpect(forwardedUrl("/WEB-INF/views/administration/systemManagement.jsp"));
    }
    
    @Test
    @WithMockUser(roles = "USER")
    public void showAdministrationHome_WithInvalidAuth_ShouldReturnAccessDeniedView() throws Exception {
        mockMvc.perform(get("/admin"))
            .andExpect(status().isForbidden())
            .andExpect(forwardedUrl("/accessDenied.jsp"));
    }
    
    @Test
    @WithMockUser(roles = "ADMIN")
    public void showNewUserForm__WithValidAuth_ShouldAddNewUserFormAndListOfAvailableRolesToModelAndRenderFormView() throws Exception {
        mockMvc.perform(get("/admin/users/addUser"))
            .andExpect(status().isOk())
            .andExpect(view().name("administration/userForm"))
            .andExpect(forwardedUrl("/WEB-INF/views/administration/userForm.jsp"))
            .andExpect(model().attribute("userForm", instanceOf(UserForm.class)))
            .andExpect(model().attribute("availableRoles", hasItems("ATTORNEY", "INVESTIGATOR","ADMIN")));
    }
    
    @Test
    @WithMockUser(roles = "USER")
    public void showNewUserForm_WithInvalidAuth_ShouldReturnAccessDeniedView() throws Exception {
        mockMvc.perform(get("/admin/users/addUser"))
            .andExpect(status().isForbidden())
            .andExpect(forwardedUrl("/accessDenied.jsp"));
    }
    
    @Test
    @WithMockUser(roles = "ADMIN")
    public void saveUser_WhenValidNewUser_WithValidAuth_ShouldSaveUser() throws Exception{
        basicUserForm.setId(null);
        
        when(userServiceMock.saveUser(any(UserForm.class)))
        .thenReturn(1L);
        
        mockMvc.perform(post("/admin/users")
                .param("fullName", "Full Name")
                .param("username", "testuser")
                .param("email", "user@pdcm.org")
                .param("password", "userpass")
                .param("matchingPassword", "userpass")
                .param("roles", "USER"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/admin"))
                .andExpect(redirectedUrl("/admin"))
                .andExpect(flash().attribute("css", is("success")))
                .andExpect(flash().attribute("msg", is("User saved successfully!")));
    }
    
    @Test
    @WithMockUser(roles = "USER")
    public void saveUser_WithInvalidAuth_ShouldReturnAccessDeniedView() throws Exception {
        mockMvc.perform(post("/admin/users"))
            .andExpect(status().isForbidden())
            .andExpect(forwardedUrl("/accessDenied.jsp"));
    }
    
    @Test
    @WithMockUser(roles = "ADMIN")
    public void showAllUsers_WithValidAuth_shouldRenderListUsersView() throws Exception {
        when(userServiceMock.findAll())
            .thenReturn(userList);
    
        mockMvc.perform(get("/admin/users"))
            .andExpect(status().isOk())
            .andExpect(view().name("administration/listUsers"))
            .andExpect(forwardedUrl("/WEB-INF/views/administration/listUsers.jsp"))
            .andExpect(model().attribute("userList", hasSize(1)))
            .andExpect(model().attribute("userList", hasItem(
                allOf(
                    hasProperty("id", is(1L)),
                    hasProperty("username", is("Test User"))))));
    }
    
    @Test
    @WithMockUser(roles = "USER")
    public void showAllUsers_WithInvalidAuth_ShouldReturnAccessDeniedView() throws Exception {
        mockMvc.perform(get("/admin/users"))
            .andExpect(status().isForbidden())
            .andExpect(forwardedUrl("/accessDenied.jsp"));
    }
    
    @Test
    @WithMockUser(roles = "ADMIN")
    public void showUpdateUserForm_WhenValidRequest_WithValidAuth_ShouldRenderUpdateUserForm() throws Exception {
        when(userServiceMock.findFormById(1L))
            .thenReturn(basicUserForm);
        
        mockMvc.perform(get("/admin/users/1/update"))
            .andExpect(status().isOk())
            .andExpect(view().name("administration/userForm"))
            .andExpect(forwardedUrl("/WEB-INF/views/administration/userForm.jsp"))
            .andExpect(model().attribute("userForm", samePropertyValuesAs(basicUserForm)))
            .andExpect(model().attribute("availableRoles", is(notNullValue())));
    }
    
    @Test
    @WithMockUser(roles = "USER")
    public void showUpdateUserForm_WithInvalidAuth_ShouldReturnAccessDeniedView() throws Exception {
        mockMvc.perform(get("/admin/users/1/update"))
            .andExpect(status().isForbidden())
            .andExpect(forwardedUrl("/accessDenied.jsp"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void deleteUserById_WhenValidRequest_WithValidAuth_ShouldDeleteAndAddMessage() throws Exception {
        Long targetId = 1L;
        
        when(userServiceMock.deleteById(targetId))
            .thenReturn(true);
        
        mockMvc.perform(post("/admin/users/" + targetId + "/delete")
                .param("id", targetId.toString()))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/admin/users"))
                .andExpect(redirectedUrl("/admin/users"))
                .andExpect(flash().attribute("css", is("success")))
                .andExpect(flash().attribute("msg", is("User is deleted!")));
    }
    
    @Test
    @WithMockUser(roles = "USER")
    public void deleteUserById_WithInvalidAuth_ShouldReturnAccessDeniedView() throws Exception {
        mockMvc.perform(post("/admin/users/1/delete"))
            .andExpect(status().isForbidden())
            .andExpect(forwardedUrl("/accessDenied.jsp"));
    }
}
