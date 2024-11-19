package com.example.springreviewhub.infrastructure.database.seeders;

import com.example.springreviewhub.infrastructure.database.jpa.MovieJpaRepository;
import com.example.springreviewhub.infrastructure.database.entity.Movie;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

/**
 * A seeder class for preloading movie data into the database.
 * <p>
 * This class implements the {@link CommandLineRunner} interface, which allows it to run specific
 * logic after the application context is fully initialized. The seeder checks if the database is
 * empty, and if so, it inserts a predefined list of movies.
 * </p>
 * <p>
 * The movie data includes details such as title, description, release date, duration, genre, director,
 * and rating. This is useful for setting up initial data for testing or development environments.
 * </p>
 */
@Component
public class MovieSeeder implements CommandLineRunner {

    private final MovieJpaRepository movieJpaRepository;

    /**
     * Constructs a new instance of {@code MovieSeeder} with the given {@code MovieJpaRepository}.
     *
     * @param movieRepository the repository used for saving movie data to the database
     */
    public MovieSeeder(MovieJpaRepository movieRepository) {
        this.movieJpaRepository = movieRepository;
    }

    /**
     * Runs the seeder logic to populate the database with predefined movie data.
     * <p>
     * If the database is empty, this method saves a list of movies into the database.
     * The data includes a diverse set of movies with attributes like title, genre,
     * release date, and ratings.
     * </p>
     *
     * @param args command-line arguments (not used in this implementation)
     * @throws Exception if an error occurs during execution
     */
    @Override
    public void run(String... args) throws Exception {
        if (movieJpaRepository.count() == 0) {
            Movie movie1 = new Movie()
                    .setTitle("The Dark Knight")
                    .setDescription("Batman faces his greatest challenge yet, the Joker.")
                    .setReleaseDate(LocalDate.of(2008, 7, 18))
                    .setDuration(152)
                    .setGenre("Action, Drama, Thriller")
                    .setDirector("Christopher Nolan")
                    .setRating(new BigDecimal("9.0"));

            Movie movie2 = new Movie()
                    .setTitle("Inception")
                    .setDescription("A skilled thief is given a chance to have his criminal record erased if he can successfully perform an inception.")
                    .setReleaseDate(LocalDate.of(2010, 7, 16))
                    .setDuration(148)
                    .setGenre("Action, Sci-Fi, Thriller")
                    .setDirector("Christopher Nolan")
                    .setRating(new BigDecimal("8.8"));

            Movie movie3 = new Movie()
                    .setTitle("The Matrix")
                    .setDescription("A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.")
                    .setReleaseDate(LocalDate.of(1999, 3, 31))
                    .setDuration(136)
                    .setGenre("Action, Sci-Fi")
                    .setDirector("The Wachowskis")
                    .setRating(new BigDecimal("8.7"));

            Movie movie4 = new Movie()
                    .setTitle("Interstellar")
                    .setDescription("A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.")
                    .setReleaseDate(LocalDate.of(2014, 11, 7))
                    .setDuration(169)
                    .setGenre("Adventure, Drama, Sci-Fi")
                    .setDirector("Christopher Nolan")
                    .setRating(new BigDecimal("8.6"));

            Movie movie5 = new Movie()
                    .setTitle("Pulp Fiction")
                    .setDescription("The lives of two mob hitmen, a boxer, a gangster, and his wife intertwine in a series of incidents.")
                    .setReleaseDate(LocalDate.of(1994, 10, 14))
                    .setDuration(154)
                    .setGenre("Crime, Drama")
                    .setDirector("Quentin Tarantino")
                    .setRating(new BigDecimal("8.9"));

            Movie movie6 = new Movie()
                    .setTitle("The Lord of the Rings: The Fellowship of the Ring")
                    .setDescription("A meek Hobbit and his friends set out to destroy the One Ring and save Middle-earth.")
                    .setReleaseDate(LocalDate.of(2001, 12, 19))
                    .setDuration(178)
                    .setGenre("Adventure, Drama, Fantasy")
                    .setDirector("Peter Jackson")
                    .setRating(new BigDecimal("8.8"));

            Movie movie7 = new Movie()
                    .setTitle("Fight Club")
                    .setDescription("An insomniac office worker and a devil-may-care soap maker form an underground fight club.")
                    .setReleaseDate(LocalDate.of(1999, 10, 15))
                    .setDuration(139)
                    .setGenre("Drama")
                    .setDirector("David Fincher")
                    .setRating(new BigDecimal("8.8"));

            Movie movie8 = new Movie()
                    .setTitle("Forrest Gump")
                    .setDescription("The life story of Forrest Gump, a man with a low IQ but a big heart.")
                    .setReleaseDate(LocalDate.of(1994, 7, 6))
                    .setDuration(142)
                    .setGenre("Drama, Romance")
                    .setDirector("Robert Zemeckis")
                    .setRating(new BigDecimal("8.8"));

            Movie movie9 = new Movie()
                    .setTitle("The Shawshank Redemption")
                    .setDescription("Two imprisoned men bond over several years, finding solace and redemption through acts of decency.")
                    .setReleaseDate(LocalDate.of(1994, 9, 22))
                    .setDuration(142)
                    .setGenre("Drama")
                    .setDirector("Frank Darabont")
                    .setRating(new BigDecimal("9.3"));

            Movie movie10 = new Movie()
                    .setTitle("The Godfather")
                    .setDescription("The aging patriarch of an organized crime dynasty transfers control to his reluctant son.")
                    .setReleaseDate(LocalDate.of(1972, 3, 24))
                    .setDuration(175)
                    .setGenre("Crime, Drama")
                    .setDirector("Francis Ford Coppola")
                    .setRating(new BigDecimal("9.2"));

            movieJpaRepository.saveAll(Arrays.asList(movie1, movie2, movie3, movie4, movie5, movie6, movie7, movie8, movie9, movie10));

            System.out.println("[*] Data movie berhasil di-seed");
        }
    }
}
