package com.myphoneshop.myphoneshop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.myphoneshop.myphoneshop.domain.Product;

@Controller
public class ProductController {
    @GetMapping("/admin/product")
    public String getProduct(Model model) {
        return "admin/product/show";
    }

    @GetMapping("/admin/product/create")
    public String getCreateProductPage(Model model) {
        model.addAttribute("newProduct", new Product());
        return "admin/product/create";
    }

}
