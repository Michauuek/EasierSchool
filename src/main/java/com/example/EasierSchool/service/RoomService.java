package com.example.EasierSchool.service;

import com.example.EasierSchool.entity.Room;
import com.example.EasierSchool.model.RoomRequest;
import com.example.EasierSchool.model.RoomResponse;

import java.util.List;

public interface RoomService {
    RoomResponse addRoom(RoomRequest roomRequest);
    List<RoomResponse> getRooms();
    RoomResponse getRoomById(Long id);
}
