package service;


import entities.Client;
import entities.HotelRoom;
import entities.Reservation;
import exception.ReservationNotFoundException;
import repository.HotelRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class ReservationService implements HotelRepository<Reservation> {

    private final Map<Integer, Reservation> reservations;
    private int currentId = 1;
    private final HotelRoomService hotelRoomService;
    private final ClientService clientService;

    public ReservationService(HotelRoomService hotelRoomService, ClientService clientService) {
        this.reservations = new HashMap<>();
        this.hotelRoomService = hotelRoomService;
        this.clientService = clientService;
    }


    @Override
    public Reservation save(Reservation reservation) {
        if (reservation.getEndDate() == null || reservation.getStartDate() == null) {
            throw new IllegalArgumentException("The reservation must have a start date and end date");
        } else {
            reservation.setId(currentId++);
            reservations.put(currentId++, reservation);
            HotelRoom fetchedHotelRoom = hotelRoomService.findById(reservation.getRoomName().getId());
            Client fetchedClient = clientService.findById(reservation.getClient().getId());
            fetchedClient.addReservation(reservation);
            fetchedHotelRoom.addReservation(reservation);
        }
        return reservation;
    }


    @Override
    public Reservation findById(int id) {
        if(reservations.containsKey(id)) {
            return reservations.get(id);
        }

        throw new ReservationNotFoundException("Reservation not found");
    }


    @Override
    public List<Reservation> findAll() {
        if (reservations.isEmpty()) {
            throw new ReservationNotFoundException("Reservation not found");
        }
        return reservations.values().stream().toList();
    }

    @Override
    public Reservation update(Reservation reservation) {
        reservations.put(reservation.getId(), reservation);
        return reservation;
    }


    @Override
    public void delete(int id) {
        Reservation reservation = findById(id);
        if (reservation != null && reservation.getId() == id) {
            HotelRoom fetchedHotelRoom = hotelRoomService.findById(reservation.getRoomName().getId());
            Client fetchedClient = clientService.findById(reservation.getClient().getId());
            fetchedClient.deleteReservation(reservation);
            fetchedHotelRoom.removeReservation(reservation);
            reservations.remove(id);
        } else {
            throw new ReservationNotFoundException("Reservation not found");
        }
    }

    @Override
    public Reservation findByName(String name) {
       return new Reservation();
    }

    @Override
    public List<Reservation> saveMultiple(List<Reservation> t) {
        return List.of();
    }


}
