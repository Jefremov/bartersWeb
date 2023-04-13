package lv.bootcamp.bartersWeb.controllers;

import jakarta.validation.Valid;
import lv.bootcamp.bartersWeb.dto.ReviewCreateDto;
import lv.bootcamp.bartersWeb.dto.ReviewShowDto;
import lv.bootcamp.bartersWeb.dto.ReviewUpdateDto;
import lv.bootcamp.bartersWeb.entities.EReviewGrade;
import lv.bootcamp.bartersWeb.services.ReviewService;
import lv.bootcamp.bartersWeb.mappers.ReviewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private ReviewService reviewService;
    private ReviewMapper reviewMapper;
    @Autowired
    public ReviewController(ReviewService reviewService,ReviewMapper reviewMapper) {
        this.reviewService = reviewService;
        this.reviewMapper=reviewMapper;
    }

    @GetMapping
    public List<ReviewShowDto> getReviews(){
        return reviewService.reviewsAll();
    }

    @GetMapping(value = "/of/{username}")
    public ResponseEntity<List<ReviewShowDto>> getSpecificReviews(@PathVariable("username") String username){
        return reviewService.reviewsSpecific(username);
    }

    @GetMapping(value = "/{reviewid}")
    public ResponseEntity<ReviewShowDto> getReviewById(@PathVariable("reviewid") Long reviewId){
        return  reviewService.getReviewById(reviewId);
    }

    @PostMapping(value ="/of/{username}" )
    public ResponseEntity<ReviewCreateDto> addReview(@PathVariable("username") String reviewed,
                                       @Valid @RequestBody ReviewCreateDto reviewCreateDto){
        return reviewService.addReview(reviewCreateDto,reviewed);
    }

    @PutMapping(value = "/{reviewid}")
    public ResponseEntity updateReview(@PathVariable("reviewid") Long reviewId,
                                             @RequestBody @Valid ReviewUpdateDto reviewUpdateDto){
        return reviewService.updateReview(reviewUpdateDto,reviewId);
    }

    @DeleteMapping(value = "/{reviewid}")
    public ResponseEntity deleteById(@PathVariable("reviewid") Long reviewId){
        return reviewService.deleteById(reviewId);
    }

}
