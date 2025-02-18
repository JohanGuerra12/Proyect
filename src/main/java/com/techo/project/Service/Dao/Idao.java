package com.techo.project.Service.Dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface Idao <T,ID>{
    List<T> findAll();
    T getById(ID id);
    void  save(T entity);
    void deleteById(ID id);
    Page<T> findAll(Pageable pageable);

}
