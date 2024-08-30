import entities.HotelRoom;
import entities.Reservation;
import exception.HotelRoomNotFoundException;
import exception.ReservationNotFoundException;
import service.HotelRoomService;
import service.ReservationService;
import utils.DateFormat;

import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

public class Main {


    ReservationService reservationService = new ReservationService();
    HotelRoomService hotelRoomService = new HotelRoomService();

    Scanner scanner = new Scanner(System.in);

    private void showMenu() throws ParseException {
        while (true) {
            Scanner scanner = new Scanner(System.in);

            System.out.println("\n===============================");
            System.out.println("Welcome to the Hotel Reservation System");
            System.out.println("===============================");
            System.out.println("Please enter your choice:");
            System.out.println("1. Enter New Reservation");
            System.out.println("2. Show All Reservations");
            System.out.println("3. Show Reservation By Id");
            System.out.println("4. Delete Reservation By Id");
            System.out.println("5. Update Reservation By Id");
            System.out.println("6. Exit");
            System.out.println("===============================");

            int choice = scanner.nextInt();
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
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    public void showAll() {
        reservationService.findAll().forEach(System.out::println);
    }


    public void createReservation() throws ParseException {
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


        System.out.println("Enter Reservation Start Date (YYYY-MM-DD):");
        String startDateInput = scanner.nextLine();
        Date startDate = DateFormat.parseDate(startDateInput);

        System.out.println("Enter Reservation End Date (YYYY-MM-DD):");
        String endDateInput = scanner.nextLine();
        Date endDate = DateFormat.parseDate(endDateInput);

        Reservation reservation = new Reservation(0, hotelRoom, startDate, endDate);

        boolean reservationExists = reservationService.findAll().stream()
                .anyMatch(existingReservation -> isExist(existingReservation, hotelRoom, startDate, endDate));

        if (reservationExists) {
            System.out.println("The room is already reserved for the selected dates.");
            return;
        }

        reservationService.save(reservation);

        System.out.println("Reservation created successfully with ID: " + reservation.getId());
    }


    public void showById() {
        System.out.println("Enter reservation room id");
        int reservationId = scanner.nextInt();

        try {
            System.out.println(reservationService.findById(reservationId));
        } catch (ReservationNotFoundException e) {
            System.out.println(e.getMessage());
            return;
        }
    }


    public void editReservation() throws ParseException {
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
        Date startDate = DateFormat.parseDate(startDateInput);

        System.out.println("Enter Reservation End Date (YYYY-MM-DD):");
        String endDateInput = scanner.nextLine();
        Date endDate = DateFormat.parseDate(endDateInput);

        Reservation reservation = new Reservation(reservationId, hotelRoom, startDate, endDate);

        reservationService.update(reservation);
    }

    public void deleteReservation() {
        System.out.println("Enter reservation ID");
        int reservationId = scanner.nextInt();
        try {
            reservationService.delete(reservationId);
            System.out.println("Reservation deleted Successfully.");
        } catch (ReservationNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean isExist(Reservation existingReservation, HotelRoom hotelRoom, Date startDate, Date endDate) {
//        System.out.println("Checking room ID: " + hotelRoom.getId() + " against existing reservation room ID: " + existingReservation.getRoomName().getId());
        if (existingReservation.getRoomName().getId() != hotelRoom.getId()) {
            return false;
        }

        Date existingStartDate = existingReservation.getStartDate();
        Date existingEndDate = existingReservation.getEndDate();

//        System.out.println("Checking dates: new reservation [" + startDate + " to " + endDate + "] against existing reservation [" + existingStartDate + " to " + existingEndDate + "]");
        return startDate.before(existingEndDate) && endDate.after(existingStartDate);
    }




    public static void main(String[] args) throws ParseException {
        Main main = new Main();
        main.showMenu();
    }
}