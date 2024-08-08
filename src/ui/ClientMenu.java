package ui;

import entities.Client;
import exception.ClientNotFoundException;
import service.ClientService;
import service.HotelRoomService;
import service.ReservationService;

import java.awt.*;
import java.util.Scanner;

public class ClientMenu {
    private final ClientService clientService;
    private final HotelRoomService hotelRoomService;
    private final ReservationService reservationService;
    private final Scanner scanner;

    public ClientMenu(ClientService clientService, HotelRoomService hotelRoomService, ReservationService reservationService) {
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
            System.out.println("1. Enter New Client");
            System.out.println("2. Show All Clients");
            System.out.println("3. Show Client By Id");
            System.out.println("4. Delete Client By Id");
            System.out.println("5. Update Client By Id");
            System.out.println("6. Menu principal");
            System.out.println("7. Exit");
            System.out.println("===============================");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    saveClient();
                    break;
                case 2:
                    findClient();
                    break;
                case 3:
                    findClientById();
                    break;
                case 4:
                    deleteClient();
                    break;
                case 5:
                    updateClient();
                    break;
                case 6:
                    menuPrincipal();
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }



    public void menuPrincipal() {
        ReservationMenu reservationMenu = new ReservationMenu(clientService, hotelRoomService, reservationService);
        reservationMenu.showMenu();
    }

    public void saveClient(){
        System.out.println("Please enter your name: ");
        String name = scanner.nextLine();
        scanner.nextLine();
        scanner.nextLine();
        System.out.println("Please enter your phone: ");
        String phone = scanner.nextLine();
        Client client = new Client(0, name, phone);
        clientService.save(client);
        System.out.println("Your client has been saved");
    }

    public void deleteClient(){
        System.out.println("Please enter if of client : ");
        int clientId = scanner.nextInt();
        try{
            clientService.delete(clientId);
            System.out.println("Client has been deleted");
        }catch(ClientNotFoundException clientNotFoundException){
            System.out.println(clientNotFoundException.getMessage());
        }
    }

    public void findClientById(){
        System.out.println("Please enter client id : ");
        int clientId = scanner.nextInt();
        try{
            clientService.findById(clientId);
            System.out.println("Client has been found");
        }catch(ClientNotFoundException clientNotFoundException){
            System.out.println(clientNotFoundException.getMessage());
        }
    }

    public void findClient(){
        clientService.findAll()
                .forEach(client -> System.out.println("Client Id : " + client.getId() + "Client Name : " + client.getName() + "Client Phone : " + client.getPhone() ));
    }


    public void updateClient(){
        System.out.println("Please enter client id : ");
        int clientId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Please enter client name : ");
        String clientName = scanner.nextLine();
        scanner.nextLine();
        System.out.println("Please enter client phone : ");
        String clientPhone = scanner.nextLine();
        Client client = new Client(clientId,clientName, clientPhone);
        clientService.update(client);
        System.out.println("Client has been updated");
    }


}
