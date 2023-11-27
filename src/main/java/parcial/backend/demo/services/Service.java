package parcial.backend.demo.services;

import java.util.List;

public interface Service<T, ID> {

    T add(T entity);
    T update(Long id, T entity);
    T delete(ID id);
    T getById(ID id);
    List<T> getAll();

}

