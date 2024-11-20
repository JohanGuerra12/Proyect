package com.techo.project.Service;

import com.techo.project.Entitys.Users;
import com.techo.project.Repository.UserRepository;
import com.techo.project.Service.Dao.Idao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService implements Idao<Users,Long> {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    public List<Users> findAll(){return userRepository.findAll();}

    @Override
    public Users getById(Long id) {return  userRepository.getById(id);}

    @Override
    public void save(Users users) {userRepository.save(users);}

    @Override
    public void deleteById(Long id) {userRepository.deleteById(id);}

    @Override
    public Page<Users> findAll(Pageable pageable) {return this.userRepository.findAll(pageable);}

}
