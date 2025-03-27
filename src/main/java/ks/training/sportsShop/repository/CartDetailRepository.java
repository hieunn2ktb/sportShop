package ks.training.sportsShop.repository;

import ks.training.sportsShop.entity.Cart;
import ks.training.sportsShop.entity.CartDetail;
import ks.training.sportsShop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {
    CartDetail findByCartAndProduct(Cart cart, Product realProduct);
}
