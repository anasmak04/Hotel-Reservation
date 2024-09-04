package service;

import entities.Client;
import exception.ClientNotFoundException;
import repository.HotelRepository;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;

import java.util.Map;
import java.util.stream.Collectors;

public class ClientService implements HotelRepository<Client> {

    private final Map<Integer, Client> clients = new LinkedHashMap<>();
    int clientId = 1;


    @Override
    public Client findById(int id) {
        if(!clients.containsKey(id)){
            throw new ClientNotFoundException("Client Not Found ");
        }
        return clients.get(id);
    }

    @Override
    public List<Client> findAll() {
        LocalDate today = LocalDate.now();
        if (clients.isEmpty()) {
            System.out.println("No clients found");
        }
        return clients.values().stream()
                .filter(client -> client.getReservations().stream()
                        .noneMatch(reservation ->
                                !reservation.getEndDate().isBefore(today)
                                        && !reservation.getStartDate().isAfter(today)))
                .collect(Collectors.toList());
    }

    @Override
    public Client update(Client client) {
        if(clients.get(client.getId()) == null){
            throw new ClientNotFoundException("Client Not Found ");
        }
        return clients.put(client.getId(), client);
    }

    @Override
    public void delete(int id) {
        if(!clients.containsKey(id)){
            throw new ClientNotFoundException("Client Not Found ");
        }
        clients.remove(id);
    }


    @Override
    public Client save(Client client) {
        client.setId(clientId++);
        clients.put(client.getId(), client);
        return client;
    }

    @Override
    public Client findByName(String name) {
        for(Map.Entry<Integer,Client> entry : clients.entrySet()){
            if(entry.getValue().getName().equals(name)){
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    public List<Client> saveMultiple(List<Client> clientList) {
        return clientList.stream().map(this::save).toList();
    }




}