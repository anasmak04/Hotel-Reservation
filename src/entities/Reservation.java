package entities;



import java.time.LocalDate;


public class Reservation {

    private int id;
    private HotelRoom roomName;
    private Client client;
    private LocalDate startDate;
    private LocalDate endDate;

    public Reservation(int id, HotelRoom roomName, Client client, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.roomName = roomName;
        this.client = client;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public HotelRoom getRoomName() {
        return roomName;
    }

    public void setRoomName(HotelRoom roomName) {
        this.roomName = roomName;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
