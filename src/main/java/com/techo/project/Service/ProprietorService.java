package com.techo.project.Service;

import com.techo.project.Entitys.Proprietor;
import com.techo.project.Repository.ProprietorRepository;
import com.techo.project.Service.Dao.Idao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProprietorService  implements Idao<Proprietor , Long> {
    @Autowired
    private ProprietorRepository proprietorRepository;
    @Override
    public List<Proprietor> findAll() {return proprietorRepository.findAll();}

    @Override
    public Proprietor getById(Long id) {return proprietorRepository.getById(id);}

    @Override
    public void save(Proprietor proprietor) { proprietorRepository.save(proprietor);}

    @Override
    public void deleteById(Long id) {proprietorRepository.deleteById(id);}

    @Override
    public Page<Proprietor> findAll(Pageable pageable) {return  proprietorRepository.findAll(pageable);}
}
