package com.example.EasierSchool.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoomRequest {
    private String roomNumber;
    private String departmentName;
}
