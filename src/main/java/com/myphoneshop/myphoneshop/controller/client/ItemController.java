package com.myphoneshop.myphoneshop.controller.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myphoneshop.myphoneshop.domain.Cart;
import com.myphoneshop.myphoneshop.domain.CartDetail;
import com.myphoneshop.myphoneshop.domain.Product;
import com.myphoneshop.myphoneshop.domain.User;
import com.myphoneshop.myphoneshop.service.ProductService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ItemController {

    private final ProductService productService;

    public ItemController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String getProductPage(Model model,
            @RequestParam("page") Optional<String> pageOptional,
            @RequestParam("name") Optional<String> nameOptional,
            @RequestParam("min-price") Optional<String> minOptional,
            @RequestParam("max-price") Optional<String> maxOptional,
            @RequestParam("factory") Optional<String> factoryOptional,
            @RequestParam("price") Optional<String> priceOptional,
            @RequestParam("sort") Optional<String> sortOptional) {
        int page = 1;
        try {
            page = Integer.parseInt(pageOptional.get());
        } catch (Exception e) {
            // TODO: handle exception
        }

        Pageable pageable = PageRequest.of(page - 1, 60);

        String name = nameOptional.isPresent() ? nameOptional.get() : "";
        Page prs = this.productService.getAllProductsWithSpec(pageable, name);
        // List<Product> products = prs.getContent();

        // case 1
        // double min = minOptional.isPresent() ? Double.parseDouble(minOptional.get())
        // : 0;
        // Page<Product> prs = this.productService.getAllProductsWithSpec(pageable,
        // min);
        // List<Product> products = prs.getContent();

        // case 2
        // double max = maxOptional.isPresent() ? Double.parseDouble(maxOptional.get())
        // : 0;
        // Page<Product> prs = this.productService.getAllProductsWithSpec(pageable,
        // max);
        // List<Product> products = prs.getContent();

        // case 3
        // String factory = factoryOptional.isPresent() ? factoryOptional.get() : "";
        // Page<Product> prs = this.productService.getAllProductsWithSpec(pageable,
        // factory);

        // case 4
        // List<String> factory = Arrays.asList(factoryOptional.get().split(","));
        // Page<Product> prs = this.productService.getAllProductsWithSpec(pageable,
        // factory);

        // case 5
        // String price = priceOptional.isPresent() ? priceOptional.get() : "";
        // Page<Product> prs = this.productService.getAllProductsWithSpec(pageable,
        // price);

        // case 6
        // List<String> price = Arrays.asList(priceOptional.get().split(","));
        // Page<Product> prs = this.productService.getAllProductsWithSpec(pageable,
        // price);

        List<Product> products = prs.getContent();
        model.addAttribute("totalPages", prs.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("products", products);
        return "client/product/show";
    }

    @GetMapping("/product/{id}")
    public String getProductDetailPage(Model model, @PathVariable long id) {
        Product pr = this.productService.getProductById(id).get();
        model.addAttribute("product", pr);
        model.addAttribute("id", id);
        return "client/product/detail";
    }

    @PostMapping("/add-product-to-cart/{id}")
    public String addProductToCart(@PathVariable long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        long productId = id;
        String email = (String) session.getAttribute("email");

        this.productService.handleAddProductToCart(email, productId, session, 1);

        return "redirect:/";
    }

    @GetMapping("/cart")
    public String getCartPage(Model model, HttpServletRequest request) {
        User currentUser = new User();// null
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        currentUser.setId(id);

        Cart cart = this.productService.fetchByUser(currentUser);

        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();

        double totalPrice = 0;
        for (CartDetail cd : cartDetails) {
            totalPrice += cd.getPrice() * cd.getQuantity();
        }

        model.addAttribute("cartDetails", cartDetails);
        model.addAttribute("totalPrice", totalPrice);

        model.addAttribute("cart", cart);

        return "client/cart/show";
    }

    @PostMapping("/delete-cart-product/{id}")
    public String deleteCartDetail(@PathVariable long id, HttpServletRequest request) {
        /*
         * Truyền false có nghĩa là mình sẽ sử dụng session khi mà spring có thôi, nếu
         * không
         * có thì không làm gì cả
         */
        HttpSession session = request.getSession(false);
        long cartDetailId = id;
        this.productService.handleRemoveCartDetail(cartDetailId, session);
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String getCheckOutPage(Model model, HttpServletRequest request) {
        User currentUser = new User();// null
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        currentUser.setId(id);

        Cart cart = this.productService.fetchByUser(currentUser);

        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();

        double totalPrice = 0;
        for (CartDetail cd : cartDetails) {
            totalPrice += cd.getPrice() * cd.getQuantity();
        }

        model.addAttribute("cartDetails", cartDetails);
        model.addAttribute("totalPrice", totalPrice);

        return "client/checkout/checkout";
    }

    @PostMapping("/confirm-checkout")
    public String getCheckOutPage(@ModelAttribute("cart") Cart cart) {
        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();
        this.productService.handleUpdateCartBeforeCheckout(cartDetails);
        return "redirect:/checkout";
    }

    @PostMapping("/place-order")
    public String handlePlaceOrder(
            HttpServletRequest request,
            @RequestParam("receiverName") String receiverName,
            @RequestParam("receiverAddress") String receiverAddress,
            @RequestParam("receiverPhone") String receiverPhone) {

        User currentUser = new User();
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        currentUser.setId(id);

        this.productService.handlePlaceOrder(currentUser, session, receiverName, receiverAddress, receiverPhone);

        return "redirect:/thanks";
    }

    @GetMapping("/thanks")
    public String getThankYouPage() {
        return "client/cart/thanks";
    }

    @PostMapping("/add-product-from-view-detail")
    public String handleAddProductFromVviewDetail(@RequestParam("id") long id, @RequestParam("quantity") long quantity,
            HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String email = (String) session.getAttribute("email");
        this.productService.handleAddProductToCart(email, id, session, quantity);
        return "redirect:/product/" + id;
    }

}
