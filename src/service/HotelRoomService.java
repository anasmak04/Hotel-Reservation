package service;

import entities.HotelRoom;
import exception.HotelRoomNotFoundException;
import repository.HotelRoomRepository;

import java.util.ArrayList;
import java.util.List;

public class HotelRoomService implements HotelRoomRepository {

    private final List<HotelRoom> rooms = new ArrayList<>();

    public HotelRoomService() {
        rooms.add(new HotelRoom(1, "Room 1"));
        rooms.add(new HotelRoom(2, "Room 2"));
        rooms.add(new HotelRoom(3, "Room 3"));
    }

    @Override
    public HotelRoom findById(int id) {
        return rooms.stream().filter(r -> r.getId() == id).findFirst().orElseThrow((() -> new HotelRoomNotFoundException("hotel room not found")));
    }

    @Override
    public List<HotelRoom> findAll() {
        return rooms;
    }
}
