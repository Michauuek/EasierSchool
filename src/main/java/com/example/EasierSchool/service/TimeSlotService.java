package com.example.EasierSchool.service;

import com.example.EasierSchool.entity.TimeSlot;
import com.example.EasierSchool.model.TimeSlotRequest;

import java.util.List;

public interface TimeSlotService {
    TimeSlot addTimeSlot(TimeSlotRequest timeSlotRequest);
    List<TimeSlot> getTimeSlots();
}
