package Cinema.Movie.dto;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import Cinema.Movie.model.Film;
import Cinema.Movie.model.Customers;

@Embeddable
public class FilmRatingPk implements Serializable {

    @ManyToOne
    private Film film;

    @ManyToOne
    private Customers customer;

    public FilmRatingPk() {}

    public FilmRatingPk(Film film, Customers customer) {
        this.film = film;
        this.customer = customer;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Customers getCustomer() {
        return customer;
    }

    public void setCustomer(Customers customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilmRatingPk that = (FilmRatingPk) o;
        return Objects.equals(film, that.film) && Objects.equals(customer, that.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(film, customer);
    }
}