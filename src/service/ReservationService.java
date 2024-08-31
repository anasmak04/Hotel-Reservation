package service;


import entities.Client;
import entities.HotelRoom;
import entities.Reservation;
import exception.ReservationNotFoundException;
import repository.HotelRepository;

import java.util.ArrayList;
import java.util.List;

public class ReservationService implements HotelRepository<Reservation> {

    private final   List<Reservation> reservations;
    private  int currentId = 1;
    private final  HotelRoomService hotelRoomService;
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
            reservation.setId(currentId++);
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
        if(reservations.isEmpty())
            throw new ReservationNotFoundException("Reservation not found");
        return reservations;
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
        fetchedClient.addReservation(reservation);
        fetchedHotelRoom.removeReservation(reservation);
    }
}
