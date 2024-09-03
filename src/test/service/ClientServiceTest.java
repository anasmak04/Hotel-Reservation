package test.service;

import entities.Client;
import exception.ClientNotFoundException;
import org.junit.Before;
import org.junit.Test;
import service.ClientService;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class ClientServiceTest {

    private ClientService clientService;
    private Client client;


    @Before
    public void setUp() throws Exception {
        clientService = new ClientService();
        client = new Client(0 , "Anas", "212677693862");
        clientService.save(client);
    }


    @Test
   public void findById() {
       Client fetchedClient =  clientService.findById(client.getId());
        assertEquals(1, fetchedClient.getId());
        assertEquals("Anas", fetchedClient.getName());
    }

    @Test
   public void findAll() {
        List<Client> fetchedClients = clientService.findAll();
        assertEquals(1, fetchedClients.size());
    }

    @Test
    public void update() {
        Client updatedClient = new Client(0 , "Kamal", "212677693862");
        clientService.update(updatedClient);
        assertEquals("Kamal", updatedClient.getName());
    }

    @Test
    public void delete() {
        Client fetchedClient = clientService.findById(client.getId());
        clientService.delete(fetchedClient.getId());
        assertThrows(ClientNotFoundException.class, () -> clientService.findById(client.getId()));
    }

    @Test
    public void save() {
        Client savedClient = new Client(99, "Bilal", "21238932434");
       Client fetchedClient =  clientService.save(savedClient);
        System.out.println(fetchedClient.getId());
        assertEquals("Bilal", fetchedClient.getName());
    }

    @Test
    public void saveMultiple() {
        Client client1 = new Client(0, "AMINE", "21238932434");
        Client client2 = new Client(0, "AYOUB", "21238932434");
        List<Client> clientList = List.of(client1, client2);
        clientService.saveMultiple(clientList);
        assertEquals(2, clientList.size());
        assertEquals("AMINE", clientList.get(0).getName());
        System.out.println(clientList.get(0).getId());
    }
}