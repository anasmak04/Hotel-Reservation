package repository;

import entities.Reservation;

import java.util.List;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

//    List<Reservation> saveMultiple(List<Reservation> reservations);

    Reservation findById(int id);

    List<Reservation> findAll();

    Reservation update(Reservation reservation);

    void delete(int id);
}
