package ks.training.sportsShop.service;

import jakarta.validation.Valid;
import ks.training.sportsShop.entity.Order;
import ks.training.sportsShop.entity.OrderDetail;
import ks.training.sportsShop.entity.User;
import ks.training.sportsShop.repository.OrderDetailRepository;
import ks.training.sportsShop.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public OrderService(
            OrderRepository orderRepository,
            OrderDetailRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
        this.orderRepository = orderRepository;
    }

    public Page<Order> fetchAllOrders(String customerName, LocalDate startDate, LocalDate endDate, Pageable page) {
        LocalDateTime startOfDay = null;
        LocalDateTime endOfDay = null;
        if (startDate != null && endDate != null) {
            startOfDay = startDate.atStartOfDay();
            endOfDay = endDate.atTime(LocalTime.MAX);
        }
        return this.orderRepository.findByCustomerNameAndBookingDate(customerName,startOfDay,endOfDay,page);
    }

    public Optional<Order> fetchOrderById(long id) {
        return this.orderRepository.findById(id);
    }

    public void deleteOrderById(long id) {
        // delete order detail
        Optional<Order> orderOptional = this.fetchOrderById(id);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            List<OrderDetail> orderDetails = order.getOrderDetails();
            for (OrderDetail orderDetail : orderDetails) {
                this.orderDetailRepository.deleteById(orderDetail.getId());
            }
        }

        this.orderRepository.deleteById(id);
    }

    public void updateOrder(Order order,String status, String cancelReason) {
        Optional<Order> orderOptional = this.fetchOrderById(order.getId());

        order.setStatus(status);
        if (orderOptional.isPresent()) {
            Order currentOrder = orderOptional.get();
            if ("CANCEL".equals(status)) {
                currentOrder.setCancelReason(cancelReason);
            }else {
                currentOrder.setCancelReason(null);
            }
            currentOrder.setStatus(order.getStatus());
            this.orderRepository.save(currentOrder);
        }
    }

    public List<Order> fetchOrderByUser(User user) {
        return this.orderRepository.findByUser(user);
    }

    public void createOrder(@Valid Order order) {
        orderRepository.save(order);
    }
}
