package com.example.EasierSchool.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectResponse {
    private String name;
    private String type;
    private String studentGroup;
    private Long roomId;
    private Long teacherId;
    private List<Long> timeSlotsId;
}
