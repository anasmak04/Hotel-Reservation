package service;

import entities.HotelRoom;
import exception.HotelRoomNotFoundException;
import repository.HotelRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HotelRoomService implements HotelRepository<HotelRoom> {

    private final List<HotelRoom> rooms = new ArrayList<>();


    @Override
    public HotelRoom findById(int id) {
        return rooms.stream().
                filter(r -> r.getId() == id)
                .findFirst().orElseThrow((() -> new HotelRoomNotFoundException("hotel room not found")));
    }

    @Override
    public List<HotelRoom> findAll() {
        LocalDate today = LocalDate.now();
        if(rooms.isEmpty()) {
            System.out.println("No hotel rooms found");
        }
        return rooms.stream()
                .filter(room -> room.getReservations().stream()
                        .noneMatch(reservation ->
                                !reservation.getEndDate().isBefore(today)
                                        && !reservation.getStartDate().isAfter(today)))
                .collect(Collectors.toList());
    }


    @Override
    public HotelRoom save(HotelRoom hotelRoom) {
        hotelRoom.setId(rooms.size() + 1);
        rooms.add(hotelRoom);
        return hotelRoom;
    }

    @Override
    public List<HotelRoom> saveMultiple(List<HotelRoom> hotelRooms) {
        return hotelRooms.stream().map(this::save).toList();
    }


    @Override
    public void delete(int id) {
        if (rooms.isEmpty()) {
            System.out.println("Hotel room is empty");
        }
        HotelRoom hotelRoom = findById(id);
        if(hotelRoom ==  null){
            throw new HotelRoomNotFoundException("hotel room not found");
        }
        rooms.remove(hotelRoom);
    }


    @Override
    public HotelRoom update(HotelRoom hotelRoom) {
        HotelRoom fetchedRoom = findById(hotelRoom.getId());
            int index = rooms.indexOf(fetchedRoom);
            rooms.set(index, hotelRoom);
            return hotelRoom;
    }

    public boolean isRoomAvailable(int roomId, LocalDate startDate, LocalDate endDate) {
        HotelRoom room = findById(roomId);
        return room.getReservations().stream()
                .allMatch(reservation ->
                        (endDate.isBefore(reservation.getStartDate())
                                || startDate.isAfter(reservation.getEndDate()))
                );
    }
}


