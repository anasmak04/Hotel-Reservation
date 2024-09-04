package repository;


import java.util.List;

public interface HotelRepository<T>
{
    T save(T t);
    T findById(int id);
    List<T> findAll();
    T update(T t);
    void delete(int id);
    T findByName(String name);
    List<T> saveMultiple(List<T> t);
}