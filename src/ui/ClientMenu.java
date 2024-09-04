package ui;

import entities.Client;
import exception.ClientNotFoundException;
import service.ClientService;
import service.HotelRoomService;
import service.ReservationService;

import java.util.Scanner;

public class ClientMenu {
    private final ClientService clientService;
    private final Scanner scanner;
    private final HotelRoomService hotelRoomService;
    private final ReservationService reservationService;

    public ClientMenu(ClientService clientService, HotelRoomService hotelRoomService, ReservationService reservationService) {
        this.clientService = clientService;
        this.reservationService = reservationService;
        this.hotelRoomService = hotelRoomService;
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
            System.out.println("6. Search By Client Name");
            System.out.println("7. Main Menu");
            System.out.println("8. Exit");
            System.out.println("===============================");

            if (!scanner.hasNextInt()){
                System.out.println("Please enter a valid option!");
            }

            else {
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
                        searchByName();
                        break;
                    case 7:
                        menuPrincipal(hotelRoomService, reservationService);
                        break;
                    case 8:
                        return;
                    default:
                        System.out.println("Invalid choice");
                }
            }
        }
    }


    public void menuPrincipal(HotelRoomService hotelRoomService, ReservationService reservationService) {
        ReservationMenu reservationMenu = new ReservationMenu(clientService, hotelRoomService, reservationService);
        reservationMenu.showMenu();
    }

    public void saveClient() {
        System.out.println("Please enter your name: ");
        String name = scanner.nextLine();
        System.out.println("Please enter your phone: ");
        String phone = scanner.nextLine();
        Client client = new Client(name, phone);
        clientService.save(client);
        System.out.println("Your client has been saved");
    }

    public void deleteClient() {
        System.out.println("Please enter if of client : ");
        int clientId = scanner.nextInt();
        try {
            clientService.delete(clientId);
            System.out.println("Client has been deleted With id " + clientId);
        } catch (ClientNotFoundException clientNotFoundException) {
            System.out.println(clientNotFoundException.getMessage());
        }
    }

    public void findClientById() {
        System.out.println("Please enter client id : ");
        int clientId = scanner.nextInt();
        try {
            Client fetchedClient = clientService.findById(clientId);
            System.out.println("Id Client : " + clientId + ", Name Client : " + fetchedClient.getName() + ", Phone Client : " + fetchedClient.getPhone());
        } catch (ClientNotFoundException clientNotFoundException) {
            System.out.println(clientNotFoundException.getMessage());
        }
    }

    public void findClient() {
        clientService.findAll()
                .forEach(client -> System.out.println("Client Id : " + client.getKey() + ", Client Name : " + client.getValue().getName() + ", Client Phone : " + client.getValue().getPhone()));
    }


    public void updateClient() {
       try{
           System.out.println("Please enter client id : ");
           int clientId = scanner.nextInt();
           scanner.nextLine();
           System.out.println("Please enter client name : ");
           String clientName = scanner.nextLine();
           System.out.println("Please enter client phone : ");
           String clientPhone = scanner.nextLine();
           Client client = new Client(clientName, clientPhone);
           clientService.update(clientId,client);
           System.out.println("Client has been updated");
       }catch(ClientNotFoundException clientNotFoundException){
           System.out.println(clientNotFoundException.getMessage());
       }
    }


    public void searchByName() {
        System.out.println("Enter a name of the client: ");
        String nameClient = scanner.nextLine();
        Client searchedClient = clientService.findByName(nameClient);
        System.out.println("Result of Client " + searchedClient.getName() + " is " + searchedClient.getPhone());
    }


}
