package com.example.EasierSchool.controller;

import com.example.EasierSchool.entity.Subject;
import com.example.EasierSchool.model.SubjectRequest;
import com.example.EasierSchool.model.SubjectResponse;
import com.example.EasierSchool.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @PostMapping("/")
    public ResponseEntity<SubjectResponse> addSubject(@RequestBody SubjectRequest subjectRequest){
        var subject = subjectService
                .addSubject(subjectRequest);

        return new ResponseEntity<>(subject, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectResponse> getSubjectById(@PathVariable("id") Long subjectId){
        var subject = subjectService
                .getSubjectById(subjectId);

        return new ResponseEntity<>(subject, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubjectById(@PathVariable("id") Long subjectId){
        subjectService.deleteSubjectById(subjectId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<SubjectResponse>> getAllSubjects(){
        var subjects = subjectService.getSubjects();
        return new ResponseEntity<>(subjects, HttpStatus.OK);
    }
}
