package ks.training.sportsShop.service.specification;


import ks.training.sportsShop.entity.Product;
import ks.training.sportsShop.entity.Product_;
import org.springframework.data.jpa.domain.Specification;


public class ProductSpecs {
    public static Specification<Product> nameLike(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Product_.brand), "%" + name + "%");
    }

    public static Specification<Product> matchMultiplePrice(double min, double max) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(
                root.get(Product_.PRICE), min, max);
    }

}
