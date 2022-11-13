package com.example.EasierSchool.controller;

import com.example.EasierSchool.model.TimeSlotRequest;
import com.example.EasierSchool.model.TimeSlotResponse;
import com.example.EasierSchool.service.TimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/timeslot")
public class TimeSlotController {

    @Autowired
    private TimeSlotService timeSlotService;

    @PostMapping("/")
    public ResponseEntity<TimeSlotResponse> addTimeSlot(@RequestBody TimeSlotRequest timeSlotRequest){
        var timeSlot = timeSlotService
                .addTimeSlot(timeSlotRequest);

        return new ResponseEntity<>(timeSlot, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TimeSlotResponse>> getAllTimeSlots(){
        var timeSlots = timeSlotService.getTimeSlots();
        return new ResponseEntity<>(timeSlots, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimeSlotResponse> getTimeSlotById(@PathVariable("id") Long timeSlotId){
        var timeSlot = timeSlotService.getTimeSlotById(timeSlotId);
        return new ResponseEntity<>(timeSlot, HttpStatus.OK);
    }
}
