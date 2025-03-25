package ks.training.sportsShop.service;

import ks.training.sportsShop.entity.Category;
import ks.training.sportsShop.entity.Product;
import ks.training.sportsShop.repository.CategoryRepository;
import ks.training.sportsShop.repository.OrderRepository;
import ks.training.sportsShop.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final CategoryRepository categoryRepository;
    public ProductService(ProductRepository productRepository, OrderRepository orderRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Product> findAll() {
        return this.productRepository.findAll();
    }

    public Page<Product> fetchProducts(String name,String category,Pageable page) {
        return this.productRepository.searchProducts(name,category,page);
    }

    public Product createProduct(Product pr) {
        return this.productRepository.save(pr);
    }

    public void deleteProduct(long id) {
        this.productRepository.deleteById(id);
    }

    public Optional<Product> fetchProductById(long id) {
        return this.productRepository.findById(id);
    }

    public boolean existsByProductId(long id) {
        return orderRepository.existsByProductId(id);
    }

    public Category getCategoryById(long id) {
        return categoryRepository.findById(id);
    }
}
