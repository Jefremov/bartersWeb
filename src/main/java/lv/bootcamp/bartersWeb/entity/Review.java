package lv.bootcamp.bartersWeb.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "reviews")
public class Review {

    public enum ReviewGrade {
        EXCELLENT("Excellent"),
        GOOD("Good"),
        AVERAGE("Average"),
        POOR("Poor"),
        FAIL("Fail");

        private final String displayName;

        ReviewGrade(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reviewId")
    private Long id;

    @OneToOne
    @JoinColumn(name = "userId")
    private UserEntity user;

    @OneToOne
    @JoinColumn(name = "reviewerId")
    private UserEntity reviewer;

    @OneToOne
    @JoinColumn(name = "reviewedId")
    private UserEntity reviewed;

    @Column(name = "grade")
    private ReviewGrade grade;

    @Column(name = "comment")
    private String comment;
}
