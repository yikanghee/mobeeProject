package movies.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import movies.config.UserDetailsImpl;
import movies.domain.Heart;
import movies.domain.Message;
import movies.service.HeartSerice;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@Api(tags = {"영화 좋아요를 담당하는 Controller"})
public class HeartController {

    private final HeartSerice heartService;

    @ApiOperation("좋아요 정보를 조회하는 메소드")
    @GetMapping("/api/movies/{movie_id}/heart")
    public HashMap<String, Object> ReadHeart(@PathVariable Long movie_id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return heartService.ReadHeart(movie_id, userDetails.getAccount().getId());
    }


    @ApiOperation("좋아요를 추가하는 담당하는 메소드")
    @PostMapping("/api/movies/{movie_id}/heart")
    public ResponseEntity CreateHeart(@PathVariable Long movie_id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Heart heart = heartService.CreateHeart(movie_id, userDetails.getAccount().getId());
        if (heart == null){
            Message message = Message.builder()
                    .message1("이미 좋아요 상태입니다.")
                    .build();
            return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok().build();
    }

    @ApiOperation("좋아요를 삭제하는 메소드")
    @DeleteMapping("/api/movies/{movie_id}/heart")
    public ResponseEntity DeleteHeart(@PathVariable Long movie_id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Heart heart = heartService.DeleteHeart(movie_id, userDetails.getAccount().getId());
        if (heart == null){
            Message message = Message.builder()
                    .message1("취소할 좋아요가 없습니다.")
                    .build();
            return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok().build();
    }

}
