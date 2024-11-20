package com.techo.project.Service;

import com.techo.project.Entitys.Apartament;
import com.techo.project.Repository.ApartamentRepository;
import com.techo.project.Service.Dao.Idao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApartamentService implements Idao<Apartament ,Long> {
    @Autowired
    private ApartamentRepository apartamentRepository;
    @Override
    public List<Apartament> findAll() {return apartamentRepository.findAll();}

    @Override
    public Apartament getById(Long id) {return apartamentRepository.getById(id);}

    @Override
    public void save(Apartament apartament) {apartamentRepository.save(apartament);}

    @Override
    public void deleteById(Long id) {apartamentRepository.deleteById(id);}

    @Override
    public Page<Apartament> findAll(Pageable pageable) {return apartamentRepository.findAll(pageable);}
}
