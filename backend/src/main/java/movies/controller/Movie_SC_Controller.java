package movies.controller;

import lombok.RequiredArgsConstructor;
import movies.domain.CrowlingMovie;
import movies.domain.Movie;
import movies.repository.CrowlingRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class Movie_SC_Controller {

    private final CrowlingRepository crowlingRepository;

    @GetMapping("/api/movies2")
    public List<CrowlingMovie> getAllMovies() {

            List<CrowlingMovie> movies  = crowlingRepository.findAllByOrderById();

        return movies;
    }

    @GetMapping("/api/movies2/{movie_id}")
    public CrowlingMovie getMovieById(@PathVariable Long movie_id) {
        CrowlingMovie movie = crowlingRepository.findById(movie_id).orElse(null);
        return movie;
    }
}
