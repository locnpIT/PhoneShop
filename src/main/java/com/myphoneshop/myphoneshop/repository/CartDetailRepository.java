package com.myphoneshop.myphoneshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myphoneshop.myphoneshop.domain.Cart;
import com.myphoneshop.myphoneshop.domain.CartDetail;
import com.myphoneshop.myphoneshop.domain.Product;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {
    boolean existsByCartAndProduct(Cart cart, Product product);
    CartDetail findByCartAndProduct(Cart cart, Product product);
}
