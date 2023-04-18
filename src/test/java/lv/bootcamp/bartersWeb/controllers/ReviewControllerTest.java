package lv.bootcamp.bartersWeb.controllers;

import lv.bootcamp.bartersWeb.dto.ReviewCreateDto;
import lv.bootcamp.bartersWeb.dto.ReviewShowDto;
import lv.bootcamp.bartersWeb.dto.ReviewUpdateDto;
import lv.bootcamp.bartersWeb.mappers.ReviewMapper;
import lv.bootcamp.bartersWeb.services.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReviewControllerTest {
    private ReviewMapper reviewMapper;
    private ReviewService reviewService;
    private ReviewController reviewController;

    @BeforeEach
    public void setUp() {
        reviewMapper = mock(ReviewMapper.class);
        reviewService = mock(ReviewService.class);
        reviewController = new ReviewController(reviewService,reviewMapper);
    }

    @Test
    void getReviewsReturnsListOfReviews() {
        List<ReviewShowDto> expectedReviews = new ArrayList<>();
        when(reviewService.reviewsAll()).thenReturn(expectedReviews);

        List<ReviewShowDto> actualReviews = reviewController.getReviews();

        assertEquals(expectedReviews, actualReviews);
    }

    @Test
    void getSpecificReviewsReturnsListOfReviews() {
        String username = "user";
        List<ReviewShowDto> expectedReviews = new ArrayList<>();
        when(reviewService.reviewsSpecific(username)).thenReturn(ResponseEntity.ok(expectedReviews));

        ResponseEntity<List<ReviewShowDto>> actualReviews = reviewController.getSpecificReviews(username);

        assertEquals(HttpStatus.OK, actualReviews.getStatusCode());
        assertEquals(expectedReviews, actualReviews.getBody());
    }

    @Test
    void addReviewReturnsCreated() {
        String reviewedUser = "user";
        ReviewCreateDto reviewCreateDto = new ReviewCreateDto();
        ResponseEntity<ReviewCreateDto> expectedResponse = ResponseEntity.status(HttpStatus.CREATED).build();
        when(reviewService.addReview(reviewCreateDto, reviewedUser)).thenReturn(expectedResponse);

        ResponseEntity<ReviewCreateDto> actualResponse = reviewController.addReview(reviewedUser, reviewCreateDto);

        assertEquals(HttpStatus.CREATED, actualResponse.getStatusCode());
    }

    @Test
    void updateReviewReturnsOk() {
        Long reviewId = 1L;
        ReviewUpdateDto reviewUpdateDto = new ReviewUpdateDto();
        ResponseEntity expectedResponse = ResponseEntity.ok().build();
        when(reviewService.updateReview(reviewUpdateDto, reviewId)).thenReturn(expectedResponse);

        ResponseEntity actualResponse = reviewController.updateReview(reviewId, reviewUpdateDto);

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
    }

    @Test
    void deleteByIdReturnsOk() {
        Long reviewId = 1L;
        ResponseEntity expectedResponse = ResponseEntity.ok().build();
        when(reviewService.deleteById(reviewId)).thenReturn(expectedResponse);

        ResponseEntity actualResponse = reviewController.deleteById(reviewId);

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
    }
}