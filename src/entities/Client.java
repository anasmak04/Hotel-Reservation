package entities;

import java.util.*;


public class Client {
    private String name;
    private String phone;
    private Map<Integer,Reservation> reservations;
    private int clientId = 1;

    public Client() {}

    public Client(String name, String phone) {
        this.name = name;
        this.phone = phone;
        this.reservations = new LinkedHashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<Map.Entry<Integer,Reservation>> getReservations() {
        return reservations.entrySet();
    }

    public int getClientId() {
        return clientId;
    }

    public void addReservation(Reservation reservation) {
        reservations.put(clientId++,reservation);
    }

    public void deleteReservation(int id) {
        reservations.remove(id);
    }

}
