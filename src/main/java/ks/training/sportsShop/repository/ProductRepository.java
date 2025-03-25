package ks.training.sportsShop.repository;

import ks.training.sportsShop.entity.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ProductRepository extends JpaRepository<Product,Long> {
    Page<Product> findAll(Pageable page);

    @Query("SELECT p FROM Product p LEFT JOIN p.category c " +
            "WHERE (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
            "AND (:category IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :category, '%')))")
    Page<Product> searchProducts(
            @Param("name") String name,
            @Param("category") String category,
            Pageable pageable);



    Page<Product> findAll(Specification<Product> spec, Pageable page);

}
