package entities;



import java.util.Date;


public class Reservation {

    private int id;
    private HotelRoom roomName;
    private Date startDate;
    private Date endDate;

    public Reservation(int id, HotelRoom roomName, Date startDate, Date endDate) {
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
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
