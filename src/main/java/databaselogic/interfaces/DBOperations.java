package databaselogic.interfaces;

import java.util.List;

public interface DBOperations<T> {

    //TODO: CRUD
    boolean save(T o);

    List<T> getAll();

    T get(int id);

    T get(String title);

    boolean delete(int id);
}
