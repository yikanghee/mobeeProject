package movies.repository;

import movies.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findByMovieIdOrderByCreatedAtDesc(Long movies_id);
//findByMovieOrderByCreatedAtDesc
    Comment findByAccountIdAndMovieId(Long account_id, Long movies_id);

    List<Comment> findByMovieId(Long movies_id);

    @Query(value = "delete from comment where account_id=:id", nativeQuery=true)
    void deleteByAccountId(@Param("id") Long id);
}
