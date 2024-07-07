package com.example.hng_stage2_backend.auth;


import com.example.hng_stage2_backend.config.JwtService;
import com.example.hng_stage2_backend.organization.entity.Organization;
import com.example.hng_stage2_backend.organization.repository.OrganizationRepository;
import com.example.hng_stage2_backend.user.entity.User;
import com.example.hng_stage2_backend.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RegistrationTests {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private OrganizationRepository organizationRepository;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("encodedPassword");
    }

//    @Test
//    void testRegisterUserSuccessfullyWithDefaultOrganization() throws Exception {
//        // Arrange
//        RegisterRequest request = new RegisterRequest();
//        request.setFirstName("John");
//        request.setLastName("Doe");
//        request.setEmail("john.doe@example.com");
//        request.setPassword("password");
//        request.setPhone("1234567890");
//
//        // Mock repository behavior
//        Mockito.when(userRepository.save(Mockito.any())).thenReturn(new User());
//
//        // Act & Assert
//        mockMvc.perform(post("/auth/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isBadRequest())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.status").value("Bad Request"))
//                .andExpect(jsonPath("$.message").value("Registration unsuccessful"))
//                .andExpect(jsonPath("$.statusCode").value(400));
//    }


//    @Test
//    void testLoginUserSuccessfully() throws Exception {
//        // Arrange
//        String email = "john.doe@example.com";
//        String password = "password";
//        String encodedPassword = "encodedPassword";
//
//        // Create a mock User entity with an ID
//        User user = new User();
//        user.setId(UUID.randomUUID()); // Set a random UUID for the ID
//        user.setEmail(email);
//        user.setPassword(encodedPassword);
//
//        // Mock repository behavior
//        Mockito.when(userRepository.findUserByEmail(email)).thenReturn(java.util.Optional.of(user));
//        Mockito.when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);
//
//        // Mock JWT token generation
//        String token = "mockedJwtToken";
//        Mockito.when(jwtService.generateToken(Mockito.any())).thenReturn(token);
//
//        // Act & Assert
//        mockMvc.perform(post("/auth/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"email\":\"john.doe@example.com\",\"password\":\"password\"}"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.status").value("success"))
//                .andExpect(jsonPath("$.message").value("Login successful"))
//                .andExpect(jsonPath("$.data.accessToken").value(token)) //accessToken
//                .andExpect(jsonPath("$.data.user.email").value(email));
//    }




//    @Test
//    void testRegisterUserFailsIfRequiredFieldsAreMissing() throws Exception {
//        RegisterRequest request = new RegisterRequest();
//        request.setEmail("john.doe@example.com");
//        request.setPassword("password");
//        request.setPhone("1234567890");
//
//        mockMvc.perform(post("/auth/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isBadRequest())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.status").value("Bad Request"))
//                .andExpect(jsonPath("$.message").value("Registration unsuccessful"))
//                .andExpect(jsonPath("$.statusCode").value(400));
//    }

//    @Test
//    void testRegisterUserFailsIfDuplicateEmail() throws Exception {
//        // Arrange
//        User existingUser = new User();
//        existingUser.setEmail("john.doe@example.com");
//        Mockito.when(userRepository.findUserByEmail("john.doe@example.com")).thenReturn(java.util.Optional.of(existingUser));
//
//        // Act & Assert
//        mockMvc.perform(post("/auth/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john.doe@example.com\",\"password\":\"password\",\"phone\":\"1234567890\"}"))
//                .andExpect(status().isBadRequest())  // Adjusted to expect 400 Bad Request
//                .andExpect(jsonPath("$.message").value("Registration unsuccessful"));
//    }

}