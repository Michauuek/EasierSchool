package com.example.EasierSchool.service;

import com.example.EasierSchool.entity.Subject;
import com.example.EasierSchool.entity.Teacher;
import com.example.EasierSchool.entity.TimeSlot;
import com.example.EasierSchool.exception.CustomServiceException;
import com.example.EasierSchool.model.SubjectResponse;
import com.example.EasierSchool.model.TeacherRequest;
import com.example.EasierSchool.model.TeacherResponse;
import com.example.EasierSchool.repository.SubjectRepository;
import com.example.EasierSchool.repository.TeacherRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Log4j2
public class TeacherServiceImpl implements TeacherService{

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Override
    public TeacherResponse addTeacher(TeacherRequest teacherRequest) {

        log.info("Searching for subjects leads by teacher");

        var teacherSubjects = subjectRepository
                .findAllById(teacherRequest.getSubjectsId());

        log.info("Subjects amount for this teacher:{}", teacherSubjects.size());

        var teacher = Teacher
                .builder()
                .name(teacherRequest.getName())
                .surname(teacherRequest.getSurname())
                .subjects(new HashSet<>(teacherSubjects))
                .build();
        teacherRepository.save(teacher);


        return TeacherResponse
                .builder()
                .id(teacher.getTeacherId())
                .name(teacher.getName())
                .surname(teacher.getSurname())
                .build();
    }

    @Override
    public TeacherResponse getTeacherById(Long id) {

        var teacher = teacherRepository
                .findById(id)
                .orElseThrow(() -> new CustomServiceException(
                        "Teacher with provided id not found",
                        "NOT_FOUND"));

        return TeacherResponse
                .builder()
                .id(teacher.getTeacherId())
                .name(teacher.getName())
                .surname(teacher.getSurname())
                .build();
    }

    @Override
    public List<TeacherResponse> getTeachers() {

        return teacherRepository
                .findAll()
                .stream()
                .map(teacher -> TeacherResponse
                        .builder()
                        .id(teacher.getTeacherId())
                        .name(teacher.getName())
                        .surname(teacher.getSurname())
                        .build()
                ).collect(Collectors.toList());
    }
}
