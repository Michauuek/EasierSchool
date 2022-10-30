package com.example.EasierSchool.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ROOM_ID")
    private long roomId;

    @Column(name = "ROOM_NUMBER")
    private String roomNumber;

    @Column(name = "DEPARTMENT")
    private String departmentName;
}
