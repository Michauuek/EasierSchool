/*
package com.example.EasierSchool.controller;

import com.example.EasierSchool.entity.Room;
import com.example.EasierSchool.entity.Subject;
import com.example.EasierSchool.entity.Teacher;
import com.example.EasierSchool.entity.TimeSlot;
import com.example.EasierSchool.model.LessonFrequency;
import com.example.EasierSchool.model.RoomRequest;
import com.example.EasierSchool.model.TimeSlotRequest;
import com.example.EasierSchool.repository.RoomRepository;
import com.example.EasierSchool.repository.SubjectRepository;
import com.example.EasierSchool.repository.TeacherRepository;
import com.example.EasierSchool.repository.TimeSlotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.example.EasierSchool.model.LessonFrequency.EVERY_WEEK;
import static java.time.DayOfWeek.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class TimeSlotControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private TeacherRepository teacherRepository;

    static private List<TimeSlot> timeSlots;
    private Subject subject;
    private Room room;
    private Teacher teacher;

    @BeforeEach
    void setUp() {
        timeSlotRepository.deleteAllInBatch();
        */
/*room = roomRepository.save(getMockRoom());
        teacher = teacherRepository.save(getTeacherMock());
        subject = subjectRepository.save(new Subject(1, "Math", "Lecture", "31-B", teacher, List.of(getTimeSlotMock()), room));

        timeSlots = new ArrayList<>(Arrays.asList(
                new TimeSlot(1, MONDAY, LocalTime.of(7,30), LocalTime.of(9,0), EVERY_WEEK, subject),
                new TimeSlot(2, TUESDAY, LocalTime.of(7,30), LocalTime.of(9,0), EVERY_WEEK, subject),
                new TimeSlot(3, FRIDAY, LocalTime.of(7,30), LocalTime.of(9,0), EVERY_WEEK, subject)
        ));
        timeSlotRepository.saveAll(timeSlots);*//*

    }

    @Test
    void should_Create_TimeSlot_Successfully() throws Exception {
        this.mockMvc.perform(
                post("/timeslot/")
                        .content(asJsonString(new TimeSlotRequest(MONDAY, LocalTime.of(7,30), LocalTime.of(11,30), EVERY_WEEK, 160L)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void should_Get_All_Time_Slots() throws Exception {
        mockMvc.perform(
                get("/timeslot/all"))
                .andExpect(status().isOk());
    }

    private Subject getMockSubject() {
        return Subject.builder()
                .subjectId(1)
                .name("Math")
                .studentGroup("31-B")
                .room(room)
                .teacher(teacher)
                .build();
    }

    private Teacher getTeacherMock(){
        return Teacher.builder()
                .teacherId(2)
                .name("Thomas")
                .surname("Fox")
                .build();
    }
    private Room getMockRoom(){
        return Room.builder()
                .roomId(5)
                .roomNumber("201B")
                .departmentName("WIEIK")
                .build();
    }

    private TimeSlot getTimeSlotMock(){
        return TimeSlot.builder()
                .timeSlotId(3)
                .frequency(LessonFrequency.EVERY_WEEK)
                .dayOfWeek(DayOfWeek.MONDAY)
                .startTime(LocalTime.of(7,30))
                .subject(getMockSubject())
                .endTime(LocalTime.of(9,0))
                .build();
    }
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}*/
