package com.sebembedded.fincrimeinterview.api.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sebembedded.fincrimeinterview.controller.UserController;
import com.sebembedded.fincrimeinterview.model.User;
import com.sebembedded.fincrimeinterview.repository.UserRepository;
import com.sebembedded.fincrimeinterview.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import({UserService.class, UserRepository.class})
class UserControllerTest {

    private static final String BASE_PATH = "/api/user";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void addOrUpdateUser() throws Exception {
        var response = mockMvc.perform(post(BASE_PATH + "/").content("""
                        {
                          "firstName": "test",
                          "lastName": "test",
                          "occupation": "EMPLOYED"
                        }
                        """).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        var user = objectMapper.readValue(response.getResponse().getContentAsByteArray(), User.class);

        assertNotNull(user.id());
    }

    @Test
    void getUser() throws Exception {
        var initial = mockMvc.perform(post(BASE_PATH + "/").content("""
                        {
                          "firstName": "test",
                          "lastName": "test",
                          "occupation": "EMPLOYED"
                        }
                        """).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        var newUser = objectMapper.readValue(initial.getResponse().getContentAsByteArray(), User.class);
        var response = mockMvc.perform(get(BASE_PATH + "/" + newUser.id()))
                .andExpect(status().isOk()).andReturn();
        var user = objectMapper.readValue(response.getResponse().getContentAsByteArray(), User.class);

        assertEquals(newUser.firstName(), user.firstName());
        assertEquals(newUser.lastName(), user.lastName());
        assertEquals(newUser.occupation(), user.occupation());
    }

    @Test
    void queryUser() throws Exception {
        mockMvc.perform(post(BASE_PATH + "/").content("""
                        {
                          "firstName": "test2",
                          "lastName": "test2",
                          "occupation": "EMPLOYED"
                        }
                        """).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        var initial = mockMvc.perform(get(BASE_PATH + "/find-by-name")
                .queryParam("firstName", "test2")
                .queryParam("lastName", "test2")
        ).andExpect(status().isOk()).andReturn();

        var setUser = objectMapper.readValue(initial.getResponse().getContentAsByteArray(), new TypeReference<Set<User>>() {
        });
        var newUser = setUser.stream().findFirst().get();
        assertEquals(newUser.firstName(), "test2");
        assertEquals(newUser.lastName(), "test2");
    }
}