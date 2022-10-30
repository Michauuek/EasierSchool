package com.example.EasierSchool.service;

import com.example.EasierSchool.entity.Subject;
import com.example.EasierSchool.model.SubjectRequest;
import com.example.EasierSchool.model.SubjectResponse;

import java.util.List;

public interface SubjectService {
    Subject addSubject(SubjectRequest subjectRequest);
    SubjectResponse getSubjectById(Long id);
    void deleteSubjectById(Long id);
    List<SubjectResponse> getSubjects();
}
