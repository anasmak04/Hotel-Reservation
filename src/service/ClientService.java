package service;

import entities.Client;
import exception.ClientNotFoundException;
import repository.HotelRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClientService implements HotelRepository<Client> {

    private final List<Client> clients = new ArrayList<>();
    int clientId = 0;

    public ClientService() {
    }

    @Override
    public Client findById(int id) {
        return clients.stream()
                .filter(client -> client.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ClientNotFoundException("client not found"));
    }

    @Override
    public List<Client> findAll() {
        LocalDate today = LocalDate.now();
        return clients.stream()
                .filter(client -> client.getReservations().stream()
                        .noneMatch(reservation ->
                                !reservation.getEndDate().isBefore(today)
                                        && !reservation.getStartDate().isAfter(today)))
                .collect(Collectors.toList());
    }

    @Override
    public Client update(Client client) {
        Client fetchedClient = findById(client.getId());
        int index = clients.indexOf(fetchedClient);
        clients.set(index, client);
        return client;
    }

    @Override
    public void delete(int id) {
        Client fetchedClient = findById(id);
        clients.remove(fetchedClient);
    }

    @Override
    public Client save(Client client) {
        client.setId(clientId++);
        clients.add(client);
        return client;
    }


}
