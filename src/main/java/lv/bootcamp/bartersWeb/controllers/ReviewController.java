package lv.bootcamp.bartersWeb.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lv.bootcamp.bartersWeb.dto.ReviewCreateDto;
import lv.bootcamp.bartersWeb.dto.ReviewShowDto;
import lv.bootcamp.bartersWeb.dto.ReviewUpdateDto;
import lv.bootcamp.bartersWeb.services.ReviewService;
import lv.bootcamp.bartersWeb.mappers.ReviewMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private static Logger logger = Logger.getLogger(ReviewController.class);
    private ReviewService reviewService;
    private ReviewMapper reviewMapper;

    @Autowired
    public ReviewController(ReviewService reviewService,ReviewMapper reviewMapper) {
        this.reviewService = reviewService;
        this.reviewMapper=reviewMapper;
    }

    @GetMapping
    public List<ReviewShowDto> getReviews(){
        logger.info("Fetching all reviews.");
        return reviewService.reviewsAll();
    }

    @GetMapping(value = "/of/{username}")
    public ResponseEntity<List<ReviewShowDto>> getSpecificReviews(@PathVariable("username") String username){
        logger.info("Fetching reviews of user:" + username);
        return reviewService.reviewsSpecific(username);
    }

    @GetMapping(value = "/{reviewid}")
    public ResponseEntity<ReviewShowDto> getReviewById(@PathVariable("reviewid") Long reviewId){
        logger.info("Fetching review with ID: " + reviewId);
        return  reviewService.getReviewById(reviewId);
    }

    @PostMapping(value ="/of/{username}" )
    public ResponseEntity<ReviewCreateDto> addReview(@PathVariable("username") String reviewed,
                                       @Valid @ModelAttribute ReviewCreateDto reviewCreateDto){
        logger.info("Adding review for user: " +reviewed);
        return reviewService.addReview(reviewCreateDto,reviewed);
    }

    @PutMapping(value = "/{reviewid}")
    public ResponseEntity updateReview(@PathVariable("reviewid") Long reviewId,
                                             @ModelAttribute @Valid ReviewUpdateDto reviewUpdateDto){
        logger.info("Updating review with ID:" + reviewId);
        return reviewService.updateReview(reviewUpdateDto,reviewId);
    }

    @DeleteMapping(value = "/{reviewid}")
    @Operation(hidden = true)
    public ResponseEntity deleteById(@PathVariable("reviewid") Long reviewId){
        logger.info("Deleting review with ID: " + reviewId);
        return reviewService.deleteById(reviewId);
    }

    @GetMapping("/{username}/average")
    public ResponseEntity<Double> getAverageRatingByUsername(@PathVariable String username) {
        Double averageRating = reviewService.getAverageRatingForUser(username);
        return ResponseEntity.ok(averageRating);
    }

}
