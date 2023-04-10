package lv.bootcamp.bartersWeb.controllers;

import lv.bootcamp.bartersWeb.dto.ReviewShowDto;
import lv.bootcamp.bartersWeb.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReviewControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private ReviewService reviewService;

    @Test
    public void getReviewById_NonExistingId_ReturnsNotFound() {

        ResponseEntity<Void> response = restTemplate.getForEntity("/reviews/99999", Void.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    @Test
    public void getReviews_ReturnsAllReviews() {
        List<ReviewShowDto> expectedReviews = reviewService.reviewsAll();

        ResponseEntity<List<ReviewShowDto>> response = restTemplate.exchange(
                "/reviews",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ReviewShowDto>>() {});

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedReviews.size(), response.getBody().size());
    }



}

