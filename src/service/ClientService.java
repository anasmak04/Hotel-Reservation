package service;

import entities.Client;
import exception.ClientNotFoundException;
import repository.HotelRepository;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientService implements HotelRepository<Client,Integer> {

    private final Map<Integer, Client> clients = new LinkedHashMap<>();
    int clientId = 1;


    @Override
    public Client save(Client client) {
        clients.put(clientId++, client);
        return client;
    }

    @Override
    public void delete(int id) {
        if(!clients.containsKey(id)){
            throw new ClientNotFoundException("Client Not Found ");
        }
        clients.remove(id);
    }


    @Override
    public Client findById(int id) {
        if(!clients.containsKey(id)){
            throw new ClientNotFoundException("Client Not Found ");
        }
        return clients.get(id);
    }

    @Override
    public List<Map.Entry<Integer,Client>> findAll() {
        Set<Map.Entry<Integer,Client>> entries = clients.entrySet();
        return entries.stream().toList();
    }

    @Override
    public Client update(int id , Client client) {
        if(clients.get(id) == null){
            throw new ClientNotFoundException("Client Not Found ");
        }
        return clients.put(id, client);
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