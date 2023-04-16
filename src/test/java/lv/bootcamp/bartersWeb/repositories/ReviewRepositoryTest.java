package lv.bootcamp.bartersWeb.repositories;

import lv.bootcamp.bartersWeb.entities.Review;
import lv.bootcamp.bartersWeb.entities.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewRepositoryTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Test
    @DisplayName("Test find by reviewed id")
    public void testFindByReviewedId() {
        Long reviewedId = 1L;
        User user = new User();
        user.setId(1L);
        Review review = new Review();
        review.setId(1L);
        review.setReviewedId(reviewedId);
        List<Review> reviews = new ArrayList<>();
        reviews.add(review);

        when(reviewRepository.findByReviewedId(reviewedId)).thenReturn(reviews);

        List<Review> result = reviewRepository.findByReviewedId(reviewedId);

        assertEquals(1, result.size());
        assertEquals(review, result.get(0));
    }
}