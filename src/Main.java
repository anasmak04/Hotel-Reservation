import entities.Client;
import service.ClientService;
import service.HotelRoomService;
import service.ReservationService;
import ui.ClientMenu;
import ui.ReservationMenu;
import ui.RoomMenu;

import java.util.Scanner;

public class Main {



    public static void main(String[] args)   {
        HotelRoomService hotelRoomService = new HotelRoomService();
        ClientService clientService = new ClientService();
        ReservationService reservationService = new ReservationService(hotelRoomService,clientService);

        ReservationMenu reservationMenu = new ReservationMenu(clientService,hotelRoomService,reservationService);
        reservationMenu.showMenu();
    }
}