package service;

import entities.Client;
import exception.ClientNotFoundException;
import exception.HotelRoomNotFoundException;
import repository.HotelRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;

public class ClientService implements HotelRepository<Client> {

    private final List< Client> clients = new ArrayList<>();
    int clientId = 1;


    @Override
    public Client findById(int id) {
        return clients.stream().
                filter(r -> r.getId() == id)
                .findFirst().orElseThrow((() -> new HotelRoomNotFoundException("hotel room not found")));
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
                .collect(Collectors.toList());
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
         clients.add(client);
        return client;
    }

    @Override
    public void delete(int id) {
        if(clients.get(id) == null){
            throw new ClientNotFoundException("Client Not Found ");
        }
        clients.remove(id);
    }

    @Override
    public Client save(Client client) {
        client.setId(++clientId);
        clients.add(client);
        return client;
    }

}