package lv.bootcamp.bartersWeb.services;

import lv.bootcamp.bartersWeb.dto.ReviewCreateDto;
import lv.bootcamp.bartersWeb.dto.ReviewShowDto;
import lv.bootcamp.bartersWeb.dto.ReviewUpdateDto;
import lv.bootcamp.bartersWeb.entities.Review;
import lv.bootcamp.bartersWeb.repositories.ReviewRepository;
import lv.bootcamp.bartersWeb.repositories.UsersRepository;
import lv.bootcamp.bartersWeb.mappers.ReviewMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    private static Logger logger = Logger.getLogger(ReviewService.class);
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
        logger.info("Getting all reviews");
        List<Review> reviews = reviewRepository.findAll();
        logger.info("Retrieved " + reviews.size() + " reviews");
        return reviews
                .stream()
                .map(reviewMapper::reviewToDtoReview)
                .collect(Collectors.toList());
    }

    public ResponseEntity<List<ReviewShowDto>> reviewsSpecific(String username){
        if(usersRepository.existsByUsername(username)) {
            return ResponseEntity.ok(reviewRepository.findByReviewedId(usersRepository.findUserByUsername(username).getId())
                    .stream()
                    .map(reviewMapper::reviewToDtoReview)
                    .collect(Collectors.toList()));
        }
        logger.info("No user found with username " + username);
        return  ResponseEntity.notFound().build();
    }

    public ResponseEntity<ReviewCreateDto> addReview(ReviewCreateDto reviewCreateDto, String username) {
        if(usersRepository.existsByUsername(username)){
            Review review= reviewMapper.CreateDtoToReview(reviewCreateDto,username);
            reviewRepository.save(review);
            logger.info("Review added for user " + username);
            return ResponseEntity.ok(reviewCreateDto);
        }
        logger.info("No user found with username " + username);
        return  ResponseEntity.notFound().build();
    }

    public ResponseEntity deleteById(Long reviewId) {
        if(reviewRepository.existsById(reviewId)){
            reviewRepository.deleteById(reviewId);
            logger.info("Review deleted with ID: " + reviewId);
            return ResponseEntity.ok().build();
        }
        logger.info("No review found with ID: " + reviewId);
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<ReviewShowDto> getReviewById(Long reviewId) {
        if(reviewRepository.existsById(reviewId)) {
            Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new RuntimeException("No review match"));
            logger.info("Retrieved review with ID: " + reviewId);
            return ResponseEntity.ok(reviewMapper.reviewToDtoReview(review));
        }
        logger.info("No review found with ID: " + reviewId);
        return  ResponseEntity.notFound().build();
    }

    public ResponseEntity updateReview(ReviewUpdateDto reviewUpdateDto, Long reviewId) {
        if(reviewRepository.existsById(reviewId)) {
            Review review = reviewMapper.UpdateDtoToReview(reviewUpdateDto,reviewId);
            logger.info("Review updated with ID: " + reviewId);
            reviewRepository.save(review);
           return ResponseEntity.ok().build();
        }
        logger.info("No review found with ID: " + reviewId);
        return  ResponseEntity.notFound().build();
    }

    public Double getAverageRatingForUser(String username) {
        if(usersRepository.existsByUsername(username)) {
            List<Review> reviews = reviewRepository.findByReviewedId(usersRepository.findUserByUsername(username).getId());
            Double averageRating = reviews.stream()
                    .mapToDouble(review -> review.getGrade().getValue())
                    .average()
                    .orElse(0.0);
            return Math.round(averageRating * 100.0) / 100.0;
        }
        else return 0.0;
    }


}
