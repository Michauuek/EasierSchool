package com.example.EasierSchool.controller;

import com.example.EasierSchool.entity.Room;
import com.example.EasierSchool.model.RoomRequest;
import com.example.EasierSchool.model.RoomResponse;
import com.example.EasierSchool.model.TimeSlotRequest;
import com.example.EasierSchool.model.TimeSlotResponse;
import com.example.EasierSchool.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping("/")
    public ResponseEntity<RoomResponse> addRoom(@RequestBody RoomRequest roomRequest){
        var room = roomService
                .addRoom(roomRequest);
        return new ResponseEntity<>(room, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<RoomResponse>> getAllRooms(){
        var rooms = roomService.getRooms();
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> getRoomById(@PathVariable("id") Long roomId){
        var room = roomService.getRoomById(roomId);
        return new ResponseEntity<>(room, HttpStatus.OK);
    }
}
