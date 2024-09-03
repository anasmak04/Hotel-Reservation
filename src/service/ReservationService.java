package service;


import entities.Client;
import entities.HotelRoom;
import entities.Reservation;
import exception.ReservationNotFoundException;
import repository.HotelRepository;

import java.util.ArrayList;
import java.util.List;

public class ReservationService implements HotelRepository<Reservation> {

    private final List<Reservation> reservations;
    private final HotelRoomService hotelRoomService;
    private final ClientService clientService;

    public ReservationService(HotelRoomService hotelRoomService, ClientService clientService) {
        this.reservations = new ArrayList<>();
        this.hotelRoomService = hotelRoomService;
        this.clientService = clientService;
    }


    @Override
    public Reservation save(Reservation reservation) {
        if (reservation.getEndDate() == null || reservation.getStartDate() == null) {
            throw new IllegalArgumentException("The reservation must have a start date and end date");
        } else {
            reservation.setId(reservations.size() + 1);
            reservations.add(reservation);
            HotelRoom fetchedHotelRoom = hotelRoomService.findById(reservation.getRoomName().getId());
            Client fetchedClient = clientService.findById(reservation.getClient().getId());
            fetchedClient.addReservation(reservation);
            fetchedHotelRoom.addReservation(reservation);
        }
        return reservation;
    }


    @Override
    public Reservation findById(int id) {
        return reservations.stream()
                .filter(reservation -> reservation.getId() == id)
                .findFirst().orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));
    }


    @Override
    public List<Reservation> findAll() {
        if (reservations.isEmpty())
            throw new ReservationNotFoundException("Reservation not found");
        return reservations;
    }

    @Override
    public List<Reservation> saveMultiple(List<Reservation> reservations) {
        return reservations.stream().map(this::save).toList();
    }

    @Override
    public Reservation update(Reservation reservation) {
        Reservation fetchedReservation = findById(reservation.getId());
        int index = reservations.indexOf(fetchedReservation);
        reservations.set(index, reservation);
        return reservation;
    }


    @Override
    public void delete(int id) {
        Reservation reservation = findById(id);
        reservations.remove(reservation);
        HotelRoom fetchedHotelRoom = hotelRoomService.findById(reservation.getRoomName().getId());
        Client fetchedClient = clientService.findById(reservation.getClient().getId());
        fetchedClient.deleteReservation(reservation);
        fetchedHotelRoom.removeReservation(reservation);
    }
}
