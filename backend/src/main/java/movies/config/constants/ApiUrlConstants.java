package movies.config.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiUrlConstants {
    public static final String MOVIE_URL = "https://movie.naver.com/movie/sdb/rank/rmovie.naver";
    public static final String URL = "https://movie.naver.com/movie/bi/mi/basic.naver?code=";

    public static final String TRANCELATEURL = "https://openapi.naver.com/v1/papago/n2mt";

}
