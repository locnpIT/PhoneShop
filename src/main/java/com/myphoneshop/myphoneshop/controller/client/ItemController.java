package com.myphoneshop.myphoneshop.controller.client;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.myphoneshop.myphoneshop.domain.Product;
import com.myphoneshop.myphoneshop.service.ProductService;

@Controller
public class ItemController {
    private final ProductService productService;

    public ItemController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product/{id}")
    public String getDetailProduct(Model model, @PathVariable long id) {
        Product selectedProduct = this.productService.getProductById(id);

        model.addAttribute("id", selectedProduct);
        model.addAttribute("product", selectedProduct);

        return "/client/product/detail";
    }

}
