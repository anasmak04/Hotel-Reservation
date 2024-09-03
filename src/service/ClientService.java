package service;

import entities.Client;
import exception.ClientNotFoundException;
import exception.HotelRoomNotFoundException;
import repository.HotelRepository;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;


public class ClientService implements HotelRepository<Client> {

    private final List<Client> clients = new ArrayList<>();



    @Override
    public Client findById(int id) {
        return clients.stream().
                filter(r -> r.getId() == id)
                .findFirst().orElseThrow((() -> new ClientNotFoundException("Client not found")));
    }

    @Override
    public List<Client> findAll() {
        LocalDate today = LocalDate.now();
        if (clients.isEmpty()) {
            System.out.println("No clients found");
        }
        return clients.stream()
                .filter(client -> client.getReservations().stream()
                        .noneMatch(reservation ->
                                !reservation.getEndDate().isBefore(today)
                                        && !reservation.getStartDate().isAfter(today)))
                .toList();
    }

    @Override
    public Client save(Client client) {
        client.setId(clients.size() + 1);
        clients.add(client);
        return client;
    }

    @Override
    public List<Client> saveMultiple(List<Client> clients) {
        return clients.stream().map(this::save).toList();
    }

    @Override
    public Client update(Client client) {
        if(clients.get(client.getId()) == null){
            throw new ClientNotFoundException("Client Not Found ");
        }
         clients.add(client.getId(),client);
        return client;
    }

    @Override
    public void delete(int id) {
        if(clients.isEmpty()){
            throw new ClientNotFoundException("Client Not Found ");
        }
        Client client = findById(id);
        if(client == null){
            throw new ClientNotFoundException("Client  not found");
        }
        clients.remove(client);
    }



}