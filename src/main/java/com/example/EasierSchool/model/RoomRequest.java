package com.example.EasierSchool.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class RoomRequest {
    private String roomNumber;
    private String departmentName;
}
