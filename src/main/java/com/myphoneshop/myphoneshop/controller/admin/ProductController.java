package com.myphoneshop.myphoneshop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.myphoneshop.myphoneshop.domain.Product;
import com.myphoneshop.myphoneshop.service.ProductService;
import com.myphoneshop.myphoneshop.service.UploadService;

import jakarta.validation.Valid;

@Controller
public class ProductController {

    private final UploadService uploadService;
    private final ProductService productService;

    public ProductController(ProductService productService, UploadService uploadService) {
        this.productService = productService;
        this.uploadService = uploadService;
    }

    @GetMapping("/admin/product")
    public String getProduct(Model model) {
        java.util.List<Product> listProduct = this.productService.getAllProducts();
        model.addAttribute("products", listProduct);
        return "admin/product/show";
    }

    // Thực hiện tạo sản phẩm
    @GetMapping("/admin/product/create")
    public String getCreateProductPage(Model model) {
        model.addAttribute("newProduct", new Product());
        return "admin/product/create";
    }

    @PostMapping("/admin/product/create")
    public String postCreateProductPage(Model model,
            @Valid @ModelAttribute("newProduct") Product sanPham,
            BindingResult bindingResultProduct,
            @RequestParam("productFile") MultipartFile file) {

        if (bindingResultProduct.hasErrors()) {
            return "admin/product/create";
        }
        String productImage = this.uploadService.handleUploadFile(file, "product");
        sanPham.setImage(productImage);
        this.productService.handleSubmProduct(sanPham);
        return "redirect:/admin/product";

    }

    // Kết thúc tạo sản phẩm

    @GetMapping("/admin/product/{id}")
    public String getProductDetailPage(Model model, @PathVariable long id) {
        Product product = this.productService.getProductById(id);
        model.addAttribute("id", id);
        model.addAttribute("product", product);
        return "admin/product/detail";
    }

    // Thực hiện delete sản phẩm

    @GetMapping("/admin/product/delete/{id}")
    public String getDeleteProductPage(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        model.addAttribute("newProduct", new Product());
        return "admin/product/delete";
    }

    @PostMapping("/admin/product/delete")
    public String postDeleteProductPage(Model model, @ModelAttribute("newProduct") Product product) {
        this.productService.deleteProductById(product.getId());
        return "redirect:/admin/product";
    }

    // Kết thúc delete sản phẩm

    // Thực hiện update sản phẩm

    @GetMapping("/admin/product/update/{id}")
    public String getUpdateProductPage(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        Product currentProduct = this.productService.getProductById(id);
        model.addAttribute("newProduct", currentProduct);
        return "admin/product/update";
    }

    @PostMapping("/admin/product/update")
    public String postUpdateProductPage(Model model,
            @Valid @ModelAttribute("newProduct") Product product,
            BindingResult newProductBidingResult,
            @RequestParam("productFile") MultipartFile file) {

        // validate
        if (newProductBidingResult.hasErrors()) {
            return "/admin/product/update";
        }

        Product currentProduct = this.productService.getProductById(product.getId());

        if (currentProduct != null) {
            if (!file.isEmpty()) {
                String img = this.uploadService.handleUploadFile(file, "product");
                currentProduct.setImage(img);
            }
            currentProduct.setName(product.getName());
            currentProduct.setPrice(product.getPrice());
            currentProduct.setQuantity(product.getQuantity());
            currentProduct.setDetailDesc(product.getDetailDesc());
            currentProduct.setShortDesc(product.getShortDesc());
            currentProduct.setFactory(product.getFactory());
            currentProduct.setTarget(product.getTarget());

            this.productService.handleSubmProduct(currentProduct);
        }

        return "redirect:/admin/product";
    }

    // Kết thúc update sản phẩm

}
