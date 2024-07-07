package com.example.hng_stage2_backend.auth;

//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class OrganizationTests {
//    @Autowired
//    private MockMvc mockMvc;
//
//    //ascertains that the user must be logged in to get the organizations
////    @Test
////    void testGetUserOrganizationsRequiresAuthentication() throws Exception {
////        mockMvc.perform(get("/api/organisations")
////                        .contentType(MediaType.APPLICATION_JSON))
////                .andExpect(status().isUnauthorized())
////                .andExpect(jsonPath("$.status").doesNotExist())
////                .andExpect(jsonPath("$.message").doesNotExist())
////                .andExpect(jsonPath("$.data").doesNotExist());
////    }
//
//
////    @Test
////    void testCreateOrganizationRequiresAuthentication() throws Exception {
////        String requestBody = "{\"name\":\"New Organization\",\"description\":\"New Organization Description\"}";
////
////        mockMvc.perform(post("/api/organisations")
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .content(requestBody))
////                .andExpect(status().isUnauthorized())
////                .andExpect(jsonPath("$.status").doesNotExist()) // No status field expected
////                .andExpect(jsonPath("$.message").doesNotExist()) // No message field expected
////                .andExpect(jsonPath("$.data").doesNotExist()); // No data field expected
////    }
//}
