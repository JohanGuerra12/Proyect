package com.techo.project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.techo.project.Entitys.Users;
@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
}
