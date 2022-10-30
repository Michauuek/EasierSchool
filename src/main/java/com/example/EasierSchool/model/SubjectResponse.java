package com.example.EasierSchool.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectResponse {
    private String name;
    private String type;
    private String teacherName;
    private String studentGroup;
    private List<Long> timeSlotsId;
}
