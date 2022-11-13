package com.example.EasierSchool.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TEACHER_ID")
    private long teacherId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SURNAME")
    private String surname;

    @Column(name = "SUBJECTS")
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Subject> subjects;
}
