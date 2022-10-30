package com.example.EasierSchool.service;

import com.example.EasierSchool.entity.Subject;
import com.example.EasierSchool.entity.TimeSlot;
import com.example.EasierSchool.exception.CustomServiceException;
import com.example.EasierSchool.model.SubjectRequest;
import com.example.EasierSchool.model.SubjectResponse;
import com.example.EasierSchool.repository.SubjectRepository;
import com.example.EasierSchool.repository.TimeSlotRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class SubjectServiceImpl implements SubjectService{
    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Override
    public Subject addSubject(SubjectRequest subjectRequest) {

        var isSubjectNamePresent = subjectRepository
                .findByName(subjectRequest.getName()).isPresent();

        if(isSubjectNamePresent){
            throw new CustomServiceException(
                    "Subject with provided name already exists",
                    "SUBJECT_ALREADY_EXISTS"
            );
        }

        Subject subject = Subject
                .builder()
                .name(subjectRequest.getName())
                .type(subjectRequest.getType())
                .teacherName(subjectRequest.getTeacherName())
                .studentGroup(subjectRequest.getStudentGroup())
                .build();

        subjectRepository.save(subject);

        return subject;
    }


    @Override
    public SubjectResponse getSubjectById(Long id) {

        log.info("Searching subject with id:{}", id);

        var subject = subjectRepository
                .findById(id)
                .orElseThrow(() -> new CustomServiceException("Subject with provided id not found", "SUBJECT_NOT_FOUND"));

        log.info("Searching timeSlotsId for provided subject with id");

        var subjectTimeSlotsId = timeSlotRepository
                .findAll()
                .stream()
                .filter(slot -> slot.getSubject().getSubjectId() == subject.getSubjectId())
                .map(TimeSlot::getTimeSlotId)
                .collect(Collectors.toList());

        log.info("Creating response");

        var subjectResponse = SubjectResponse
                .builder()
                .name(subject.getName())
                .studentGroup(subject.getStudentGroup())
                .teacherName(subject.getTeacherName())
                .type(subject.getType())
                .timeSlotsId(subjectTimeSlotsId)
                .build();

        return subjectResponse;
    }

    @Override
    public void deleteSubjectById(Long id) {
        subjectRepository.deleteById(id);
    }

    @Override
    public List<SubjectResponse> getSubjects() {
        var subjectResponses = subjectRepository
                .findAll()
                .stream()
                .map(subject -> SubjectResponse
                        .builder()
                        .name(subject.getName())
                        .type(subject.getType())
                        .studentGroup(subject.getStudentGroup())
                        .teacherName(subject.getTeacherName())
                        .timeSlotsId(subject
                                .getTimeSlots()
                                .stream()
                                .map(TimeSlot::getTimeSlotId)
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());

        return subjectResponses;
    }
}
