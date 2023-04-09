package lv.bootcamp.bartersWeb.controller;

import lv.bootcamp.bartersWeb.dto.ReviewShowDto;
import lv.bootcamp.bartersWeb.entity.EReviewGrade;
import lv.bootcamp.bartersWeb.service.ReviewService;
import lv.bootcamp.bartersWeb.service.mapper.ReviewMapper;
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
    public List<ReviewShowDto> getSpecificReviews(@PathVariable("username") String username){

        return reviewService.reviewsSpecific(username);
    }
    @GetMapping(value = "/{reviewid}")
    public ResponseEntity<ReviewShowDto> getReviewById(@PathVariable("reviewid") Long reviewId){
        ReviewShowDto review = reviewService.getReviewById(reviewId);
        if (review == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(review);
        }
    }
    @PostMapping
    public void addReview(@PathVariable("username") String reviewed,
                          @RequestParam("reviewerId") Long reviewerId,
                          @RequestParam("grade") String g,
                          @RequestParam("comment") String comment){
        EReviewGrade grade = EReviewGrade.valueOf(g);
        reviewService.addReview(reviewed,reviewerId,grade, comment);
    }
    @PutMapping(value = "/{reviewid}")
    public ResponseEntity<Void> updateReview(@PathVariable("reviewid") Long reviewId,
                             @RequestParam("grade") String g,
                             @RequestParam("comment") String comment){
        EReviewGrade grade = EReviewGrade.valueOf(g);
        boolean success = reviewService.updateReview(reviewId,grade, comment);
        if (success) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteById(@RequestParam("reviewid") Long reviewId){
        boolean success = reviewService.deleteById(reviewId);
        if (success) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
