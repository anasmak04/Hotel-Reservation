package entities;


import java.util.*;

public class HotelRoom {

    private String roomName;
    private RoomType roomType;
    private Map<Integer,Reservation> reservations;
    private int roomId = 1;
    public HotelRoom() {}

    public HotelRoom(String roomName, RoomType roomType) {
        this.roomName = roomName;
        this.roomType = roomType;
        this.reservations = new LinkedHashMap<>();
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

    public Set<Map.Entry<Integer, Reservation>> getReservations() {
        return reservations.entrySet();
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public  void addReservation(Reservation reservation) {
        reservations.put(roomId++,reservation);
    }

    public void removeReservation(int id) {
        reservations.remove(id);
    }
}
