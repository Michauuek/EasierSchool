package com.example.EasierSchool.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
@Builder
public class SubjectRequest {
    private String name;
    private String type;
    private String studentGroup;
    private Long teacherId;
    private Long roomId;
}
