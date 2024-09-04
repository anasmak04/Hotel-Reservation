import entities.Client;
import entities.HotelRoom;
import entities.RoomType;
import service.ClientService;
import service.HotelRoomService;
import service.ReservationService;
import ui.ReservationMenu;

import java.util.List;


public class Main {


    public static void main(String[] args)   {
         ClientService clientService = new ClientService();
         HotelRoomService hotelRoomService = new HotelRoomService();
         ReservationService reservationService = new ReservationService(hotelRoomService,clientService);

        Client client1 = new Client("Anas", "0637933366");
        Client client2 = new Client("Kareem", "0662598961");
        Client client3 = new Client("Said", "0609352720");
        List<Client> clientList = List.of(client1,client2,client3);

        clientService.saveMultiple(clientList);

        HotelRoom hotelRoom1 = new HotelRoom( "Room 101", RoomType.SINGLE);
        HotelRoom hotelRoom2 = new HotelRoom( "Room 102", RoomType.DELUXE);
        HotelRoom hotelRoom3 = new HotelRoom( "Room 103", RoomType.STUDIO);
        HotelRoom hotelRoom4 = new HotelRoom("Room 104", RoomType.SUITE);
        List<HotelRoom> hotelRoomList = List.of(hotelRoom1,hotelRoom2,hotelRoom3,hotelRoom4);
        hotelRoomService.saveMultiple(hotelRoomList);


         ReservationMenu reservationMenu = new ReservationMenu(clientService,hotelRoomService,reservationService);
        reservationMenu.showMenu();
    }
}