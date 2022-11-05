package com.example.EasierSchool.service;

import com.example.EasierSchool.entity.TimeSlot;
import com.example.EasierSchool.exception.CustomServiceException;
import com.example.EasierSchool.model.TimeSlotRequest;
import com.example.EasierSchool.model.TimeSlotResponse;
import com.example.EasierSchool.repository.SubjectRepository;
import com.example.EasierSchool.repository.TimeSlotRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
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

        log.info("Time slot id:{}", timeSlot.getTimeSlotId());
        timeSlotRepository.save(timeSlot);

        var timeSlotResponse = TimeSlotResponse
                .builder()
                .id(timeSlot.getTimeSlotId())
                .startTime(timeSlotRequest.getStartTime())
                .endTime(timeSlotRequest.getEndTime())
                .dayOfWeek(timeSlotRequest.getDayOfWeek())
                .frequency(timeSlotRequest.getFrequency())
                .subjectId(subject.getSubjectId())
                .build();

        log.info("Time slot response id:{}", timeSlotResponse.getId());
        return timeSlotResponse;
    }

    @Override
    public List<TimeSlotResponse> getTimeSlots() {
        return timeSlotRepository
                .findAll()
                .stream()
                .map(slot -> TimeSlotResponse
                        .builder()
                        .id(slot.getTimeSlotId())
                        .dayOfWeek(slot.getDayOfWeek())
                        .startTime(slot.getStartTime())
                        .endTime(slot.getEndTime())
                        .frequency(slot.getFrequency())
                        .subjectId(slot.getSubject().getSubjectId())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public TimeSlotResponse getTimeSlotById(Long id) {
        var timeSlot = timeSlotRepository
                .findById(id)
                .orElseThrow(() -> new CustomServiceException(
                        "Time Slot with provided id not found",
                        "NOT_FOUND"));

        return TimeSlotResponse
                .builder()
                .id(timeSlot.getTimeSlotId())
                .startTime(timeSlot.getStartTime())
                .endTime(timeSlot.getEndTime())
                .subjectId(timeSlot.getTimeSlotId())
                .frequency(timeSlot.getFrequency())
                .dayOfWeek(timeSlot.getDayOfWeek())
                .build();
    }
}
