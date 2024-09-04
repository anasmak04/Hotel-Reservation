package ui;

import entities.HotelRoom;
import entities.RoomType;
import exception.HotelRoomNotFoundException;
import service.ClientService;
import service.HotelRoomService;
import service.ReservationService;

import java.util.Scanner;

public class RoomMenu {

    private final HotelRoomService hotelRoomService;
    private final Scanner scanner;
    private final ClientService clientService;
    private final ReservationService reservationService;

    public RoomMenu(HotelRoomService hotelRoomService, ClientService clientService, ReservationService reservationService) {
        this.hotelRoomService = hotelRoomService;
        this.clientService = clientService;
        this.reservationService = reservationService;
        this.scanner = new Scanner(System.in);
    }

    public void HotelRoomMenu() {
        while (true) {
            System.out.println("\n===============================");
            System.out.println("Welcome to the Room System");
            System.out.println("===============================");
            System.out.println("1. Create New Room");
            System.out.println("2. Available rooms");
            System.out.println("3. Show Room By Id");
            System.out.println("4. Update Room By Id");
            System.out.println("5. Delete Room By Id");
            System.out.println("6. Back To Main Menu");
            System.out.println("7. Exit");

            System.out.print("Please enter your choice: ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        createRoom();
                        break;
                    case 2:
                        showAllRoom();
                        break;
                    case 3:
                        showRoomById();
                        break;
                    case 4:
                        update();
                        break;
                    case 5:
                        deleteRoomById();
                        break;
                    case 6:
                        MainMenu(clientService, reservationService);
                        break;
                    case 7:
                        return;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 7.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }
    }

    public void MainMenu(ClientService clientService, ReservationService reservationService) {
        ReservationMenu reservationMenu = new ReservationMenu(clientService, hotelRoomService, reservationService);
        reservationMenu.showMenu();
    }

    public void createRoom() {
        System.out.println("Please enter a new room:");
        String roomName = scanner.nextLine();
        System.out.println("Please enter type of the room (SINGLE, DELUXE, SUITE, DELUXE):");
        String roomTypeInput = scanner.nextLine();
        RoomType roomType = RoomType.valueOf(roomTypeInput.toUpperCase());
        HotelRoom hotelRoom = new HotelRoom(roomName, roomType);
        hotelRoom = hotelRoomService.save(hotelRoom);
        System.out.println("Room created successfully with ID: " + hotelRoom.getRoomId());
    }


    public void showAllRoom() {
       hotelRoomService.findAll().forEach(room ->
               System.out.println("Room ID: " + room.getKey()
                       + ", Room Name: " + room.getValue().getRoomName() + ", Type: " + room.getValue().getRoomType()));
    }


    public void showRoomById() {
        System.out.println("Please enter a room ID:");
        int roomId = scanner.nextInt();

        try {
            HotelRoom fetchedHotelRoom = hotelRoomService.findById(roomId);
            System.out.println("Room Details:");
            System.out.println("--------------------");
            System.out.println("Room ID: " + fetchedHotelRoom.getRoomId());
            System.out.println("Room Name     : " + fetchedHotelRoom.getRoomName());
            System.out.println("Room Type     : " + fetchedHotelRoom.getRoomType());
            System.out.println("--------------------");
        } catch (HotelRoomNotFoundException hotelRoomNotFoundException) {
            System.out.println(hotelRoomNotFoundException.getMessage());
        }
    }

    public void deleteRoomById() {
        System.out.println("Please enter a room ID:");
        int roomId = scanner.nextInt();
        try {
            hotelRoomService.delete(roomId);
            System.out.println("Room deleted successfully with ID: " + roomId);
        } catch (HotelRoomNotFoundException hotelRoomNotFoundException) {
            System.out.println(hotelRoomNotFoundException.getMessage());
        }
    }

    public void update() {
       try{
           System.out.println("enter id of room that u want to update");
           int roomId = scanner.nextInt();

           scanner.nextLine();

           System.out.println("Please enter new room name:");
           String roomName = scanner.nextLine();
           System.out.println("Please enter type of the room (SINGLE, DELUXE, SUITE, DELUXE):");
           String roomTypeInput = scanner.nextLine();
           RoomType roomType = RoomType.valueOf(roomTypeInput.toUpperCase());
           HotelRoom hotelRoom = new HotelRoom(roomName, roomType);
           hotelRoomService.update(roomId,hotelRoom);
           System.out.println("Room updated successfully with : ");
       }catch (HotelRoomNotFoundException hotelRoomNotFoundException) {
           System.out.println(hotelRoomNotFoundException.getMessage());
       }
    }
}
