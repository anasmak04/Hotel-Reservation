package repository;


import java.util.List;
import java.util.Map;

public interface HotelRepository<T,K>
{
    T save(T t);
    T findById(int id);
    List<Map.Entry<K,T>> findAll();
    T update(int id , T t);
    void delete(int id);
    T findByName(String name);
    List<T> saveMultiple(List<T> t);
}