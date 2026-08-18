package Cinema.Movie.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "film_rating")
public class FilmRating {

    @EmbeddedId
    private FilmRatingPk pk;

    @Column(nullable = false)
    private Integer score; // Rating score (e.g., 1 to 5)

    @Column(length = 255)
    private String comment;

    public FilmRating() {}

    public FilmRating(FilmRatingPk pk, Integer score, String comment) {
        this.pk = pk;
        this.score = score;
        this.comment = comment;
    }

    public FilmRatingPk getPk() {
        return pk;
    }

    public void setPk(FilmRatingPk pk) {
        this.pk = pk;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}