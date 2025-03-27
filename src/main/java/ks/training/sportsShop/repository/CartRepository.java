package ks.training.sportsShop.repository;

import ks.training.sportsShop.entity.Cart;
import ks.training.sportsShop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {
    Cart findByUser(User user);
}
