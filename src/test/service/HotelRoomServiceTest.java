package test.service;

import entities.HotelRoom;
import exception.HotelRoomNotFoundException;
import org.junit.Before;
import org.junit.Test;
import service.HotelRoomService;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class HotelRoomServiceTest {

    private HotelRoomService hotelRoomService;
    private HotelRoom hotelRoom;

    @Before
    public void setUp() {
        hotelRoomService = new HotelRoomService();
        hotelRoom = new HotelRoom(0, "Room 012");
        hotelRoomService.save(hotelRoom);
    }

    @Test
    public void testSave() {
        HotelRoom newHotelRoom = new HotelRoom(0, "Room 777");
        HotelRoom savedHotelRoom = hotelRoomService.save(newHotelRoom);
        assertEquals(savedHotelRoom.getId(), newHotelRoom.getId());
        assertEquals(savedHotelRoom.getRoomName(), newHotelRoom.getRoomName());
    }

    @Test
    public void testUpdate() {
        HotelRoom fetchedHotelRoom = hotelRoomService.findById(hotelRoom.getId());
        fetchedHotelRoom.setRoomName("test");
        HotelRoom updatedHotelRoom = hotelRoomService.update(fetchedHotelRoom);
            assertEquals(fetchedHotelRoom.getRoomName(), updatedHotelRoom.getRoomName());
    }

    @Test
    public void testDelete() {
        HotelRoom fetchedHotelRoom = hotelRoomService.findById(hotelRoom.getId());
        hotelRoomService.delete(fetchedHotelRoom.getId());
        assertThrows(HotelRoomNotFoundException.class, () -> {
            hotelRoomService.findById(fetchedHotelRoom.getId());
        });
    }


    @Test
    public void testFindById() {
        HotelRoom fetchedHotelRoom = hotelRoomService.findById(hotelRoom.getId());
        assertEquals(1, fetchedHotelRoom.getId());
        assertEquals("Room 012", fetchedHotelRoom.getRoomName());
    }


    @Test
    public void testFindAll() {
        List<HotelRoom> fetchedHotelRooms = hotelRoomService.findAll();
        assertEquals(1, fetchedHotelRooms.size());
    }

}