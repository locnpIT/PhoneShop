package com.myphoneshop.myphoneshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.myphoneshop.myphoneshop.domain.Product;
import com.myphoneshop.myphoneshop.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }

    public Product getProductById(long id) {
        return this.productRepository.findById(id);
    }

    public void deleteProductById(long id) {
        this.productRepository.deleteById(id);
    }

    public Product handleSubmProduct(Product product) {
        return this.productRepository.save(product);
    }

}
