package com.grupocinco.kilosapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class BaseServiceImpl<T, ID, R extends JpaRepository<T, ID>> implements BaseService<T, ID> {

    @Autowired
    protected R repository;


    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }

    @Override
    public T save(T a) {
        return repository.save(a);
    }

    @Override
    public List<T> saveAll(List<T> list) {
        return repository.saveAll(list);
    }

    @Override
    public T edit(T a) {
        return save(a);
    }

    @Override
    public void delete(T a) {
        repository.delete(a);
    }

    @Override
    public void deleteById(ID id) {
        repository.deleteById(id);
    }

}