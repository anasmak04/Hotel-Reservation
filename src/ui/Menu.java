package ui;

import entities.HotelRoom;
import entities.Reservation;
import exception.HotelRoomNotFoundException;
import exception.ReservationNotFoundException;
import service.HotelRoomService;
import service.ReservationService;
import utils.DateFormat;

import java.time.LocalDate;
import java.util.Scanner;

public class Menu {

    ReservationService reservationService;
    HotelRoomService hotelRoomService;
     Scanner scanner;

    public Menu() {
        this.reservationService = new ReservationService();
        this.hotelRoomService = new HotelRoomService();
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
            System.out.println("3. Show Reservation By Id");
            System.out.println("4. Delete Reservation By Id");
            System.out.println("5. Update Reservation By Id");
            System.out.println("6. Create New Room");
            System.out.println("7. Exit");
            System.out.println("===============================");

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
                    createRoom();
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }


    public void createRoom(){
        System.out.println("Please enter a new room:");
        String roomName = scanner.nextLine();
        HotelRoom hotelRoom = new HotelRoom(0, roomName);
        hotelRoom = hotelRoomService.save(hotelRoom);
        System.out.println("Room created successfully with ID: " + hotelRoom.getId());
    }

    public void createReservation(){
        System.out.println("Available Hotel Rooms:");
        hotelRoomService.findAll().forEach(room -> System.out.println("ID: " + room.getId() + ", Name: " + room.getRoomName()));

        System.out.println("Enter Hotel Room ID to reserve:");
        int roomId = scanner.nextInt();
        scanner.nextLine();

        HotelRoom hotelRoom;

        try {
            hotelRoom = hotelRoomService.findById(roomId);
        } catch (HotelRoomNotFoundException e) {
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
        }

        if (endDate.isBefore(startDate)) {
            System.out.println("End date cannot be before start date.");
        }

        Reservation reservation = new Reservation(0, hotelRoom, startDate, endDate);

        boolean reservationExists = reservationService.findAll().stream()
               .anyMatch(existingReservation -> checkReservationIfExist(existingReservation, hotelRoom, startDate, endDate));

        if (reservationExists) {
            System.out.println("The room is already reserved for the selected dates.");
        }


        reservationService.save(reservation);
        System.out.println("Reservation created successfully with ID: " + reservation.getId());
    }

    private boolean checkReservationIfExist(Reservation existingReservation, HotelRoom hotelRoom, LocalDate startDate, LocalDate endDate) {
        if (existingReservation.getRoomName().getId() != hotelRoom.getId()) {
            return false;
        }
        LocalDate existingStartDate = existingReservation.getStartDate();
        LocalDate existingEndDate = existingReservation.getEndDate();
        return startDate.isBefore(existingEndDate) && endDate.isAfter(existingStartDate);
    }


    public void showById(){
        System.out.println("Enter reservation id");
        int reservationId = scanner.nextInt();

        try{
            reservationService.findById(reservationId);
        }catch (ReservationNotFoundException r){
            System.out.println(r.getMessage());
        }
    }



    public void deleteReservation(){
        System.out.println("Enter Reservation ID to reserve:");
        int reservationId  = scanner.nextInt();
        try{
            reservationService.delete(reservationId);
            System.out.println("Reservation deleted Successfully.");
        }catch(ReservationNotFoundException r){
            System.out.println(r.getMessage());
        }
    }

    public void showAll() {
        reservationService.findAll().forEach(reservation -> System.out.println("ID: " + reservation.getId() + ", Name: " + reservation.getRoomName().getRoomName() + ", StartDate " + reservation.getStartDate() + ", EndDate " + reservation.getEndDate() + ", EndDate " + reservation.getEndDate()));
    }

    public void editReservation()   {
        System.out.println("Enter reservation room id");
        int reservationId = scanner.nextInt();


        System.out.println("Enter Hotel Room ID to reserve:");
        int roomId = scanner.nextInt();
        scanner.nextLine();

        HotelRoom hotelRoom;

        try {
            hotelRoom = hotelRoomService.findById(roomId);
        } catch (HotelRoomNotFoundException e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.println("Enter Reservation Start Date (YYYY-MM-DD):");
        String startDateInput = scanner.nextLine();
        LocalDate startDate = DateFormat.parseDate(startDateInput);

        System.out.println("Enter Reservation End Date (YYYY-MM-DD):");
        String endDateInput = scanner.nextLine();
        LocalDate endDate = DateFormat.parseDate(endDateInput);

        Reservation reservation = new Reservation(reservationId, hotelRoom, startDate, endDate);

        reservationService.update(reservation);
    }


}
