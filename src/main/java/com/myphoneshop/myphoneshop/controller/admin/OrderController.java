package com.myphoneshop.myphoneshop.controller.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

import com.myphoneshop.myphoneshop.domain.Order;
import com.myphoneshop.myphoneshop.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/admin/order")
    public String getDashboard(Model model, @RequestParam("page") Optional<String> pageOptional) {
        int page = 1;
        try {
            page = Integer.parseInt(pageOptional.get());
        } catch (Exception e) {
            // TODO: handle exception
        }

        Pageable orderPageable = PageRequest.of(page - 1 , 5);
        Page<Order> pageOrder = this.orderService.getListOrders(orderPageable);
        List<Order> listOrders = pageOrder.getContent();
        
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pageOrder.getTotalPages());
        model.addAttribute("listOrders", listOrders);
        return "admin/order/show";
    }

    @GetMapping("/admin/order/detail/{id}")
    public String getDetailOrder(Model model, @PathVariable long id) {
        Order currentOrder = this.orderService.findByIdOrder(id).get();
        model.addAttribute("id", id);
        model.addAttribute("order", currentOrder);
        model.addAttribute("orderDetails", currentOrder.getOrderDetails());
        return "admin/order/detail";
    }

    @GetMapping("/admin/order/delete/{id}")
    public String getDeleteOrderPage(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        model.addAttribute("newOrder", new Order());
        return "admin/order/delete";
    }

    @PostMapping("/admin/order/delete")
    public String postDeleteOrder(@ModelAttribute("newOrder") Order order) {
        this.orderService.deleteOrderById(order.getId());
        return "redirect:/admin/order";
    }

    @GetMapping("/admin/order/update/{id}")
    public String getUpdateOrderPage(Model model, @PathVariable long id) {
        Optional<Order> currentOrder = this.orderService.findByIdOrder(id);
        model.addAttribute("newOrder", currentOrder.get());
        return "admin/order/update";
    }

    @PostMapping("/admin/order/update")
    public String handleUpdateOrder(@ModelAttribute("newOrder") Order order) {
        this.orderService.updateOrder(order);
        return "redirect:/admin/order";
    }

}
