package test.service;

import entities.Client;
import entities.HotelRoom;
import entities.Reservation;
import exception.ReservationNotFoundException;
import org.junit.Before;
import org.junit.Test;
import service.ClientService;
import service.HotelRoomService;
import service.ReservationService;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;


public class ReservationServiceTest {
    private ReservationService reservationService;
    private Reservation reservation;


    @Before
    public void setUp() throws Exception {
        HotelRoom hotelRoom = new HotelRoom(1, "room test");
        Client client = new Client(1, "jhon", "38924947893");
        reservationService = new ReservationService(new HotelRoomService(), new ClientService());
        reservation = new Reservation(0, hotelRoom, client, LocalDate.now() , LocalDate.now());
        reservationService.save(reservation);
    }

    @Test
    public void testSave(){
        Reservation savedReservation = reservationService.save(reservation);
        assertEquals(savedReservation.getId(), reservation.getId());
        assertEquals(savedReservation.getRoomName().getRoomName(), reservation.getRoomName().getRoomName());
    }

    @Test
    public void testFindById(){
        Reservation fetchedReservation = reservationService.findById(reservation.getId());
        assertEquals(fetchedReservation.getId(), reservation.getId());
        assertEquals(fetchedReservation.getRoomName().getRoomName(), reservation.getRoomName().getRoomName());
    }

    @Test
    public void testDelete(){
        Reservation fetchedReservation = reservationService.findById(reservation.getId());
        reservationService.delete(fetchedReservation.getId());
        assertThrows(ReservationNotFoundException.class, () -> reservationService.findById(reservation.getId()));
    }

    @Test
    public void testUpdate(){
        Reservation fetchedReservation = reservationService.findById(reservation.getId());
        HotelRoom hotelRoom = new HotelRoom(1, "room YYY");
        fetchedReservation.setRoomName(hotelRoom);
        reservationService.update(fetchedReservation);
        assertEquals(fetchedReservation.getRoomName().getRoomName(), reservation.getRoomName().getRoomName());
    }


}