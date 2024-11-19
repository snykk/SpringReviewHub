package com.example.springreviewhub.adapter.repository;

import com.example.springreviewhub.core.domain.MovieDomain;
import com.example.springreviewhub.core.interfaces.repositories.IMovieRepository;
import com.example.springreviewhub.infrastructure.database.entity.Movie;
import com.example.springreviewhub.infrastructure.database.entity.mapper.MovieMapper;
import com.example.springreviewhub.infrastructure.database.jpa.MovieJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class MovieRepositoryImpl implements IMovieRepository {

    private final MovieJpaRepository movieJpaRepository;

    @Autowired
    public MovieRepositoryImpl(MovieJpaRepository movieJpaRepository) {
        this.movieJpaRepository = movieJpaRepository;
    }

    @Override
    public List<MovieDomain> getAllMovies() {
        List<Movie> movieEntities = movieJpaRepository.findAll();

        return MovieMapper.fromEntityListToDomList(movieEntities);
    }

    @Override
    public Optional<MovieDomain> getMovieById(Long id) {
        Optional<Movie> movieEntity = movieJpaRepository.findById(id);

        return movieEntity.map(MovieMapper::fromEntityToDomain);
    }

    @Override
    public MovieDomain createMovie(MovieDomain movieDomain) {
        Movie movie = MovieMapper.fromDomainToEntity(movieDomain);

        Movie savedMovie = movieJpaRepository.save(movie);

        return MovieMapper.fromEntityToDomain(savedMovie);
    }

    @Override
    public MovieDomain updateMovie(Long id, MovieDomain movieDomain) {
        Optional<Movie> existingMovie = movieJpaRepository.findById(id);

        if (existingMovie.isPresent()) {
            Movie movie = existingMovie.get()
                    .setTitle(movieDomain.getTitle())
                    .setDescription(movieDomain.getDescription())
                    .setReleaseDate(movieDomain.getReleaseDate())
                    .setDuration(movieDomain.getDuration())
                    .setGenre(movieDomain.getGenre())
                    .setDirector(movieDomain.getDirector())
                    .setRating(movieDomain.getRating());

            Movie updatedMovie = movieJpaRepository.save(movie);

            return MovieMapper.fromEntityToDomain(updatedMovie);
        } else {
            throw new RuntimeException("Movie not found with id: " + id);
        }
    }

    @Override
    public void deleteMovie(Long id) {
        movieJpaRepository.deleteById(id);
    }

    @Override
    public List<MovieDomain> searchMovies(
            String title,
            String genre,
            BigDecimal minRating,
            LocalDate startDate,
            LocalDate endDate
    ) {
        List<Movie> movieEntities = movieJpaRepository.advancedSearch(title, genre, minRating, startDate, endDate);

        return MovieMapper.fromEntityListToDomList(movieEntities);
    }
}
