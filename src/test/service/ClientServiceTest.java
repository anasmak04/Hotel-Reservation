package test.service;

import entities.Client;
import org.junit.Before;
import org.junit.Test;
import service.ClientService;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ClientServiceTest {

    private ClientService clientService;
    private Client client;


    @Before
    public void setUp() throws Exception {
        clientService = new ClientService();
        client = new Client("Anas", "212677693862");
        clientService.save(client);
    }


    @Test
   public void findById() {
       Client fetchedClient =  clientService.findById(client.getClientId());
        assertEquals(1, fetchedClient.getClientId());
        assertEquals("Anas", fetchedClient.getName());
    }

    @Test
   public void findAll() {
        List<Map.Entry<Integer, Client>> fetchedClients = clientService.findAll();
        assertEquals(1, fetchedClients.size());
    }

    @Test
    public void update() {
        Client updatedClient = new Client("Kamal", "212677693862");
        clientService.update(client.getClientId(), updatedClient);
        assertEquals("Kamal", updatedClient.getName());
    }

    @Test
    public void delete() {
        Client fetchedClient =  clientService.findById(client.getClientId());
        clientService.delete(fetchedClient.getClientId());
        assertEquals(0, clientService.findAll().size());
    }

    @Test
    public void save() {
        Client savedClient = new Client("Bilal", "21238932434");
       Client fetchedClient =  clientService.save(savedClient);
        System.out.println(fetchedClient.getClientId());
        assertEquals("Bilal", fetchedClient.getName());
    }
}