package movies.service.Impl;

import movies.domain.Heart;
import movies.domain.Movie;
import movies.repository.AccountRepository;
import movies.repository.HeartRepository;
import movies.domain.Account;
import movies.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import movies.service.HeartSerice;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HeartServiceImpl implements HeartSerice {

    private final HeartRepository heartRepository;
    private final MovieRepository movieRepository;
    private final AccountRepository accountRepository;

    public HashMap<String, Object> ReadHeart(Long movie_id, Long account_id) {
        Heart heart = heartRepository.findByMovieIdAndAccountId(movie_id, account_id);

        List<Heart> heartCount = heartRepository.findByMovieId(movie_id);

        HashMap<String, Object> map = new HashMap<>();
        Integer Count = heartCount.size();
        if (heart == null){
            map.put("check", false);
            map.put("heartCount", Count);
            return map;
        }
        map.put("check", true);
        map.put("heartCount", Count);
        return map;
    }

    @Transactional
    public Heart CreateHeart(Long movie_id, Long account_id){
        Movie movie = movieRepository.findById(movie_id).orElseThrow(
                () -> new IllegalArgumentException("책이 존재하지 않습니다.")
        );
        Account account = accountRepository.findById(account_id).orElseThrow(
                () -> new IllegalArgumentException("계정이 존재하지 않습니다.")
        );
        Heart heart = heartRepository.findByMovieIdAndAccountId(movie_id, account_id);
        if (heart == null) {
            Heart newHeart = new Heart();
            newHeart.setHeart(true);
            movie.addHeart(newHeart);
            account.addHeart(newHeart);
            heartRepository.save(newHeart);
            return newHeart;
        }
        return null;
    }

    public Heart DeleteHeart(Long movie_id, Long account_id){
        Movie movie = movieRepository.findById(movie_id).orElseThrow(
                () -> new IllegalArgumentException("책이 존재하지 않습니다.")
        );
        Account account = accountRepository.findById(account_id).orElseThrow(
                () -> new IllegalArgumentException("계정이 존재하지 않습니다.")
        );
        Heart heart = heartRepository.findByMovieIdAndAccountId(movie_id, account_id);
        if (heart != null){
            movie.deleteHeart(heart);
            account.deleteHeart(heart);
            heartRepository.deleteById(heart.getId());
            return heart;
        }
        return null;
    }

}
