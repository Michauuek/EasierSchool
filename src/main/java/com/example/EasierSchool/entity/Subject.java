package com.example.EasierSchool.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    @Column(name = "TEACHER")
    private String teacherName;

    @Column(name = "STUDENT_GROUP")
    private String studentGroup;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<TimeSlot> posts;
}
