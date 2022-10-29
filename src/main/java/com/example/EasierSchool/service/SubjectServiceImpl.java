package com.example.EasierSchool.service;

import com.example.EasierSchool.entity.Subject;
import com.example.EasierSchool.exception.CustomServiceException;
import com.example.EasierSchool.model.SubjectRequest;
import com.example.EasierSchool.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService{
    @Autowired
    private SubjectRepository subjectRepository;

    @Override
    public Subject addSubject(SubjectRequest subjectRequest) {

        var isSubjectPresent = subjectRepository
                .findByName(subjectRequest.getName()).isPresent();

        if(isSubjectPresent){
            throw new CustomServiceException(
                    "Subject with provided name already exists",
                    "SUBJECT_ALREADY_EXISTS"
            );
        }

        Subject subject = Subject
                .builder()
                .name(subjectRequest.getName())
                .build();

        subjectRepository.save(subject);

        return subject;
    }


    @Override
    public Subject getSubjectById(Long id) {

        var subject = subjectRepository
                .findById(id)
                .orElseThrow(() -> new CustomServiceException("Subject with provided id not found", "SUBJECT_NOT_FOUND"));

        return subject;
    }

    @Override
    public void deleteSubjectById(Long id) {
        subjectRepository.deleteById(id);
    }

    @Override
    public List<Subject> getSubjects() {
        return subjectRepository.findAll();
    }
}
