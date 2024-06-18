package com.myphoneshop.myphoneshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

import com.myphoneshop.myphoneshop.domain.Order;
import com.myphoneshop.myphoneshop.domain.User;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    public List<Order> findAll();

    public Optional<Order> findById(long id);

    public void deleteById(long id);

    List<Order> findByUser(User user);

}
