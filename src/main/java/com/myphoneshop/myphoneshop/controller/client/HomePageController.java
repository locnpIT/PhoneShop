package com.myphoneshop.myphoneshop.controller.client;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.myphoneshop.myphoneshop.domain.Product;
import com.myphoneshop.myphoneshop.service.ProductService;

@Controller
public class HomePageController {

    private final ProductService productService;
    public HomePageController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping("/")
    public String getHomePage(Model model) {
        List<Product> products = this.productService.getAllProducts();
        model.addAttribute("products", products);
        return "client/homepage/show";
    }



    
}
