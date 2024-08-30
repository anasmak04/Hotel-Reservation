package repository;

import entities.HotelRoom;

import java.util.List;

public interface HotelRoomRepository {

    HotelRoom findById(int id);
    List<HotelRoom> findAll();
}
