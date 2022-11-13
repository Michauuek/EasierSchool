package com.example.EasierSchool.controller;


import com.example.EasierSchool.entity.Room;
import com.example.EasierSchool.entity.Subject;
import com.example.EasierSchool.entity.Teacher;
import com.example.EasierSchool.entity.TimeSlot;
import com.example.EasierSchool.model.LessonFrequency;
import com.example.EasierSchool.model.SubjectRequest;
import com.example.EasierSchool.repository.RoomRepository;
import com.example.EasierSchool.repository.SubjectRepository;
import com.example.EasierSchool.repository.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class SubjectControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private RoomRepository roomRepository;

    static private List<Subject> subjects;
    private Teacher teacher;
    private Room room;

    @BeforeEach
    void setUp() {
        subjectRepository.deleteAllInBatch();
        teacher = teacherRepository.save(getTeacherMock());
        room = roomRepository.save(getMockRoom());

        subjects = new ArrayList<>(Arrays.asList(
                new Subject(1, "Math", "Lecture", "31-B", teacher, List.of(getTimeSlotMock()), room),
                new Subject(2, "English", "Lecture", "31-B", teacher, List.of(getTimeSlotMock()), room),
                new Subject(3, "History", "Labs", "31-B", teacher, List.of(getTimeSlotMock()), room)
        ));
        subjectRepository.saveAll(subjects);
    }

    @Test
    void should_create_subject_successfully() throws Exception {
        this.mockMvc.perform(
                post("/subject/")
                    .content(asJsonString(new SubjectRequest("Przedmiot", "Labs", "32-B", teacher.getTeacherId(), room.getRoomId())))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void should_get_subject_with_provided_id() throws Exception {
        long subjectId = subjectRepository.findAll().get(0).getSubjectId();
        mockMvc.perform(
                get("/subject/{id}", subjectId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(subjectId));
    }

    @Test
    void should_get_all_subjects() throws Exception {
        mockMvc.perform(
                get("/subject/all"))
                .andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Teacher getTeacherMock(){
        return Teacher.builder()
                .teacherId(2)
                .name("Thomas")
                .surname("Fox")
                .build();
    }

    private TimeSlot getTimeSlotMock(){
        return TimeSlot.builder()
                .timeSlotId(3)
                .frequency(LessonFrequency.EVERY_WEEK)
                .dayOfWeek(DayOfWeek.MONDAY)
                .startTime(LocalTime.of(7,30))
                .endTime(LocalTime.of(9,0))
                .build();
    }

    private Room getMockRoom(){
        return Room.builder()
                .roomId(5)
                .roomNumber("201B")
                .departmentName("WIEIK")
                .build();
    }
}