package movies.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import movies.service.CrowlingService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
@RequiredArgsConstructor
@Slf4j
public class CrowlingController {

    private final CrowlingService crowlingService;

    @Async
    @Scheduled(cron = "0 0 12 * * *", zone = "Asia/Seoul")
    @GetMapping("crowling")
    public void getCrowling() throws IOException {
        crowlingService.getCrowling();
    }

    @Async
    @Scheduled(cron = "59 59 11 * * *", zone = "Asia/Seoul")
    @GetMapping("delete/crowling")
    public void deleteCrowling() throws IOException {
        crowlingService.deleteCrowling();
    }

}
