package com.example.EasierSchool.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SUBJECT_ID")
    private long subjectId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SUBJECT_TYPE")
    private String type;

    @Column(name = "STUDENT_GROUP")
    private String studentGroup;

    @ManyToOne
    @JoinColumn(name = "TEACHER_ID")
    private Teacher teacher;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<TimeSlot> timeSlots;

    @ManyToOne
    @JoinColumn(name = "ROOM_ID")
    private Room room;


}
