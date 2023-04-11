package lv.bootcamp.bartersWeb.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "reviews")
public class Review {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @Column(name = "reviewer_id")
    private Long reviewerId;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "reviewer_id", referencedColumnName = "userid", insertable=false, updatable=false)
    private User reviewer;

    @Column(name = "reviewed_id")
    private Long reviewedId;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "reviewed_id", referencedColumnName = "userid", insertable=false, updatable=false)
    private User reviewed;

    @Column(name = "grade")
    private EReviewGrade grade;

    @Column(name = "comment")
    private String comment;


    public Review(Long reviewerId, Long reviewedId, EReviewGrade grade, String comment) {
        this.reviewerId = reviewerId;
        this.reviewedId = reviewedId;
        this.grade = grade;
        this.comment = comment;
    }

    public Review() {
    }
}
