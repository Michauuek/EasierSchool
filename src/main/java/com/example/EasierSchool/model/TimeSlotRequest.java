package com.example.EasierSchool.model;


import com.example.EasierSchool.entity.Subject;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeSlotRequest {
    private DayOfWeek dayOfWeek;
    private String startTime;
    private String endTime;
    //private LessonFrequency frequency;
    private Long subjectId;
}
