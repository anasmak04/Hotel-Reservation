package repository;


import java.util.List;

public interface HotelRepository<T>
{
    T save(T t);
    T findById(int id);
    List<T> findAll();
    List<T> saveMultiple(List<T> t);
    T update(T t);
    void delete(int id);
}
