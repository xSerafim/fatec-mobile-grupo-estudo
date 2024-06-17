package persistence;

import java.util.List;

public interface ICRUDDAO<T> {

    public void create(T t);
    public void update(T t);
    public void delete(T t);
    public T findOne(T t);
    public List<T> findAll();
}
