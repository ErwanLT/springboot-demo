package com.example.validationtutorial.controller;

import com.example.validationtutorial.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    private UserDto createValidUserDto() {
        return new UserDto(
                "testuser",
                "Password@123",
                "test@example.com",
                20,
                "1234567890",
                "https://example.com",
                LocalDate.now(),
                "4111111111111111", // Visa
                Arrays.asList("reading", "coding"),
                Arrays.asList("music", "movies")
        );
    }

    @Test
    void whenValidInput_thenReturns200() throws Exception {
        UserDto userDto = createValidUserDto();
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("User is valid"));
    }

    @Test
    void whenUsernameTooShort_thenReturns400() throws Exception {
        UserDto userDto = createValidUserDto();
        userDto.setUsername("a");
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenInvalidPassword_thenReturns400() throws Exception {
        UserDto userDto = createValidUserDto();
        userDto.setPassword("weak");
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenInvalidEmail_thenReturns400() throws Exception {
        UserDto userDto = createValidUserDto();
        userDto.setEmail("invalid-email");
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenAgeTooLow_thenReturns400() throws Exception {
        UserDto userDto = createValidUserDto();
        userDto.setAge(17);
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenAgeTooHigh_thenReturns400() throws Exception {
        UserDto userDto = createValidUserDto();
        userDto.setAge(101);
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenInvalidPhoneNumber_thenReturns400() throws Exception {
        UserDto userDto = createValidUserDto();
        userDto.setPhoneNumber("123");
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenInvalidURL_thenReturns400() throws Exception {
        UserDto userDto = createValidUserDto();
        userDto.setWebsite("not-a-url");
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenFutureRegistrationDate_thenReturns400() throws Exception {
        UserDto userDto = createValidUserDto();
        userDto.setRegistrationDate(LocalDate.now().plusDays(1));
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenInvalidCreditCard_thenReturns400() throws Exception {
        UserDto userDto = createValidUserDto();
        userDto.setCreditCardNumber("1234");
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenHobbiesEmpty_thenReturns400() throws Exception {
        UserDto userDto = createValidUserDto();
        userDto.setHobbies(Collections.emptyList());
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenHobbiesTooMany_thenReturns400() throws Exception {
        UserDto userDto = createValidUserDto();
        userDto.setHobbies(Arrays.asList("h1", "h2", "h3", "h4", "h5", "h6")); // More than 5
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenPreferencesNull_thenReturns400() throws Exception {
        UserDto userDto = createValidUserDto();
        userDto.setPreferences(null);
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest());
    }
}
