package com.example.EasierSchool.service;

import com.example.EasierSchool.model.TeacherRequest;
import com.example.EasierSchool.model.TeacherResponse;

import java.util.List;

public interface TeacherService {
    TeacherResponse addTeacher(TeacherRequest teacherRequest);
    TeacherResponse getTeacherById(Long id);
    List<TeacherResponse> getTeachers();
}
