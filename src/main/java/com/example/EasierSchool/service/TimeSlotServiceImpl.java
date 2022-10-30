package com.example.EasierSchool.service;

import com.example.EasierSchool.entity.TimeSlot;
import com.example.EasierSchool.exception.CustomServiceException;
import com.example.EasierSchool.model.TimeSlotRequest;
import com.example.EasierSchool.model.TimeSlotResponse;
import com.example.EasierSchool.repository.SubjectRepository;
import com.example.EasierSchool.repository.TimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimeSlotServiceImpl implements TimeSlotService{

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Override
    public TimeSlotResponse addTimeSlot(TimeSlotRequest timeSlotRequest) {

        var subject = subjectRepository
                .findById(timeSlotRequest.getSubjectId())
                .orElseThrow(() -> new CustomServiceException(
                        "Subject with provided id not found",
                        "NOT_FOUND"));

        var timeSlot = TimeSlot
                .builder()
                .startTime(timeSlotRequest.getStartTime())
                .endTime(timeSlotRequest.getEndTime())
                .dayOfWeek(timeSlotRequest.getDayOfWeek())
                .frequency(timeSlotRequest.getFrequency())
                .subject(subject)
                .build();

        timeSlotRepository.save(timeSlot);

        var timeSlotResponse = TimeSlotResponse
                .builder()
                .startTime(timeSlotRequest.getStartTime())
                .endTime(timeSlotRequest.getEndTime())
                .dayOfWeek(timeSlotRequest.getDayOfWeek())
                .frequency(timeSlotRequest.getFrequency())
                .subjectId(subject.getSubjectId())
                .build();

        return timeSlotResponse;
    }

    @Override
    public List<TimeSlotResponse> getTimeSlots() {
        var timeSlots =  timeSlotRepository
                .findAll()
                .stream()
                .map(slot -> TimeSlotResponse
                        .builder()
                        .dayOfWeek(slot.getDayOfWeek())
                        .startTime(slot.getStartTime())
                        .endTime(slot.getEndTime())
                        .frequency(slot.getFrequency())
                        .subjectId(slot.getSubject().getSubjectId())
                        .build())
                .collect(Collectors.toList());
        return timeSlots;
    }
}
