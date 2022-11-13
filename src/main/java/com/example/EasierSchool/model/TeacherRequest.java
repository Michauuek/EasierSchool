package com.example.EasierSchool.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
@Builder
public class TeacherRequest {
    private String name;
    private String surname;
    private Set<Long> subjectsId;
}
