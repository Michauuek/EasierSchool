package com.example.EasierSchool.controller;

import com.example.EasierSchool.entity.Subject;
import com.example.EasierSchool.entity.Teacher;
import com.example.EasierSchool.model.SubjectRequest;
import com.example.EasierSchool.model.TeacherRequest;
import com.example.EasierSchool.repository.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class TeacherControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TeacherRepository teacherRepository;

    static private List<Teacher> teachers;

    @BeforeEach
    void setUp() {
        teachers = new ArrayList<>(Arrays.asList(
                new Teacher(1, "Thomas", "Fox", Collections.emptySet()),
                new Teacher(2, "Anna", "Panda", Collections.emptySet()),
                new Teacher(3, "Georg", "Horse", Collections.emptySet())
        ));
        teacherRepository.saveAll(teachers);
    }

    @Test
    void should_create_subject_successfully() throws Exception {
        this.mockMvc.perform(
                    post("/teacher/")
                            .content(asJsonString(new TeacherRequest("Michael", "Jordan", Collections.emptySet())))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Michael"));
    }

    @Test
    void should_get_teacher_with_provided_id() throws Exception {
        long teacherId = teacherRepository.findAll().get(0).getTeacherId();
        mockMvc.perform(
                get("/teacher/{id}", teacherId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(teacherId));
    }

    @Test
    void should_get_all_teachers() throws Exception {
        mockMvc.perform(
                get("/teacher/all"))
                .andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}