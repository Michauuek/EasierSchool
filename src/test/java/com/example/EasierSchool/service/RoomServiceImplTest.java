package com.example.EasierSchool.service;

import com.example.EasierSchool.entity.Room;
import com.example.EasierSchool.entity.Subject;
import com.example.EasierSchool.exception.CustomServiceException;
import com.example.EasierSchool.model.RoomRequest;
import com.example.EasierSchool.model.RoomResponse;
import com.example.EasierSchool.repository.RoomRepository;
import com.example.EasierSchool.repository.SubjectRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RoomServiceImplTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    RoomService roomService = new RoomServiceImpl();

    @DisplayName("Add Room - Success Scenario")
    @Test
    void test_When_Add_Room_Success() {
        Room room = getRoomMock();
        RoomRequest roomRequest = getMockRoomRequest();

        when(roomRepository.save(any(Room.class))).thenReturn(room);

        RoomResponse roomResponse = roomService.addRoom(roomRequest);

        verify(roomRepository, times(1)).save(any());

        assertEquals(room.getRoomId(), roomResponse.getId());
    }

    @DisplayName("Get Room - Success Scenario")
    @Test
    void test_When_Get_Room_By_Id_Success() {
        Room room = getRoomMock();

        when(roomRepository.findById(anyLong()))
                .thenReturn(Optional.of(room));

        RoomResponse roomResponse = roomService.getRoomById(0L);

        verify(roomRepository, times(1))
                .findById(anyLong());

        assertNotNull(roomResponse);
        assertEquals(room.getRoomId(), roomResponse.getId());
    }

    @DisplayName("Get Room - Failure Scenario")
    @Test
    void test_When_Get_Room_By_Id_Failed() {
        when(roomRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(null));

        CustomServiceException exception = assertThrows(
                CustomServiceException.class,
                () -> roomService.getRoomById(anyLong()));

        assertEquals("NOT_FOUND", exception.getErrorCode());
        assertEquals("Room with provided id not found", exception.getMessage());

        verify(roomRepository, times(1)).findById(anyLong());
    }

    @DisplayName("Get Rooms - Success Scenario")
    @Test
    void test_When_Get_Rooms_Success() {
        Room room = getRoomMock();

        when(roomRepository.findAll())
                .thenReturn(List.of(room));

        List<RoomResponse> roomResponses = roomService.getRooms();

        verify(roomRepository, times(1)).findAll();

        assertNotNull(roomResponses);
        assertEquals(room.getRoomId(), roomResponses.get(0).getId());
    }

    private Room getRoomMock(){
        return Room.builder()
                .roomId(0)
                .roomNumber("201B")
                .departmentName("WIEIK")
                .subjects(List.of(new Subject()))
                .build();
    }

    private RoomRequest getMockRoomRequest(){
        return RoomRequest.builder()
                .departmentName("WIEIK")
                .roomNumber("201B")
                .build();
    }
}