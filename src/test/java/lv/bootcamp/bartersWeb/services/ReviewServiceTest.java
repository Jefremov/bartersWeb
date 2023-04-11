package lv.bootcamp.bartersWeb.services;

import lv.bootcamp.bartersWeb.dto.ReviewShowDto;
import lv.bootcamp.bartersWeb.entities.EReviewGrade;
import lv.bootcamp.bartersWeb.entities.Review;
import lv.bootcamp.bartersWeb.entities.User;
import lv.bootcamp.bartersWeb.repositories.ReviewRepository;
import lv.bootcamp.bartersWeb.repositories.UsersRepository;
import lv.bootcamp.bartersWeb.services.ReviewService;
import lv.bootcamp.bartersWeb.mappers.ReviewMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ReviewServiceTest {
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    ReviewService reviewService;
    @Autowired
    ReviewMapper reviewMapper;

    @AfterEach
    void tearDown() {
        reviewRepository.deleteAll();
        usersRepository.deleteAll();
    }

    @Test
    public void testReviewsAll() {
        User user1 = new User();
        User user2 = new User();
        usersRepository.save(user1);
        usersRepository.save(user2);
        Review review1 = new Review();
        Review review2 = new Review();
        reviewRepository.save(review1);
        reviewRepository.save(review2);

        List<ReviewShowDto> reviewDtos = reviewService.reviewsAll();

        assertEquals(2, reviewDtos.size());
        assertTrue(reviewDtos.stream().anyMatch(dto -> dto.getId() == review1.getId()));
        assertTrue(reviewDtos.stream().anyMatch(dto -> dto.getId() == review2.getId()));
    }
//    @Test
//    public void testReviewsSpecific() {
//        User user1 = new User();
//        user1.setUsername("alpha");
//        usersRepository.save(user1);
//        User user2 = new User();
//        user2.setUsername("beta");
//        usersRepository.save(user2);
//
//        Review review1 = new Review();
//        review1.setReviewedId(user1.getId());
//        review1.setReviewerId(user2.getId());
//        reviewRepository.save(review1);
//        Review review2 = new Review();
//        review2.setReviewedId(user1.getId());
//        review2.setReviewerId(user2.getId());
//        reviewRepository.save(review2);
//        Review review3 = new Review();
//        review3.setReviewedId(user2.getId());
//        review3.setReviewerId(user1.getId());
//        reviewRepository.save(review3);
//
//        List<ReviewShowDto> reviewDtos = reviewService.reviewsSpecific("alpha");
//        assertEquals(2, reviewDtos.size());
//
//        reviewDtos = reviewService.reviewsSpecific("beta");
//        assertEquals(1, reviewDtos.size());
//
//        assertTrue(reviewDtos.stream().allMatch(dto -> dto.getReviewed().equals("beta")));
//        assertFalse(reviewDtos.stream().anyMatch(dto -> dto.getReviewed().equals("alpha")));
//    }
    @Test
    public void testAddReview() {
        User user1 = new User();
        user1.setUsername("alpha");
        usersRepository.save(user1);
        User user2 = new User();
        user2.setUsername("beta");
        usersRepository.save(user2);

        reviewService.addReview("beta", user1.getId(), EReviewGrade.GOOD, "Alpha is reviewing beta");

        List<Review> reviews = reviewRepository.findAll();
        assertEquals(1, reviews.size());
        assertEquals("Alpha is reviewing beta", reviews.get(0).getComment());
    }
    @Test
    public void testDeleteById() {
        Review review = new Review();
        reviewRepository.save(review);

        boolean result = reviewService.deleteById(review.getId());

        assertFalse(reviewRepository.existsById(review.getId()));
        assertTrue(result);
    }
    @Test
    public void testGetReviewById() {
        Review review = new Review();
        reviewRepository.save(review);

        ReviewShowDto reviewDto = reviewService.getReviewById(review.getId());

        assertEquals(review.getId(), reviewDto.getId());
    }
    @Test
    public void testUpdateReview() {
        User user1 = new User();
        user1.setUsername("alpha");
        user1.setEmail("alpha@test.com");
        usersRepository.save(user1);
        User user2 = new User();
        usersRepository.save(user2);

        Review review = new Review(user2.getId(), user1.getId(), EReviewGrade.GOOD, "Good review");
        reviewRepository.save(review);

        EReviewGrade newGrade = EReviewGrade.FAIL;
        String newComment = "Fail review";
        boolean success = reviewService.updateReview(review.getId(), newGrade, newComment);

        assertTrue(success);

        Review updatedReview = reviewRepository.findById(review.getId()).orElse(null);

        assertNotNull(updatedReview);
        assertEquals(newGrade, updatedReview.getGrade());
        assertEquals(newComment, updatedReview.getComment());

        ReviewShowDto updatedReviewDto = reviewMapper.reviewToDtoReview(updatedReview);
        assertEquals(newGrade.getDisplayName(), updatedReviewDto.getGrade());
        assertEquals(newComment.toString(), updatedReviewDto.getComment());
    }

}
