package lv.bootcamp.bartersWeb.mappers;

import lv.bootcamp.bartersWeb.dto.ReviewShowDto;
import lv.bootcamp.bartersWeb.entity.EReviewGrade;
import lv.bootcamp.bartersWeb.entity.Review;
import lv.bootcamp.bartersWeb.entity.User;
import lv.bootcamp.bartersWeb.repository.UsersRepository;
import lv.bootcamp.bartersWeb.service.mapper.ReviewMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class ReviewMapperTest {
    @Autowired
    private ReviewMapper reviewMapper;
    @Autowired
    private UsersRepository usersRepository;


    @Test
    public void testReviewToDtoReview() {
        Review review = new Review();
        review.setId(1L);
        review.setReviewerId(1L);
        review.setReviewedId(2L);
        review.setGrade(EReviewGrade.GOOD);
        review.setComment("This is a good review");

        User user1 = new User();
        User user2 = new User();
        user1.setId(1L);
        user2.setId(2L);
        user1.setUsername("a");
        user2.setUsername("b");
        usersRepository.save(user1);
        usersRepository.save(user2);

        ReviewShowDto reviewDto = reviewMapper.reviewToDtoReview(review);

        assertEquals(1L, reviewDto.getId());
        assertEquals("Good", reviewDto.getGrade());
        assertEquals("a", reviewDto.getReviewer());
        assertEquals("b", reviewDto.getReviewed());
        assertEquals("This is a good review", reviewDto.getComment());
    }
    @Test
    public void testReviewToDtoReview_NullInput() {
        ReviewShowDto reviewDto = reviewMapper.reviewToDtoReview(null);

        assertNull(reviewDto);
    }

}
