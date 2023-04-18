package lv.bootcamp.bartersWeb.mappers;

import lv.bootcamp.bartersWeb.dto.ReviewCreateDto;
import lv.bootcamp.bartersWeb.dto.ReviewShowDto;
import lv.bootcamp.bartersWeb.dto.ReviewUpdateDto;
import lv.bootcamp.bartersWeb.entities.EReviewGrade;
import lv.bootcamp.bartersWeb.entities.Review;
import lv.bootcamp.bartersWeb.repositories.ReviewRepository;
import lv.bootcamp.bartersWeb.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    public ReviewShowDto reviewToDtoReview(Review review){
        if (review == null) {return null;}
        ReviewShowDto reviewShowDto = new ReviewShowDto();
        reviewShowDto.setId(review.getId());
        if(review.getReviewedId()!=null) {reviewShowDto.setReviewed(usersRepository.findById(review.getReviewedId()).get().getUsername()); }
        if(review.getReviewerId()!=null) {reviewShowDto.setReviewer (usersRepository.findById(review.getReviewerId()).get().getUsername()); }
        if (review.getComment()!=null) {reviewShowDto.setComment(review.getComment()); }
        if (review.getGrade()!=null) {reviewShowDto.setGrade(review.getGrade().getDisplayName()); }
        return reviewShowDto;
    }

    public Review CreateDtoToReview(ReviewCreateDto reviewCreateDto, String username){
        Review review = new Review();
            review.setReviewedId(usersRepository.findUserByUsername(username).getId());
            review.setReviewerId(usersRepository.findUserByUsername(reviewCreateDto.getReviewer()).getId());
            review.setGrade(EReviewGrade.valueOf(reviewCreateDto.getGrade()));
            review.setComment(reviewCreateDto.getComment());
        return review;
    }

    public Review UpdateDtoToReview(ReviewUpdateDto reviewUpdateDto, Long reviewId){
        Review review = reviewRepository.findById(reviewId).orElseThrow(()->new RuntimeException("No review match"));
        review.setGrade(EReviewGrade.valueOf(reviewUpdateDto.getGrade()));
        review.setComment(reviewUpdateDto.getComment());
        return review;
    }
}