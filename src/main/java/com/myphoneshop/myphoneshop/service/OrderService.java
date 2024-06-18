package com.myphoneshop.myphoneshop.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myphoneshop.myphoneshop.domain.Order;
import com.myphoneshop.myphoneshop.domain.OrderDetail;
import com.myphoneshop.myphoneshop.domain.User;
import com.myphoneshop.myphoneshop.repository.OrderDetailRepository;
import com.myphoneshop.myphoneshop.repository.OrderRepository;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public OrderService(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    public List<Order> getListOrders() {
        return this.orderRepository.findAll();
    }

    public Optional<Order> findByIdOrder(long id) {
        return this.orderRepository.findById(id);
    }

    public List<Order> fetchOrderByUser(User user) {
        return this.orderRepository.findByUser(user);
    }

    @Transactional
    public void deleteOrderById(long id) {
        // delete order detail
        Optional<Order> orderOptional = this.findByIdOrder(id);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            List<OrderDetail> orderDetails = order.getOrderDetails();
            for (OrderDetail orderDetail : orderDetails) {
                this.orderDetailRepository.deleteById(orderDetail.getId());
            }
        }
        this.orderRepository.deleteById(id);
    }

    public void updateOrder(Order order) {
        Optional<Order> orderOptional = this.findByIdOrder(order.getId());
        if (orderOptional.isPresent()) {
            Order currentOrder = orderOptional.get();
            currentOrder.setStatus(order.getStatus());
            this.orderRepository.save(currentOrder);
        }
    }

}
