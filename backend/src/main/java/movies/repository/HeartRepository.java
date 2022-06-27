package movies.repository;

import movies.domain.Heart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface HeartRepository extends JpaRepository<Heart, Long> {

    Heart findByMovieIdAndAccountId(Long movie_id, Long account_id);

    List<Heart> findByMovieId(Long movie_id);

    @Transactional
    @Query(value = "delete from heart where account_id=:id", nativeQuery=true)
    void deleteByAccountId(Long id);
}
