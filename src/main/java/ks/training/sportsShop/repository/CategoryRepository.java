package ks.training.sportsShop.repository;

import ks.training.sportsShop.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category findById(long id);
}
