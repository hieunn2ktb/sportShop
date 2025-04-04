package ks.training.sportsShop.repository;

import ks.training.sportsShop.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category findById(long id);

    @Query("SELECT c FROM Category c " +
            "WHERE (:categoryName IS NULL OR c.name LIKE %:categoryName%)")
    Page<Category> findByCategoryByName(@Param("categoryName") String categoryName,
                                        Pageable pageable);
}
