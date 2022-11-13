package com.example.EasierSchool.controller;

import com.example.EasierSchool.model.TeacherRequest;
import com.example.EasierSchool.model.TeacherResponse;
import com.example.EasierSchool.model.TimeSlotRequest;
import com.example.EasierSchool.model.TimeSlotResponse;
import com.example.EasierSchool.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @PostMapping("/")
    public ResponseEntity<TeacherResponse> addTeacher(@RequestBody TeacherRequest teacherRequest){
        var teacher = teacherService
                .addTeacher(teacherRequest);
        return new ResponseEntity<>(teacher, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TeacherResponse>> getAllTTeachers(){
        var teachers = teacherService.getTeachers();
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherResponse> getTeacherById(@PathVariable("id") Long teacherId){
        var teacher = teacherService.getTeacherById(teacherId);
        return new ResponseEntity<>(teacher, HttpStatus.OK);
    }
}
