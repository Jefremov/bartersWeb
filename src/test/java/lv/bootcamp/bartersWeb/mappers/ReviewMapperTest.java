package lv.bootcamp.bartersWeb.mappers;

import lv.bootcamp.bartersWeb.dto.ReviewCreateDto;
import lv.bootcamp.bartersWeb.dto.ReviewShowDto;
import lv.bootcamp.bartersWeb.dto.ReviewUpdateDto;
import lv.bootcamp.bartersWeb.entities.EReviewGrade;
import lv.bootcamp.bartersWeb.entities.Review;
import lv.bootcamp.bartersWeb.entities.User;
import lv.bootcamp.bartersWeb.repositories.ReviewRepository;
import lv.bootcamp.bartersWeb.repositories.UsersRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ReviewMapperTest {
    @Mock
    private UsersRepository usersRepository;
    @Mock
    private ReviewRepository reviewRepository;
    @InjectMocks
    private ReviewMapper reviewMapper;


    @AfterEach
    public void tearDown() {
        reset(usersRepository);
    }
    @Test
    public void testReviewToDtoReview() {
        User user1 = new User();
        User user2 = new User();
        user1.setId(1L);
        user2.setId(2L);
        user1.setUsername("a");
        user2.setUsername("b");
        Review review = new Review();
        review.setId(1L);
        review.setReviewerId(1L);
        review.setReviewedId(2L);
        review.setGrade(EReviewGrade.GOOD);
        review.setComment("This is a good review");

        when(usersRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(usersRepository.findById(2L)).thenReturn(Optional.of(user2));

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

    @Test
    public void testCreateDtoToReview() {

        User user1 = new User();
        user1.setId(1L);
        when(usersRepository.findUserByUsername("user1")).thenReturn(user1);
        User user2 = new User();
        user2.setId(2L);
        when(usersRepository.findUserByUsername("user2")).thenReturn(user2);

        ReviewCreateDto reviewCreateDto = new ReviewCreateDto();
        reviewCreateDto.setReviewer("user2");
        reviewCreateDto.setGrade("GOOD");
        reviewCreateDto.setComment("Test comment");

        Review review = reviewMapper.CreateDtoToReview(reviewCreateDto, "user1");

        assertEquals(1L, review.getReviewedId());
        assertEquals(2L, review.getReviewerId());
        assertEquals(EReviewGrade.GOOD, review.getGrade());
        assertEquals("Test comment", review.getComment());
    }

    @Test
    public void testUpdateDtoToReview() {

        Review testReview = new Review();
        testReview.setId(1L);
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(testReview));


        ReviewUpdateDto reviewUpdateDto = new ReviewUpdateDto();
        reviewUpdateDto.setGrade("FAIL");
        reviewUpdateDto.setComment("Updated comment");

        Review review = reviewMapper.UpdateDtoToReview(reviewUpdateDto, 1L);

        assertEquals(1L, review.getId());
        assertEquals(EReviewGrade.FAIL, review.getGrade());
        assertEquals("Updated comment", review.getComment());
    }
}