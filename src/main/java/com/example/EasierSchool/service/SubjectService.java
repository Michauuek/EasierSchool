package com.example.EasierSchool.service;

import com.example.EasierSchool.entity.Subject;
import com.example.EasierSchool.model.SubjectRequest;

import java.util.List;

public interface SubjectService {
    Subject addSubject(SubjectRequest subjectRequest);
    Subject getSubjectById(Long id);
    void deleteSubjectById(Long id);
    List<Subject> getSubjects();
}
