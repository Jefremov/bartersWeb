package lv.bootcamp.bartersWeb.services;

import lv.bootcamp.bartersWeb.dto.ReviewCreateDto;
import lv.bootcamp.bartersWeb.dto.ReviewShowDto;
import lv.bootcamp.bartersWeb.dto.ReviewUpdateDto;
import lv.bootcamp.bartersWeb.entities.EReviewGrade;
import lv.bootcamp.bartersWeb.entities.Review;
import lv.bootcamp.bartersWeb.repositories.ReviewRepository;
import lv.bootcamp.bartersWeb.repositories.UsersRepository;
import lv.bootcamp.bartersWeb.mappers.ReviewMapper;
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
            return ResponseEntity.ok(reviewRepository.findByReviewedId(usersRepository.findUserByUsername(username).getId())
                    .stream()
                    .map(reviewMapper::reviewToDtoReview)
                    .collect(Collectors.toList()));
        }
        else return  ResponseEntity.notFound().build();
    }

    public ResponseEntity<ReviewCreateDto> addReview(ReviewCreateDto reviewCreateDto, String username) {
        if(usersRepository.existsByUsername(username)){
            Review review= reviewMapper.CreateDtoToReview(reviewCreateDto,username);
            reviewRepository.save(review);
            return ResponseEntity.ok(reviewCreateDto);
        }
            else return  ResponseEntity.notFound().build();
    }

    public ResponseEntity deleteById(Long reviewId) {
        if(reviewRepository.existsById(reviewId)){
            reviewRepository.deleteById(reviewId);
            return ResponseEntity.ok().build();
        }
        else return ResponseEntity.notFound().build();
    }

    public ResponseEntity<ReviewShowDto> getReviewById(Long reviewId) {
        if(reviewRepository.existsById(reviewId)) {
            Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new RuntimeException("No review match"));
            return ResponseEntity.ok(reviewMapper.reviewToDtoReview(review));
        }
        else return  ResponseEntity.notFound().build();
    }

    public ResponseEntity updateReview(ReviewUpdateDto reviewUpdateDto, Long reviewId) {
        if(reviewRepository.existsById(reviewId)) {
            Review review = reviewMapper.UpdateDtoToReview(reviewUpdateDto,reviewId);
            reviewRepository.save(review);
           return ResponseEntity.ok().build();
        }
        else return  ResponseEntity.notFound().build();
    }


}
