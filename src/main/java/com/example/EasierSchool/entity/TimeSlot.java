package com.example.EasierSchool.entity;


import com.example.EasierSchool.model.LessonFrequency;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TIME_SLOT_ID")
    private long timeSlotId;

    @Column(name = "DAY_OF_WEEK")
    private DayOfWeek dayOfWeek;

    /*@Column(name = "START_TIME")
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(style = "hh:mm")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm")
    private Date startTime;

    @Column(name = "END_TIME")
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(style = "hh:mm")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm")
    private Date endTime;*/

    @Column(name = "START_TIME")
    private String startTime;
    @Column(name = "END_TIME")
    private String endTime;

    /*@Column(name = "FREQUENCY")
    @Enumerated(EnumType.STRING)
    private LessonFrequency frequency;*/

    @ManyToOne
    @JoinColumn(name = "SUBJECT_ID")
    private Subject subject;
}
