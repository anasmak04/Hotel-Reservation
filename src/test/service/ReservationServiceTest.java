package test.service;

import entities.Client;
import entities.HotelRoom;
import entities.Reservation;
import entities.RoomType;
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
    private ClientService clientService;
    private HotelRoomService hotelRoomService;
    private Client client;
    private HotelRoom hotelRoom;

    @Before
    public void setUp()  {
        HotelRoom hotelRoom = new HotelRoom(1, "room test", RoomType.SINGLE);
         client = new Client(1, "Bilal", "38924947893");
         clientService = new ClientService();
         clientService.save(client);
        hotelRoomService = new HotelRoomService();
        hotelRoomService.save(hotelRoom);

        reservationService = new ReservationService(hotelRoomService,clientService);
        reservation = new Reservation(0, hotelRoom, client, LocalDate.now() , LocalDate.now());
        reservationService.save(reservation);
    }

    @Test
    public void testSave(){
        Reservation savedReservation = reservationService.save(reservation);
        System.out.println("Id here : " + savedReservation.getId());
        assertEquals(savedReservation.getId(), reservation.getId());
        assertEquals(savedReservation.getRoomName().getRoomName(), "room test");
    }

    @Test
    public void testFindById(){
        Reservation fetchedReservation = reservationService.findById(reservation.getId());
        assertEquals(fetchedReservation.getId(), reservation.getId());
        assertEquals(fetchedReservation.getRoomName().getRoomName(), reservation.getRoomName().getRoomName());
    }


    @Test
    public void testUpdate(){
        Reservation fetchedReservation = reservationService.findById(reservation.getId());
        HotelRoom hotelRoom = new HotelRoom(1, "room YYY", RoomType.SINGLE);
        fetchedReservation.setRoomName(hotelRoom);
        reservationService.update(fetchedReservation);
        assertEquals(fetchedReservation.getRoomName().getRoomName(), reservation.getRoomName().getRoomName());
    }


    @Test
    public void testDelete() {
        Reservation fetchedReservation = reservationService.findById(reservation.getId());
        reservationService.delete(fetchedReservation.getId());
        assertThrows(ReservationNotFoundException.class,
                () -> reservationService.findById(fetchedReservation.getId()));
    }

}