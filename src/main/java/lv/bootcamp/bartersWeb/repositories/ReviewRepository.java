package lv.bootcamp.bartersWeb.repositories;

import lv.bootcamp.bartersWeb.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {

    List<Review> findByReviewedId(Long reviewedId);
}
