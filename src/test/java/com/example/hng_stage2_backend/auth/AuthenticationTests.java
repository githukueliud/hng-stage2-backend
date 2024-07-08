package com.example.hng_stage2_backend.auth;

import com.example.hng_stage2_backend.auth.response.SuccessAuthResponse;
import com.example.hng_stage2_backend.user.entity.User;
import com.example.hng_stage2_backend.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import java.util.UUID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthenticationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private AuthenticationService authService;

    @InjectMocks
    private AuthenticationController authController;



    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //live database  doesn't drop users
//    @BeforeEach
//    public void setUp() {
//        // Ensure the user exists in the repository
//        User user = new User();
//        user.setUserId(UUID.randomUUID());
//        user.setEmail("eliud@example.com");
//        user.setPassword(passwordEncoder.encode("password"));
//        user.setFirstName("John");
//        user.setLastName("Doe");
//        user.setPhone("1234567890");
//
//        userRepository.save(user);
//    }

    @Test
    public void testThatUserIsLoggedInSuccessfully() throws Exception {
        // Setup request data
        LoginForm loginForm = new LoginForm("eliud@example.com", "password");

        // Perform the login request
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(loginForm)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // Validate the static parts of the response
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Login successful"))
                .andExpect(jsonPath("$.data.user.email").value("eliud@example.com"))
                .andExpect(jsonPath("$.data.user.firstName").value("John"))
                .andExpect(jsonPath("$.data.user.lastName").value("Doe"))
                .andExpect(jsonPath("$.data.user.phone").value("1234567890"))
                // Validate the dynamic parts (e.g., check if accessToken and userId are non-null and valid)
                .andExpect(jsonPath("$.data.accessToken").isNotEmpty())
                .andExpect(jsonPath("$.data.user.userId").isNotEmpty());
    }

     //Utility method to convert object to JSON string
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




    @Test
    public void testThatUserLoginFails() throws Exception {
        // Create login request
        AuthenticationRequest loginRequest = new AuthenticationRequest();
        loginRequest.setEmail("wrong.email@example.com");
        loginRequest.setPassword("wrongpassword");

        // Expected response
        String expectedResponse = "{\"status\":\"Bad Request\",\"message\":\"Authentication failed\",\"statusCode\":401}";

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().json(expectedResponse));
    }




}
