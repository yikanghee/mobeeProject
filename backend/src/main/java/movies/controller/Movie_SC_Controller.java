package movies.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = {"현재 영화 정보를 제공하는 Controller"})
public class Movie_SC_Controller {

    private final CrowlingRepository crowlingRepository;

    @ApiOperation("영화 정보를 조회하는 메소드")
    @GetMapping("/api/movies2")
    public List<CrowlingMovie> getAllMovies() {

            List<CrowlingMovie> movies  = crowlingRepository.findAllByOrderById();

        return movies;
    }

    @ApiOperation("상세 영화 정보를 조회하는 메소")
    @GetMapping("/api/movies2/{movie_id}")
    public CrowlingMovie getMovieById(@PathVariable Long movie_id) {
        CrowlingMovie movie = crowlingRepository.findById(movie_id).orElse(null);
        return movie;
    }
}
