package movies.repository;

import movies.domain.CrowlingMovie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CrowlingRepository extends JpaRepository<CrowlingMovie, Long> {
    List<CrowlingMovie> findAllByOrderById();
}
