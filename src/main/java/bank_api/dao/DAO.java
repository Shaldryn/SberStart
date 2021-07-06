package bank_api.dao;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {

    Optional<T> getById(long id);
    List<T> getAll();
    void save(T t);
    void update(T t);
    void delete(long id);

}
