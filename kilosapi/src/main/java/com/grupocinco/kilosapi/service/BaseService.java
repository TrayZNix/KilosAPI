package com.grupocinco.kilosapi.service;

import java.util.List;
import java.util.Optional;

public interface BaseService<T, ID> {


    public List<T> findAll();

    public Optional<T> findById(ID id);

    public T save(T a);

    public List<T> saveAll(List<T> list);

    public T edit(T a);

    public void delete(T a);

    public void deleteById(ID id);


}