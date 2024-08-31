package entities;


import java.util.ArrayList;
import java.util.List;

public class HotelRoom {

    private int id;
    private String roomName;
    private RoomType roomType;
    private List<Reservation> reservations = new ArrayList<>();

    public HotelRoom() {}

    public HotelRoom(int id, String roomName, RoomType roomType) {
        this.id = id;
        this.roomName = roomName;
        this.roomType = roomType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public  void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public void removeReservation(Reservation reservation) {
        reservations.remove(reservation);
    }
}
