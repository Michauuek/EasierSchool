package com.example.EasierSchool.controller;

import com.example.EasierSchool.entity.Subject;
import com.example.EasierSchool.model.SubjectRequest;
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
    public ResponseEntity<Subject> addSubject(@RequestBody SubjectRequest subjectRequest){
        var subject = subjectService
                .addSubject(subjectRequest);

        return new ResponseEntity<>(subject, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subject> getSubjectById(@PathVariable("id") Long subjectId){
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
    public ResponseEntity<List<Subject>> getAllSubjects(){
        var subjects = subjectService.getSubjects();
        return new ResponseEntity<>(subjects, HttpStatus.OK);
    }
}
