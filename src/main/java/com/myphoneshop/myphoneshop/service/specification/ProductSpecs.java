package com.myphoneshop.myphoneshop.service.specification;

import org.springframework.data.jpa.domain.Specification;

import com.myphoneshop.myphoneshop.domain.Product;
import com.myphoneshop.myphoneshop.domain.Product_;

public class ProductSpecs {

    public static Specification<Product> nameLike(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Product_.NAME), "%" + name + "%");
    }

}
