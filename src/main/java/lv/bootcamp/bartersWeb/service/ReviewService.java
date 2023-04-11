package lv.bootcamp.bartersWeb.service;

import lv.bootcamp.bartersWeb.dto.ReviewShowDto;
import lv.bootcamp.bartersWeb.entity.EReviewGrade;
import lv.bootcamp.bartersWeb.entity.Review;
import lv.bootcamp.bartersWeb.repository.ReviewRepository;
import lv.bootcamp.bartersWeb.repository.UsersRepository;
import lv.bootcamp.bartersWeb.service.mapper.ReviewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    private ReviewRepository reviewRepository;
    private ReviewMapper reviewMapper;
    private UsersRepository usersRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, ReviewMapper reviewMapper, UsersRepository usersRepository){
        this.reviewRepository=reviewRepository;
        this.reviewMapper=reviewMapper;
        this.usersRepository=usersRepository;
    }

    public List<ReviewShowDto> reviewsAll(){
        return reviewRepository.findAll()
                .stream()
                .map(reviewMapper::reviewToDtoReview)
                .collect(Collectors.toList());
    }

    public ResponseEntity<List<ReviewShowDto>> reviewsSpecific(String username){
        if(usersRepository.existsByUsername(username)) {
            return ResponseEntity.ok(reviewRepository.findByReviewedId(usersRepository.findByUsername(username).getId())
                    .stream()
                    .map(reviewMapper::reviewToDtoReview)
                    .collect(Collectors.toList()));
        }
        else return ResponseEntity.notFound().build();
    }

    public void addReview(String reviewed, Long reviewerId, EReviewGrade grade, String comment) {
        if(usersRepository.existsByUsername(reviewed)){
            Long reviewedId=usersRepository.findByUsername(reviewed).getId();
            Review review= new Review(reviewerId,reviewedId,grade,comment);
            reviewRepository.save(review);
        }
    }

    public boolean deleteById(Long reviewId) {
        if(reviewRepository.existsById(reviewId)){
            reviewRepository.deleteById(reviewId);
            return true;
        }
        else return false;
    }

    public ReviewShowDto getReviewById(Long reviewId) {
        if(reviewRepository.existsById(reviewId)) {
            Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new RuntimeException("No review match"));
            return reviewMapper.reviewToDtoReview(review);
        }
        else return null;
    }

    public boolean updateReview(Long reviewId, EReviewGrade grade, String comment) {
        if(reviewRepository.existsById(reviewId)) {
            Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new RuntimeException("No review match"));
            review.setGrade(grade);
            review.setComment(comment);
            Review updatedReview = reviewRepository.save(review);
            return true;
        }
        else return false;
    }

}
