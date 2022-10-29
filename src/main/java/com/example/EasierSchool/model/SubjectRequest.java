package com.example.EasierSchool.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectRequest {
    private String name;
    private String type;
    private String teacherName;
    private String studentGroup;
}
