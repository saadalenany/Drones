package com.saad.drones.services;

import java.util.List;

public interface ObjectService<T> {

    T save(T o);

    T update(T o);

    T delete(String id);

    T get(String id) throws RuntimeException;

    List<T> getAll(int page, int rows);

}
