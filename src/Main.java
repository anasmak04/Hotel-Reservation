import service.ClientService;
import service.HotelRoomService;
import service.ReservationService;
import ui.ReservationMenu;


public class Main {



    public static void main(String[] args)   {
         ClientService clientService = new ClientService();
         HotelRoomService hotelRoomService = new HotelRoomService();
         ReservationService reservationService = new ReservationService(hotelRoomService,clientService);
        ReservationMenu reservationMenu = new ReservationMenu(clientService,hotelRoomService,reservationService);
        reservationMenu.showMenu();
    }
}