package test.service;

import entities.HotelRoom;
import entities.RoomType;
import exception.HotelRoomNotFoundException;
import org.junit.Before;
import org.junit.Test;
import service.HotelRoomService;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class HotelRoomServiceTest {

    private HotelRoomService hotelRoomService;
    private HotelRoom hotelRoom;

    @Before
    public void setUp() {
        hotelRoomService = new HotelRoomService();
        hotelRoom = new HotelRoom("Room 012", RoomType.SINGLE);
        hotelRoomService.save(hotelRoom);
    }

    @Test
    public void testSave() {
        HotelRoom newHotelRoom = new HotelRoom("Room 777", RoomType.SINGLE);
        HotelRoom savedHotelRoom = hotelRoomService.save(newHotelRoom);
        assertEquals(savedHotelRoom.getRoomId(), newHotelRoom.getRoomId());
        assertEquals(savedHotelRoom.getRoomName(), newHotelRoom.getRoomName());
    }

    @Test
    public void testUpdate() {
        HotelRoom fetchedHotelRoom = hotelRoomService.findById(hotelRoom.getRoomId());
        fetchedHotelRoom.setRoomName("test");
        HotelRoom updatedHotelRoom = hotelRoomService.update(hotelRoom.getRoomId(),fetchedHotelRoom);
            assertEquals(fetchedHotelRoom.getRoomName(), updatedHotelRoom.getRoomName());
    }

    @Test
    public void testDelete() {
        HotelRoom fetchedHotelRoom = hotelRoomService.findById(hotelRoom.getRoomId());
        hotelRoomService.delete(fetchedHotelRoom.getRoomId());
        assertThrows(HotelRoomNotFoundException.class, () -> {
            hotelRoomService.findById(fetchedHotelRoom.getRoomId());
        });
    }


    @Test
    public void testFindById() {
        HotelRoom fetchedHotelRoom = hotelRoomService.findById(hotelRoom.getRoomId());
        assertEquals(1, fetchedHotelRoom.getRoomId());
        assertEquals("Room 012", fetchedHotelRoom.getRoomName());
    }


    @Test
    public void testFindAll() {

        List<Map.Entry<Integer, HotelRoom>> fetchedHotelRooms = hotelRoomService.findAll();
        assertEquals(1, fetchedHotelRooms.size());
    }

}