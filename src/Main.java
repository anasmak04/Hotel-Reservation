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


        HotelRoomService hotelRoomService = new HotelRoomService();

        HotelRoom hotelRoom1 = new HotelRoom(0,"ROOM1", RoomType.SINGLE);
        HotelRoom hotelRoom2 = new HotelRoom(0,"ROOM2", RoomType.SUITE);
        HotelRoom hotelRoom3 = new HotelRoom(0,"ROOM3", RoomType.DELUXE);
        HotelRoom hotelRoom4 = new HotelRoom(0,"ROOM4", RoomType.STUDIO);
        List<HotelRoom> hotelRoomList =  List.of(hotelRoom1,hotelRoom2,hotelRoom3,hotelRoom4);
        hotelRoomService.saveMultiple(hotelRoomList);

        ClientService clientService = new ClientService();
        Client client1 = new Client(0,"Anas", "0637933366");
        Client client2 = new Client(0,"Bilal", "0637933366");
        Client client3 = new Client(0,"Atmane", "0637933366");
        Client client4 = new Client(0,"Amine", "0637933366");
        List<Client> clientList = List.of(client1,client2,client3,client4);
        clientService.saveMultiple(clientList);

        ReservationService reservationService = new ReservationService(hotelRoomService,clientService);

        ReservationMenu reservationMenu = new ReservationMenu(clientService,hotelRoomService,reservationService);
        reservationMenu.showMenu();
    }
}