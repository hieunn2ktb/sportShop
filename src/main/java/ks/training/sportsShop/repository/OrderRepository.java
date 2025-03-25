package ks.training.sportsShop.repository;

import ks.training.sportsShop.entity.Order;
import ks.training.sportsShop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    @Query("SELECT COUNT(od) > 0 FROM OrderDetail od WHERE od.product.id = :productId")
    boolean existsByProductId(Long productId);

    List<Order> findByUser(User user);
}
