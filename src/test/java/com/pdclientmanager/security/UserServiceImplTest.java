package com.pdclientmanager.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.validateMockitoUsage;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import com.pdclientmanager.config.WebConfigTest;
import com.pdclientmanager.model.form.UserForm;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfigTest.class})
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public class UserServiceImplTest {
    
    @Mock
    private UserRepository userRepositoryMock;
    
    @Mock
    private BCryptPasswordEncoder encoderMock;
    
    private UserService userService;
    private User testUser;
    private UserForm testForm;
    private List<User> userList;
    
    @BeforeAll
    public void setupTestService() {
        initMocks(this);
        userService = new UserServiceImpl(userRepositoryMock, encoderMock);
    }
    
    @BeforeEach
    public void setupTestData() {
        testUser = new User.UserBuilder()
            .withId(1L)
            .withUsername("Test User")
            .withPassword("testpass").build();
        Set<Authority> testAuthorities= new HashSet<>();
        testAuthorities.add(new Authority(1L, "ROLE_USER", testUser));
        testAuthorities.add(new Authority(2L, "ROLE_ADMIN", testUser));
        testUser.setAuthorities(testAuthorities);
        
        testForm = new UserForm.UserFormBuilder()
            .withId(1L)
            .withUsername("Test User")
            .withPassword("testpass")
            .withRoles(Arrays.asList("USER", "ADMIN")).build();
        
        userList = new ArrayList<>();
        userList.add(testUser);
    }
    
    @AfterAll
    public void validate() {
        validateMockitoUsage();
    }
    
    @Test
    public void loadUserByUsername_WithValidRequest_ShouldReturnUserDetails() throws Exception {
        when(userRepositoryMock.findByUsername("Test User")).thenReturn(testUser);
        
        UserDetails result = userService.loadUserByUsername("Test User");
        
        assertThat(result.equals(testUser));
    }
    
    @SuppressWarnings("unlikely-arg-type")
    @Test
    public void saveUser_WithNewUser_ShouldMapFormToUserAndSave() throws Exception {
        testForm.setId(null);
        
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        doNothing().when(userRepositoryMock).save(captor.capture());
        when(encoderMock.encode("testpass")).thenReturn("encodedpass");
        
        userService.saveUser(testForm);
        
        User savedUser = captor.getValue();
        assertThat(savedUser.getId()).isEqualTo(testForm.getId());
        assertThat(savedUser.getUsername()).isEqualTo(testForm.getUsername());
        assertThat(savedUser.getPassword()).isEqualTo("encodedpass");
        assertThat(savedUser.getEmail()).isEqualTo(testForm.getEmail());
        assertThat(savedUser.getAuthorities().contains("ROLE_ADMIN"));
        assertThat(savedUser.getAuthorities().contains("ROLE_USER"));
    }
    
    @Test
    public void findFormById_WhenValidRequest_MapsUserToFormAndReturnsForm() throws Exception {
        when(userRepositoryMock.findById(1L)).thenReturn(testUser);
        
        UserForm resultForm = userService.findFormById(1L);
        
        assertThat(resultForm.getId()).isEqualTo(testUser.getId());
        assertThat(resultForm.getUsername()).isEqualTo(testUser.getUsername());
        assertThat(resultForm.getPassword()).isEqualTo(testUser.getPassword());
        assertThat(resultForm.getRoles().contains("USER"));
        assertThat(resultForm.getRoles().contains("ADMIN"));
    }
    
    @Test
    public void findAll_ReturnsUserList() throws Exception {
        when(userRepositoryMock.findAll()).thenReturn(userList);
        
        List<User> result = userService.findAll();
        
        assertThat(result).isEqualTo(userList);
    }
    
    @Test
    public void deleteById_WithValidId_ShouldDeleteUserAndReturnTrue() throws Exception {
        when(userRepositoryMock.findById(1L)).thenReturn(testUser);
        
        assertThat(userService.deleteById(1L));
    }
}

