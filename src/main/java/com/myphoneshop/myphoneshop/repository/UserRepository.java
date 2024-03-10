package com.myphoneshop.myphoneshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myphoneshop.myphoneshop.domain.User;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User save(User phuocloc);

    List<User> findByEmail(String email);

    User findById(long id);

    void deleteById(long id);

}
