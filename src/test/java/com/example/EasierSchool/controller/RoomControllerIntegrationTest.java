package com.example.EasierSchool.controller;

import com.example.EasierSchool.entity.Room;
import com.example.EasierSchool.entity.Subject;
import com.example.EasierSchool.model.RoomRequest;
import com.example.EasierSchool.model.SubjectRequest;
import com.example.EasierSchool.repository.RoomRepository;
import com.example.EasierSchool.repository.SubjectRepository;
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

import static java.util.Collections.emptyList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class RoomControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    static private List<Room> rooms;
    private Subject subject;

    @BeforeEach
    void setUp() {
        subject = subjectRepository.save(getMockSubject());
        rooms = new ArrayList<>(Arrays.asList(
                new Room(1, "201G", "WIEIK", List.of(subject)),
                new Room(2, "202H", "WIEIK", List.of(subject)),
                new Room(3, "203A", "WIEIK", List.of(subject)),
                new Room(4, "204K", "WIEIK", List.of(subject))
        ));
        roomRepository.saveAll(rooms);
    }


    @Test
    void should_Create_Room_Successfully() throws Exception {
        this.mockMvc.perform(
                    post("/room/")
                            .content(asJsonString(new RoomRequest("3", "WIEIK")))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void should_Get_Room_With_Provided_Id() throws Exception {
        long roomId = roomRepository.findAll().get(0).getRoomId();
        mockMvc.perform(
                        get("/room/{id}", roomId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(roomId));
    }
    @Test
    void should_Get_All_Rooms() throws Exception {
        mockMvc.perform(
                get("/room/all"))
                .andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Subject getMockSubject() {
        return Subject.builder()
                .subjectId(1)
                .name("Math")
                .studentGroup("31-B")
                .build();
    }
}