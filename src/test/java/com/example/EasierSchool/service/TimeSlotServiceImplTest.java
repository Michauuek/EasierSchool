package com.example.EasierSchool.service;

import com.example.EasierSchool.entity.Room;
import com.example.EasierSchool.entity.Subject;
import com.example.EasierSchool.entity.Teacher;
import com.example.EasierSchool.entity.TimeSlot;
import com.example.EasierSchool.exception.CustomServiceException;
import com.example.EasierSchool.model.LessonFrequency;
import com.example.EasierSchool.model.TimeSlotRequest;
import com.example.EasierSchool.model.TimeSlotResponse;
import com.example.EasierSchool.repository.SubjectRepository;
import com.example.EasierSchool.repository.TimeSlotRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;


import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TimeSlotServiceImplTest {

    @Mock
    private TimeSlotRepository timeSlotRepository;

    @Mock
    private SubjectRepository subjectRepository;

    @InjectMocks
    TimeSlotService timeSlotService = new TimeSlotServiceImpl();

    @DisplayName("Add TimeSlot - Success Scenario")
    @Test
    void test_When_Add_TimeSlot_Success() {
        Subject subject = getMockSubject();
        TimeSlot timeSlot = getTimeSlotMock();
        TimeSlotRequest timeSlotRequest = getTimeSlotRequestMock();

        when(subjectRepository.findById(anyLong())).thenReturn(Optional.of(subject));
        when(timeSlotRepository.save(any(TimeSlot.class))).thenReturn(timeSlot);

        TimeSlotResponse timeSlotResponse = timeSlotService.addTimeSlot(timeSlotRequest);

        verify(subjectRepository, times(1)).findById(anyLong());
        verify(timeSlotRepository, times(1)).save(any());

        assertEquals(timeSlot.getTimeSlotId(), timeSlotResponse.getId());
        assertEquals(timeSlot.getSubject().getSubjectId(), timeSlotResponse.getSubjectId());
    }

    @DisplayName("Add TimeSlot - Failed Scenario - Subject Not Found")
    @Test
    void test_When_Add_TimeSlot_SUBJECT_NOT_FOUND_then_Failed() {
        Subject subject = getMockSubject();
        TimeSlot timeSlot = getTimeSlotMock();
        TimeSlotRequest timeSlotRequest = getTimeSlotRequestMock();

        when(subjectRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        CustomServiceException exception = assertThrows(CustomServiceException.class,
                () -> timeSlotService.addTimeSlot(timeSlotRequest));

        assertEquals("NOT_FOUND", exception.getErrorCode());
        assertEquals("Subject with provided id not found", exception.getMessage());

        verify(subjectRepository, times(1)).findById(anyLong());
    }

    @DisplayName("Get TimeSlot - Success Scenario")
    @Test
    void test_When_Get_TimeSlot_By_Id_Success() {
        TimeSlot timeSlot = getTimeSlotMock();

        when(timeSlotRepository.findById(anyLong())).thenReturn(Optional.of(timeSlot));

        TimeSlotResponse timeSlotResponse = timeSlotService.getTimeSlotById(1L);

        verify(timeSlotRepository, times(1)).findById(anyLong());

        assertNotNull(timeSlotResponse);
        assertEquals(timeSlot.getTimeSlotId(), timeSlotResponse.getId());
    }

    @DisplayName("Get TimeSlot - Failure Scenario")
    @Test
    void test_When_Get_TimeSlot_By_Id_Failed() {

        when(timeSlotRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(null));

        CustomServiceException exception = assertThrows(
                CustomServiceException.class,
                () -> timeSlotService.getTimeSlotById(anyLong()));


        assertEquals("NOT_FOUND", exception.getErrorCode());
        assertEquals("Time Slot with provided id not found", exception.getMessage());

        verify(timeSlotRepository, times(1)).findById(anyLong());
    }

    @DisplayName("Get TimeSlots - Success Scenario")
    @Test
    void test_When_Get_TimeSlots_Success() {
        TimeSlot timeSlot = getTimeSlotMock();

        when(timeSlotRepository.findAll()).thenReturn(List.of(timeSlot));

        List<TimeSlotResponse> timeSlotResponses = timeSlotService.getTimeSlots();

        verify(timeSlotRepository, times(1)).findAll();

        assertNotNull(timeSlotResponses);
        assertEquals(timeSlot.getTimeSlotId(), timeSlotResponses.get(0).getId());
    }


    private TimeSlot getTimeSlotMock(){
        return TimeSlot.builder()
                .timeSlotId(0)
                .frequency(LessonFrequency.EVERY_WEEK)
                .dayOfWeek(DayOfWeek.MONDAY)
                .startTime(LocalTime.of(7,30))
                .endTime(LocalTime.of(9,0))
                .subject(getMockSubject())
                .build();
    }

    private TimeSlotRequest getTimeSlotRequestMock(){
        return TimeSlotRequest.builder()
                .frequency(LessonFrequency.EVERY_WEEK)
                .dayOfWeek(DayOfWeek.MONDAY)
                .startTime(LocalTime.of(7,30))
                .endTime(LocalTime.of(9,0))
                .subjectId(getMockSubject().getSubjectId())
                .build();
    }

    private Subject getMockSubject() {
        return Subject.builder()
                .subjectId(1)
                .name("Math")
                .studentGroup("31-B")
                .room(getMockRoom())
                .teacher(getTeacherMock())
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
}