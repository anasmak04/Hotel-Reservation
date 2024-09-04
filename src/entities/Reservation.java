package entities;



import java.time.LocalDate;


public class Reservation {

    private HotelRoom roomName;
    private Client client;
    private LocalDate startDate;
    private LocalDate endDate;
    private int reservationId = 1;

    public Reservation(){}

    public Reservation(HotelRoom roomName, Client client, LocalDate startDate, LocalDate endDate) {
        this.roomName = roomName;
        this.client = client;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public HotelRoom getRoomName() {
        return roomName;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
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
