package com.example.EasierSchool.service;

import com.example.EasierSchool.entity.Subject;
import com.example.EasierSchool.entity.Teacher;
import com.example.EasierSchool.entity.TimeSlot;
import com.example.EasierSchool.exception.CustomServiceException;
import com.example.EasierSchool.model.SubjectRequest;
import com.example.EasierSchool.model.SubjectResponse;
import com.example.EasierSchool.repository.RoomRepository;
import com.example.EasierSchool.repository.SubjectRepository;
import com.example.EasierSchool.repository.TeacherRepository;
import com.example.EasierSchool.repository.TimeSlotRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Log4j2
public class SubjectServiceImpl implements SubjectService{
    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public SubjectResponse addSubject(SubjectRequest subjectRequest) {

        var isSubjectNamePresent = subjectRepository
                .findByName(subjectRequest.getName()).stream().findAny().isPresent();

        var isSubjectTeacherPresent = subjectRepository
                .findByName(subjectRequest.getName())
                .stream()
                .filter(subject ->
                        subject.getTeacher().getTeacherId() == subjectRequest.getTeacherId()
                ).findFirst().isPresent();

        if(isSubjectNamePresent && isSubjectTeacherPresent){
            throw new CustomServiceException(
                    "Subject with provided name already exists",
                    "SUBJECT_ALREADY_EXISTS"
            );
        }

        var room = roomRepository
                .findById(subjectRequest.getRoomId())
                .orElseThrow(() -> new CustomServiceException(
                        "Room with provided id not found",
                        "NOT_FOUND"));

        log.info("Searching for teachers with  provided id");

        var teacher = teacherRepository
                .findById(subjectRequest.getTeacherId())
                .orElseThrow(() -> new CustomServiceException(
                        "Teacher with provided id not found",
                        "NOT_FOUND"));

        Subject subject = Subject
                .builder()
                .name(subjectRequest.getName())
                .type(subjectRequest.getType())
                .teacher(teacher)
                .studentGroup(subjectRequest.getStudentGroup())
                .room(room)
                .build();

        subjectRepository.save(subject);

        var subjectTimeSlotsId = timeSlotRepository
                .findAll()
                .stream()
                .filter(slot -> slot.getSubject().getSubjectId() == subject.getSubjectId())
                .map(TimeSlot::getTimeSlotId)
                .collect(Collectors.toList());

        var subjectResponse = SubjectResponse
                .builder()
                .name(subject.getName())
                .studentGroup(subject.getStudentGroup())
                .teacherId(subject.getTeacher().getTeacherId())
                .type(subject.getType())
                .roomId(subject.getRoom().getRoomId())
                .timeSlotsId(subjectTimeSlotsId)
                .build();

        return subjectResponse;
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
                .teacherId(subject.getTeacher().getTeacherId())
                .type(subject.getType())
                .roomId(subject.getRoom().getRoomId())
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
                        .teacherId(subject.getTeacher().getTeacherId())
                        .roomId(subject.getRoom().getRoomId())
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
