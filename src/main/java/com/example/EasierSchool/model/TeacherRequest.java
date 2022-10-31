package com.example.EasierSchool.model;

import lombok.Data;

import java.util.Set;

@Data
public class TeacherRequest {
    private String name;
    private String surname;
    private Set<Long> subjectsId;
}
