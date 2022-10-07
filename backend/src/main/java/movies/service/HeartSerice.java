package movies.service;

import movies.domain.Heart;

import java.util.HashMap;

public interface HeartSerice {

    HashMap<String, Object> ReadHeart(Long movie_id, Long account_id);

    Heart CreateHeart(Long movie_id, Long account_id);

    Heart DeleteHeart(Long movie_id, Long account_id);

}
