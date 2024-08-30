package entities;



import java.time.LocalDate;
import java.util.Date;


public class Reservation {

    private int id;
    private HotelRoom roomName;
    private LocalDate startDate;
    private LocalDate endDate;

    public Reservation(int id, HotelRoom roomName, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.roomName = roomName;
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

    @Override
    public String toString() {
        return "Reservation" +
                "id=" + id +
                ", roomName=" + roomName.getRoomName() +
                ", startDate=" + startDate +
                ", endDate=" + endDate;
    }
}
