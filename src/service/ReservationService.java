package service;


import entities.Reservation;
import exception.ReservationNotFoundException;
import repository.ReservationRepository;

import java.util.ArrayList;
import java.util.List;

public class ReservationService implements ReservationRepository {
    private final List<Reservation> reservations = new ArrayList<>();
    private int currentId = 1;

    @Override
    public Reservation save(Reservation reservation) {
        if (reservation.getEndDate() == null || reservation.getStartDate() == null) {
            System.out.println("please select a correct duration");
        } else {
            reservation.setId(currentId++);
            reservations.add(reservation);
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
        if (reservations.isEmpty()) {
            System.out.println("No reservations found");
        }
        return reservations;
    }

    @Override
    public Reservation update(Reservation reservation) {
        Reservation existingReservation = findById(reservation.getId());
        if (existingReservation != null) {
            int index = reservations.indexOf(existingReservation);
            reservations.set(index, reservation);
            return reservation;
        }
        else {
            System.out.println("Reservation not found");
            return null;
        }
    }


    @Override
    public void delete(int id) {
        if (reservations.isEmpty()) {
            System.out.println("No reservations found");
        }
        Reservation reservation = reservations.stream()
                .filter(res -> res.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));

        reservations.remove(reservation);

    }

}
