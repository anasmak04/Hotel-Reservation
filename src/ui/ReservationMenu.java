package ui;

import entities.Client;
import entities.HotelRoom;
import entities.Reservation;
import exception.ClientNotFoundException;
import exception.HotelRoomNotFoundException;
import exception.ReservationNotFoundException;
import service.ClientService;
import service.HotelRoomService;
import service.ReservationService;
import utils.DateFormat;

import java.time.LocalDate;
import java.util.Scanner;

public class ReservationMenu {

    private final ClientService clientService;
    private final HotelRoomService hotelRoomService;
    private final ReservationService reservationService;
    private final Scanner scanner;

    public ReservationMenu(ClientService clientService, HotelRoomService hotelRoomService, ReservationService reservationService) {
        this.clientService = clientService;
        this.hotelRoomService = hotelRoomService;
        this.reservationService = reservationService;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        while (true) {

            System.out.println("\n===============================");
            System.out.println("Welcome to the Hotel Reservation System");
            System.out.println("===============================");
            System.out.println("Please enter your choice:");
            System.out.println("1. Enter New Reservation");
            System.out.println("2. Show All Reservations");
            System.out.println("3. Show Reservation by ID");
            System.out.println("4. Delete Reservation by ID");
            System.out.println("5. Update Reservation by ID");
            System.out.println("6. Show Available Rooms");
            System.out.println("7. Show All Clients");
            System.out.println("8. Exit");
            System.out.println("===============================");
            if (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid option!");
            }

            else {
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        createReservation();
                        break;
                    case 2:
                        showAll();
                        break;
                    case 3:
                        showById();
                        break;
                    case 4:
                        deleteReservation();
                        break;
                    case 5:
                        editReservation();
                        break;

                    case 6:
                        HotelRoomMenu();
                        break;
                    case 7:
                        clientMenu();
                        break;
                    case 8:
                        return;
                    default:
                        System.out.println("Invalid choice");
                }
            }
        }
    }

    public void HotelRoomMenu() {
        RoomMenu roomMenu = new RoomMenu(hotelRoomService,clientService,reservationService);
        roomMenu.HotelRoomMenu();
    }




    public void createReservation() {

        System.out.println("Enter Hotel Room ID to reserve:");
        int roomId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter Client ID to reserve:");
        int clientId = scanner.nextInt();
        scanner.nextLine();
        HotelRoom hotelRoom;
        Client client;
        try {
            hotelRoom = hotelRoomService.findById(roomId);
            client = clientService.findById(clientId);
        } catch (HotelRoomNotFoundException | ClientNotFoundException e) {
            System.out.println(e.getMessage());
            return;
        }

        LocalDate DateNow = LocalDate.now();
        System.out.println("Enter Reservation Start Date (YYYY-MM-DD):");
        String startDateInput = scanner.nextLine();
        LocalDate startDate = DateFormat.parseDate(startDateInput);

        System.out.println("Enter Reservation End Date (YYYY-MM-DD):");
        String endDateInput = scanner.nextLine();
        LocalDate endDate = DateFormat.parseDate(endDateInput);

        if (startDate.isBefore(DateNow)) {
            System.out.println("Start date cannot be in the past.");
            return;
        }

        if (endDate.isBefore(startDate)) {
            System.out.println("End date cannot be before start date.");
            return;
        }

        if (!hotelRoomService.isRoomAvailable(roomId, startDate, endDate, 0)) {
            System.out.println("The room is not available for the selected dates.");
            return;
        }



        Reservation reservation = new Reservation(hotelRoom, client, startDate, endDate);

        reservationService.save(reservation);
    }


    public void showById() {
        System.out.println("Enter reservation id");
        int reservationId = scanner.nextInt();
        scanner.nextLine();
        try {
            Reservation fetchedReservation = reservationService.findById(reservationId);
            System.out.println(String.format(
                    "Reservation ID: %d\nClient Name: %s\nClient Phone: %s\nRoom Name: %s\nStart Date: %s\nEnd Date: %s",
                    reservationId,
                    fetchedReservation.getClient().getName(),
                    fetchedReservation.getClient().getPhone(),
                    fetchedReservation.getRoomName().getRoomName(),
                    fetchedReservation.getStartDate(),
                    fetchedReservation.getEndDate()
            ));
        } catch (ReservationNotFoundException r) {
            System.out.println(r.getMessage());
        }
    }



    public void deleteReservation() {
        System.out.println("Enter Reservation ID to reserve:");
        int reservationId = scanner.nextInt();
        try {
            reservationService.delete(reservationId);
            System.out.println("Reservation deleted Successfully.");
        } catch (ReservationNotFoundException r) {
            System.out.println(r.getMessage());
        }
    }

    public void showAll() {
//        LocalDate date = LocalDate.now();
       try{
           reservationService.findAll().stream()
                   .forEach(reservation ->
                           System.out.println(String.format(
                                   "ID: %d\nRoom Name: %s\nClient Name: %s\nClient Phone: %s\nStart Date: %s\nEnd Date: %s",
                                   reservation.getKey(),
                                   reservation.getValue().getRoomName().getRoomName(),
                                   reservation.getValue().getClient().getName(),
                                   reservation.getValue().getClient().getPhone(),
                                   reservation.getValue().getStartDate(),
                                   reservation.getValue().getEndDate()
                           ))
                   );
       }catch (ReservationNotFoundException r){
           System.out.println(r.getMessage());
       }
    }




    public void clientMenu() {
        ClientMenu clientMenu = new ClientMenu(clientService,hotelRoomService,reservationService);
        clientMenu.showMenu();
    }

    public void editReservation() {
        System.out.println("Enter Reservation Id");
        int reservationId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter Hotel Room ID to reserve:");
        int roomId = scanner.nextInt();
        scanner.nextLine();



        System.out.println("Enter Client ID to reserve:");
        int clientId = scanner.nextInt();
        scanner.nextLine();


        HotelRoom hotelRoom;
        Client client;
        try {
            client = clientService.findById(clientId);
            hotelRoom = hotelRoomService.findById(roomId);
        } catch (HotelRoomNotFoundException | ClientNotFoundException e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.println("Enter Reservation Start Date (YYYY-MM-DD):");
        String startDateInput = scanner.nextLine();
        LocalDate startDate = DateFormat.parseDate(startDateInput);

        System.out.println("Enter Reservation End Date (YYYY-MM-DD):");
        String endDateInput = scanner.nextLine();
        LocalDate endDate = DateFormat.parseDate(endDateInput);

        if (!hotelRoomService.isRoomAvailable(roomId, startDate, endDate, 0)) {
            System.out.println("The room is not available for the selected dates.");
            return;
        }

        Reservation reservation = new Reservation(hotelRoom, client, startDate, endDate);
        reservationService.update(reservationId,reservation);
    }
}