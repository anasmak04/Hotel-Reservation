package service;

import entities.HotelRoom;
import exception.HotelRoomNotFoundException;
import repository.HotelRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class HotelRoomService implements HotelRepository<HotelRoom> {

    private final Map<Integer, HotelRoom> rooms = new HashMap<>();
    int hotelRoomId = 1;

    @Override
    public HotelRoom findById(int id) {
        if (rooms.containsKey(id)) {
            return rooms.get(id);
        }
        throw new HotelRoomNotFoundException("Hotel Room Not Found");
    }


    @Override
    public List<HotelRoom> findAll() {
        LocalDate today = LocalDate.now();
        if (rooms.isEmpty()) {
            System.out.println("No hotel rooms found");
        }
        return rooms.values().stream()
                .filter(room -> room.getReservations().stream()
                        .noneMatch(reservation ->
                                !reservation.getEndDate().isBefore(today)
                                        && !reservation.getStartDate().isAfter(today)))
                .toList();
    }


    @Override
    public HotelRoom save(HotelRoom hotelRoom) {
        hotelRoom.setId(hotelRoomId++);
        rooms.put(hotelRoom.getId(), hotelRoom);
        return hotelRoom;
    }

    @Override
    public void delete(int id) {
        if (rooms.get(id) == null) {
            throw new HotelRoomNotFoundException("Hotel Room Not Found");
        }
        rooms.remove(id);
    }

    @Override
    public HotelRoom findByName(String name) {
        for (Map.Entry<Integer, HotelRoom> entry : rooms.entrySet()) {
            if (entry.getValue().getRoomName().equals(name)) {
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    public List<HotelRoom> saveMultiple(List<HotelRoom> hotelRoomList) {
        return hotelRoomList.stream().map(this::save).toList();
    }

    @Override
    public HotelRoom update(HotelRoom hotelRoom) {
       if(hotelRoom == null){
           throw new HotelRoomNotFoundException("Hotel Room Not Found");
       }
        rooms.put(hotelRoom.getId(), hotelRoom);
        return hotelRoom;
    }

    public boolean isRoomAvailable(int roomId, LocalDate startDate, LocalDate endDate, int reservationId) {
        HotelRoom room = findById(roomId);
        return room.getReservations().stream()
                .filter(reservation -> reservation.getId() != reservationId)
                .noneMatch(reservation ->
                        !(endDate.isBefore(reservation.getStartDate())
                                || startDate.isAfter(reservation.getEndDate()))
                );
    }
}


