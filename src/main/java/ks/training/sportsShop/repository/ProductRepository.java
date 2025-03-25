package ks.training.sportsShop.repository;

import ks.training.sportsShop.entity.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ProductRepository extends JpaRepository<Product,Long> {
    Page<Product> findAll(Pageable page);

    Page<Product> findAll(Specification<Product> spec, Pageable page);

}
