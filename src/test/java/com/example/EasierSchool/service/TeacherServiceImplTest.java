package com.example.EasierSchool.service;

import com.example.EasierSchool.entity.Subject;
import com.example.EasierSchool.entity.Teacher;
import com.example.EasierSchool.exception.CustomServiceException;
import com.example.EasierSchool.model.TeacherRequest;
import com.example.EasierSchool.model.TeacherResponse;
import com.example.EasierSchool.repository.SubjectRepository;
import com.example.EasierSchool.repository.TeacherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TeacherServiceImplTest {

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private SubjectRepository subjectRepository;

    @InjectMocks
    TeacherServiceImpl teacherService = new TeacherServiceImpl();

    @DisplayName("Add teacher - Success Scenario")
    @Test
    void test_When_Add_Teacher_Success() {
        Teacher teacher = getTeacherMock();
        TeacherRequest teacherRequest = getTeacherRequestMock();

        when(teacherRepository.save(any(Teacher.class))).thenReturn(teacher);

        TeacherResponse teacherResponse = teacherService.addTeacher(teacherRequest);

        verify(teacherRepository, times(1)).save(any());

        assertEquals(teacher.getTeacherId(), teacherResponse.getId());
    }

    @DisplayName("Get Teacher - Success Scenario")
    @Test
    void test_When_Get_Teacher_By_Id_Success() {
        Teacher teacher = getTeacherMock();

        when(teacherRepository.findById(anyLong()))
                .thenReturn(Optional.of(teacher));

        TeacherResponse teacherResponse = teacherService.getTeacherById(0L);

        verify(teacherRepository, times(1)).findById(anyLong());

        assertNotNull(teacherResponse);
        assertEquals(teacher.getTeacherId(), teacherResponse.getId());
    }

    @DisplayName("Get Teacher - Failure Scenario")
    @Test
    void test_When_Get_Teacher_Not_Found(){

        when(teacherRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(null));

        CustomServiceException exception = assertThrows(
                CustomServiceException.class,
                () -> teacherService.getTeacherById(anyLong()));

        assertEquals("NOT_FOUND", exception.getErrorCode());
        assertEquals("Teacher with provided id not found", exception.getMessage());

        verify(teacherRepository, times(1)).findById(anyLong());
    }

    @DisplayName("Get Teachers - Success Scenario")
    @Test
    void getTeachers() {
        Teacher teacher = getTeacherMock();

        when(teacherRepository.findAll())
                .thenReturn(List.of(teacher));

        List<TeacherResponse> teachers = teacherService.getTeachers();

        verify(teacherRepository, times(1)).findAll();

        assertNotNull(teachers);
        assertEquals(teacher.getTeacherId(), teachers.get(0).getId());
    }

    private Teacher getTeacherMock(){
        return Teacher.builder()
                .teacherId(0)
                .name("Thomas")
                .surname("Fox")
                .subjects(Set.of(getMockSubject()))
                .build();
    }

    private TeacherRequest getTeacherRequestMock(){
        return TeacherRequest.builder()
                .name("Thomas")
                .surname("Fox")
                .subjectsId(Set.of(getMockSubject().getSubjectId()))
                .build();
    }

    private Subject getMockSubject() {
        return Subject.builder()
                .subjectId(1)
                .name("Math")
                .studentGroup("31-B")
                .build();
    }
}