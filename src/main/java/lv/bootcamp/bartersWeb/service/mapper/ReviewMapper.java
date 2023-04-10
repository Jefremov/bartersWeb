package lv.bootcamp.bartersWeb.service.mapper;

import lv.bootcamp.bartersWeb.dto.ReviewShowDto;
import lv.bootcamp.bartersWeb.entity.Review;
import lv.bootcamp.bartersWeb.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    @Autowired
    private UsersRepository usersRepository;

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

}
