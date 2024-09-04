package service;

import entities.HotelRoom;
import exception.HotelRoomNotFoundException;
import repository.HotelRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class HotelRoomService implements HotelRepository<HotelRoom,Integer> {

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
    public List<Map.Entry<Integer, HotelRoom>> findAll() {
        LocalDate today = LocalDate.now();
        if (rooms.isEmpty()) {
            System.out.println("No hotel rooms found");
        }
        Set<Map.Entry<Integer, HotelRoom>> entry = rooms.entrySet();

        return entry.stream()
                .filter(room -> room.getValue().getReservations().stream()
                        .noneMatch(reservation ->
                                !reservation.getValue().getEndDate().isBefore(today)
                                        && !reservation.getValue().getStartDate().isAfter(today)))
                .toList();
    }



    @Override
    public HotelRoom save(HotelRoom hotelRoom) {
        rooms.put(hotelRoomId++, hotelRoom);
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
    public HotelRoom update(int id , HotelRoom hotelRoom) {
       if(hotelRoom == null){
           throw new HotelRoomNotFoundException("Hotel Room Not Found");
       }
        rooms.put(id, hotelRoom);
        return hotelRoom;
    }

    public boolean isRoomAvailable(int roomId, LocalDate startDate, LocalDate endDate, int reservationId) {
        HotelRoom room = findById(roomId);
        return room.getReservations().stream()
                .filter(reservation -> reservation.getKey() != reservationId)
                .noneMatch(reservation ->
                        !(endDate.isBefore(reservation.getValue().getStartDate())
                                || startDate.isAfter(reservation.getValue().getEndDate()))
                );
    }
}


