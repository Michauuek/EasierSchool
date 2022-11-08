package com.example.EasierSchool.service;

import com.example.EasierSchool.entity.Room;
import com.example.EasierSchool.entity.Subject;
import com.example.EasierSchool.exception.CustomServiceException;
import com.example.EasierSchool.model.RoomRequest;
import com.example.EasierSchool.model.RoomResponse;
import com.example.EasierSchool.repository.RoomRepository;
import com.example.EasierSchool.repository.SubjectRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class RoomServiceImpl implements RoomService{

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Override
    public RoomResponse addRoom(RoomRequest roomRequest) {

        var room = Room
                .builder()
                .roomNumber(roomRequest.getRoomNumber())
                .departmentName(roomRequest.getDepartmentName())
                .subjects(Collections.emptyList())
                .build();

        log.info("Saving room to database with id:{}", room.getRoomId());
        roomRepository.save(room);

        var roomResponse = RoomResponse
                .builder()
                .id(room.getRoomId())
                .roomNumber(room.getRoomNumber())
                .departmentName(roomRequest.getDepartmentName())
                .build();

        log.info("Room response with id:{}", roomResponse.getId());
        return roomResponse;
    }

    @Override
    public RoomResponse getRoomById(Long id) {

        var room = roomRepository
                .findById(id)
                .orElseThrow(() -> new CustomServiceException(
                        "Room with provided id not found",
                        "NOT_FOUND"));

        return RoomResponse
                .builder()
                .id(room.getRoomId())
                .roomNumber(room.getRoomNumber())
                .departmentName(room.getDepartmentName())
                .build();
    }

    @Override
    public List<RoomResponse> getRooms() {
        return roomRepository
                .findAll()
                .stream()
                .map(room -> RoomResponse
                        .builder()
                        .id(room.getRoomId())
                        .roomNumber(room.getRoomNumber())
                        .departmentName(room.getDepartmentName())
                        .build())
                .collect(Collectors.toList());
    }
}
