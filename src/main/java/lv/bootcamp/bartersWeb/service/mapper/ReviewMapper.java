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
        ReviewShowDto reviewShowDto = new ReviewShowDto();
        reviewShowDto.setId(review.getId());
        reviewShowDto.setReviewed(usersRepository.findById(review.getReviewedId()).get().getUsername());
        reviewShowDto.setReviewer(usersRepository.findById(review.getReviewerId()).get().getUsername());
        reviewShowDto.setComment(review.getComment());
        reviewShowDto.setGrade(review.getGrade().getDisplayName());
        return reviewShowDto;
    }

}
