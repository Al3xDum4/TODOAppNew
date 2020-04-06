package com.Alex.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, K> {
    List<T> findAll();
    void save(T t); //Create and Update (INSERT and UPDATE) all together
    void deleteById(K id); // Delete from WHERE id = "id"
    T findById(K id); // SELECT * from TABLE WHERE id = "id"
}
