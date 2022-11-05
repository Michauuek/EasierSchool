package com.example.EasierSchool.service;

import com.example.EasierSchool.entity.Room;
import com.example.EasierSchool.entity.Subject;
import com.example.EasierSchool.entity.Teacher;
import com.example.EasierSchool.entity.TimeSlot;
import com.example.EasierSchool.exception.CustomServiceException;
import com.example.EasierSchool.model.LessonFrequency;
import com.example.EasierSchool.model.SubjectRequest;
import com.example.EasierSchool.model.SubjectResponse;
import com.example.EasierSchool.repository.RoomRepository;
import com.example.EasierSchool.repository.SubjectRepository;
import com.example.EasierSchool.repository.TeacherRepository;
import com.example.EasierSchool.repository.TimeSlotRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SubjectServiceImplTest {
    @Mock
    private SubjectRepository subjectRepository;

    @Mock
    private TimeSlotRepository timeSlotRepository;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    SubjectService subjectService = new SubjectServiceImpl();

    @DisplayName("Get Subject - Success Scenario")
    @Test
    void test_When_Get_Subject_Success(){
        //Mocking
        Subject subject = getMockSubject();

        when(subjectRepository.findById(anyLong()))
                .thenReturn(Optional.of(subject));

        //Actual
        SubjectResponse subjectResponse = subjectService.getSubjectById(1L);

        //Verify
        verify(subjectRepository, times(1)).findById(anyLong());

        //Assert
        assertNotNull(subjectResponse);
        assertEquals(subject.getSubjectId(), subjectResponse.getId());

    }

    @DisplayName("Get Subject - Failure Scenario")
    @Test
    void test_When_Get_Subject_Not_Found(){

        when(subjectRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(null));

        CustomServiceException exception = assertThrows(
                CustomServiceException.class,
                () -> subjectService.getSubjectById(anyLong()));


        assertEquals("SUBJECT_NOT_FOUND", exception.getErrorCode());
        assertEquals("Subject with provided id not found", exception.getMessage());

        verify(subjectRepository, times(1)).findById(anyLong());
    }

    @DisplayName("Add Subject - Success Scenario")
    @Test
    void test_When_Add_Subject_Success(){
        Subject subject = getMockSubject();
        SubjectRequest subjectRequest = getMockSubjectRequest();
        Room room = getMockRoom();
        Teacher teacher = getTeacherMock();

        when(roomRepository.findById(anyLong())).thenReturn(Optional.of(room));
        when(teacherRepository.findById(anyLong())).thenReturn(Optional.of(teacher));
        when(subjectRepository.save(any(Subject.class))).thenReturn(subject);

        SubjectResponse subjectResponse = subjectService.addSubject(subjectRequest);

        verify(subjectRepository, times(1)).save(any());

        assertEquals(0, subjectResponse.getId());
    }

    @DisplayName("Add Subject - Failed Scenario - Subject Already Present")
    @Test
    void test_When_Add_Subject_SUBJECT_ALREADY_EXIST_then_Failed(){
        Subject subject = getMockSubject();
        SubjectRequest subjectRequest = getMockSubjectRequest();

        when(subjectRepository.findByName(anyString())).thenReturn(List.of(subject));

        CustomServiceException exception = assertThrows(CustomServiceException.class,
                () -> subjectService.addSubject(subjectRequest));

        assertEquals("SUBJECT_ALREADY_EXISTS", exception.getErrorCode());
        assertEquals("Subject with provided name already exists", exception.getMessage());

        verify(subjectRepository, times(2)).findByName(anyString());
    }

    @DisplayName("Add Subject - Success Scenario")
    @Test
    void test_When_Get_Subjects_Success(){
        Subject subject = getMockSubject();

        when(subjectRepository.findAll())
                .thenReturn(List.of(subject));

        //Actual
        List<SubjectResponse> subjectResponse = subjectService.getSubjects();

        //Verify
        verify(subjectRepository, times(1)).findAll();

        //Assert
        assertNotNull(subjectResponse);
        assertEquals(subject.getSubjectId(), subjectResponse.get(0).getId());
    }

    private SubjectRequest getMockSubjectRequest() {
        return SubjectRequest.builder()
                .name("Math")
                .studentGroup("31-B")
                .teacherId(getTeacherMock().getTeacherId())
                .roomId(getMockRoom().getRoomId())
                .build();
    }


    private Subject getMockSubject() {
        return Subject.builder()
                .subjectId(1)
                .name("Math")
                .studentGroup("31-B")
                .room(getMockRoom())
                .timeSlots(List.of(getTimeSlotMock()))
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