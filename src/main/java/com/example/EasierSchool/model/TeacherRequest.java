package com.example.EasierSchool.model;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class TeacherRequest {
    private String name;
    private String surname;
    private Set<Long> subjectsId;
}
