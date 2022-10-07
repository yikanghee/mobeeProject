package movies.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import movies.service.CrowlingService;
import movies.service.Impl.CrowlingServiceImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
@RequiredArgsConstructor
@Slf4j
@Api(tags = {"실시간 영화 정보를 크롤링하는 Controller"})
public class CrowlingController {

    private final CrowlingService crowlingService;

    @Async
    @Scheduled(cron = "0 0 12 * * *", zone = "Asia/Seoul")
    @GetMapping("/api/crowling/1")
    @ApiOperation("영화 정보를 가져오는 메소드")
    public void getCrowling() throws Exception {
        crowlingService.getCrowling();
    }

    @Async
    @Scheduled(cron = "59 59 11 * * *", zone = "Asia/Seoul")
    @DeleteMapping("/api/crowling/2")
    @ApiOperation("현재 영화 정보를 삭제하는 메소드")
    public void deleteCrowling() throws IOException {
        crowlingService.deleteCrowling();
    }

}
