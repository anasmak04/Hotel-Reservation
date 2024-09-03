package test.service;

import entities.Client;
import org.junit.Before;
import org.junit.Test;
import service.ClientService;

import java.util.List;

import static org.junit.Assert.assertEquals;

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
        assertEquals(0, fetchedClient.getId());
        assertEquals("Anas", fetchedClient.getName());
    }

    @Test
   public void findAll() {
        List<Client> fetchedClients = clientService.findAll();
        assertEquals(2, fetchedClients.size());
    }

    @Test
    public void update() {
        Client updatedClient = new Client(0 , "Kamal", "212677693862");
        clientService.update(updatedClient);
        assertEquals("Kamal", updatedClient.getName());
    }

    @Test
    public void delete() {
        Client fetchedClient =  clientService.findById(client.getId());
        clientService.delete(fetchedClient.getId());
        assertEquals(1, clientService.findAll().size());
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

    }
}