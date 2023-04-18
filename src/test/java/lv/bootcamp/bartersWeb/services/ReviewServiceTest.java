package lv.bootcamp.bartersWeb.services;

import lv.bootcamp.bartersWeb.dto.ReviewCreateDto;
import lv.bootcamp.bartersWeb.dto.ReviewShowDto;
import lv.bootcamp.bartersWeb.dto.ReviewUpdateDto;
import lv.bootcamp.bartersWeb.entities.EReviewGrade;
import lv.bootcamp.bartersWeb.entities.Review;
import lv.bootcamp.bartersWeb.entities.User;
import lv.bootcamp.bartersWeb.mappers.ReviewMapper;
import lv.bootcamp.bartersWeb.repositories.ReviewRepository;
import lv.bootcamp.bartersWeb.repositories.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ReviewServiceTest {

    private ReviewRepository reviewRepository;
    private UsersRepository usersRepository;


    private ReviewMapper reviewMapper;

    private ReviewService reviewService;

    @BeforeEach
    public void setUp() {
        reviewRepository = mock(ReviewRepository.class);
        reviewMapper = mock(ReviewMapper.class);
        usersRepository = mock(UsersRepository.class);
        reviewService = new ReviewService(reviewRepository, reviewMapper,usersRepository);
    }
    @Test
    public void shouldReturnAllReviews() {
        List<Review> reviews = new ArrayList<>();
        Review review1 = new Review();
        review1.setId(1L);
        review1.setComment("Review 1");
        reviews.add(review1);
        Review review2 = new Review();
        review2.setId(2L);
        review2.setComment("Review 2");
        reviews.add(review2);

        List<ReviewShowDto> reviewShowDtos = new ArrayList<>();
        ReviewShowDto reviewShowDto1 = new ReviewShowDto();
        reviewShowDto1.setId(1L);
        reviewShowDto1.setComment("Review 1");
        reviewShowDtos.add(reviewShowDto1);
        ReviewShowDto reviewShowDto2 = new ReviewShowDto();
        reviewShowDto2.setId(2L);
        reviewShowDto2.setComment("Review 2");
        reviewShowDtos.add(reviewShowDto2);

        when(reviewRepository.findAll()).thenReturn(reviews);
        when(reviewMapper.reviewToDtoReview(review1)).thenReturn(reviewShowDto1);
        when(reviewMapper.reviewToDtoReview(review2)).thenReturn(reviewShowDto2);

        List<ReviewShowDto> actualReviews = reviewService.reviewsAll();

        assertEquals(reviewShowDtos.size(), actualReviews.size());
        assertEquals(reviewShowDtos.get(0).getId(), actualReviews.get(0).getId());
        assertEquals(reviewShowDtos.get(0).getComment(), actualReviews.get(0).getComment());
        assertEquals(reviewShowDtos.get(1).getId(), actualReviews.get(1).getId());
        assertEquals(reviewShowDtos.get(1).getComment(), actualReviews.get(1).getComment());
    }

    @Test
    public void testReviewsAll_NoReviewsFound() {
        List<Review> reviews = new ArrayList<>();
        when(reviewRepository.findAll()).thenReturn(reviews);

        List<ReviewShowDto> actualResult = reviewService.reviewsAll();

        assertTrue(actualResult.isEmpty());
    }

    @Test
    void testReviewsSpecificWithValidUsername() {
        User user = new User();
        user.setUsername("alpha");
        user.setId(1L);

        Review review1 = new Review(11L, 1L, EReviewGrade.GOOD, "Great transaction!");
        Review review2 = new Review(1L, 1L, EReviewGrade.EXCELLENT, "Smooth trade");
        List<Review> reviews = Arrays.asList(review1, review2);
        when(usersRepository.existsByUsername("alpha")).thenReturn(true);
        when(usersRepository.findUserByUsername("alpha")).thenReturn(user);
        when(reviewRepository.findByReviewedId(1L)).thenReturn(reviews);
        when(reviewMapper.reviewToDtoReview(review1)).thenReturn(new ReviewShowDto());
        when(reviewMapper.reviewToDtoReview(review2)).thenReturn(new ReviewShowDto());

        ResponseEntity<List<ReviewShowDto>> response = reviewService.reviewsSpecific("alpha");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testReviewsSpecificWithInvalidUsername() {
        // Arrange
        String username = "idontexist";
        when(usersRepository.existsByUsername(username)).thenReturn(false);

        ResponseEntity<List<ReviewShowDto>> response = reviewService.reviewsSpecific(username);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }
    @Test
    public void addReview_validUser_reviewCreated() {
        ReviewCreateDto reviewCreateDto = new ReviewCreateDto();
        reviewCreateDto.setComment("Great product");
        reviewCreateDto.setGrade("FAIL");

        String username = "alpha";
        User user = new User();
        user.setId(1L);
        user.setUsername(username);
        reviewCreateDto.setReviewer(username);
        when(usersRepository.existsByUsername(username)).thenReturn(true);
        when(usersRepository.findUserByUsername(username)).thenReturn(user);

        Review review = new Review();
        review.setId(1L);
        when(reviewMapper.CreateDtoToReview(reviewCreateDto, username)).thenReturn(review);

        ResponseEntity<ReviewCreateDto> responseEntity = reviewService.addReview(reviewCreateDto, username);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(reviewCreateDto, responseEntity.getBody());
        verify(reviewRepository, times(1)).save(review);
    }
    @Test
    public void addReview_invalidUser_notFound() {
        User user = new User();
        user.setId(1L);
        user.setUsername("user");

        ReviewCreateDto reviewCreateDto = new ReviewCreateDto();
        reviewCreateDto.setComment("Great product");
        reviewCreateDto.setGrade("FAIL");
        reviewCreateDto.setReviewer("user");

        String username = "alpha";
        when(usersRepository.existsByUsername(username)).thenReturn(false);

        ResponseEntity<ReviewCreateDto> responseEntity = reviewService.addReview(reviewCreateDto, username);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
        verify(reviewRepository, never()).save(any());
    }

    @Test
    public void testDeleteByIdSuccess() {
        Long reviewId = 1L;
        when(reviewRepository.existsById(reviewId)).thenReturn(true);

        ResponseEntity responseEntity = reviewService.deleteById(reviewId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(reviewRepository, times(1)).deleteById(reviewId);
    }

    @Test
    public void testDeleteByIdNotFound() {
        Long reviewId = 1L;
        when(reviewRepository.existsById(reviewId)).thenReturn(false);

        ResponseEntity responseEntity = reviewService.deleteById(reviewId);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(reviewRepository, never()).deleteById(reviewId);
    }
    @Test
    public void testGetReviewById() {
        Long reviewId = 1L;
        Review review = new Review();
        review.setId(reviewId);
        ReviewShowDto reviewShowDto = new ReviewShowDto();
        reviewShowDto.setId(reviewId);
        when(reviewRepository.existsById(reviewId)).thenReturn(true);
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));
        when(reviewMapper.reviewToDtoReview(review)).thenReturn(reviewShowDto);

        ResponseEntity<ReviewShowDto> response = reviewService.getReviewById(reviewId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reviewShowDto, response.getBody());
    }

    @Test
    public void testGetReviewByIdNotFound() {
        Long reviewId = 1L;
        when(reviewRepository.existsById(reviewId)).thenReturn(false);

        ResponseEntity<ReviewShowDto> response = reviewService.getReviewById(reviewId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testUpdateReview() {
        Long reviewId = 1L;
        ReviewUpdateDto reviewUpdateDto = new ReviewUpdateDto();
        Review review = new Review();
        when(reviewRepository.existsById(reviewId)).thenReturn(true);
        when(reviewMapper.UpdateDtoToReview(reviewUpdateDto, reviewId)).thenReturn(review);
        when(reviewRepository.save(review)).thenReturn(review);

        ResponseEntity response = reviewService.updateReview(reviewUpdateDto, reviewId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testUpdateReviewNotFound() {
        Long reviewId = 1L;
        ReviewUpdateDto reviewUpdateDto = new ReviewUpdateDto();
        when(reviewRepository.existsById(reviewId)).thenReturn(false);

        ResponseEntity response = reviewService.updateReview(reviewUpdateDto, reviewId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }
}