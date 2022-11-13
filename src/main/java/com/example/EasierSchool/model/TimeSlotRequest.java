package com.example.EasierSchool.model;


import com.example.EasierSchool.entity.Subject;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalTime;
import java.util.Date;

@Data
@AllArgsConstructor
@Builder
public class TimeSlotRequest {
    private DayOfWeek dayOfWeek;
    @DateTimeFormat(style = "HH:mm")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm")
    private LocalTime startTime;
    @DateTimeFormat(style = "HH:mm")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm")
    private LocalTime endTime;
    @Enumerated(EnumType.STRING)
    private LessonFrequency frequency;
    private Long subjectId;
}
