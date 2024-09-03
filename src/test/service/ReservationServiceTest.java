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
    private HotelRoomService hotelRoomService;
    private ClientService clientService;

    @Before
    public void setUp() throws Exception {

        hotelRoomService = new HotelRoomService();
        clientService = new ClientService();

        HotelRoom hotelRoom = new HotelRoom(1, "room test", RoomType.SINGLE);
        hotelRoomService.save(hotelRoom);

        Client client = new Client(1, "Jhon", "38924947893");
        clientService.save(client);

        reservationService = new ReservationService(hotelRoomService, clientService);

        reservation = new Reservation(0, hotelRoom, client, LocalDate.now(), LocalDate.now());
        reservationService.save(reservation);
    }


    @Test
    public void testSave(){
     Reservation fetchedReservation = reservationService.save(reservation);
       HotelRoom fetchedHotelRoom =  hotelRoomService.findById(fetchedReservation.getRoomName().getId());
       fetchedHotelRoom.addReservation(reservation);
        Client fetchedClient =  clientService.findById(fetchedReservation.getClient().getId());
        fetchedClient.addReservation(reservation);
        assertEquals(2, reservation.getId());
        assertEquals("room test", reservation.getRoomName().getRoomName());
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
        HotelRoom hotelRoom = new HotelRoom(1, "room YYY", RoomType.SINGLE);
        fetchedReservation.setRoomName(hotelRoom);
        reservationService.update(fetchedReservation);
        assertEquals(fetchedReservation.getRoomName().getRoomName(), reservation.getRoomName().getRoomName());
    }


}