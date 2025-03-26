package ks.training.sportsShop.repository;

import ks.training.sportsShop.entity.Order;
import ks.training.sportsShop.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT COUNT(od) > 0 FROM OrderDetail od WHERE od.product.id = :productId")
    boolean existsByProductId(Long productId);

    @Query("SELECT o FROM Order o LEFT JOIN o.user u WHERE " +
            "(:customerName IS NULL OR u.fullName LIKE %:customerName%) " +
            "AND (:startOfDay IS NULL OR :endOfDay IS NULL OR o.createdAt BETWEEN :startOfDay AND :endOfDay)")
    Page<Order> findByCustomerNameAndBookingDate(@Param("customerName") String customerName,
                                                 @Param("startOfDay") LocalDateTime startOfDay,
                                                 @Param("endOfDay") LocalDateTime endOfDay,
                                                 Pageable pageable);

    List<Order> findByUser(User user);
}
