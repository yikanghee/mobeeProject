package movies.controller;


import lombok.extern.slf4j.Slf4j;
import movies.repository.AccountRepository;
import movies.service.AccountService;
import movies.config.JwtTokenProvider;
import movies.dto.AccountRequestDto;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@Slf4j
public class AccountController {

    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AccountRepository accountRepository;

    @PostMapping("/api/account/signup")
    public ResponseEntity registerAccount(@RequestBody AccountRequestDto requestDto) {

        log.info("registerAccount Controller Start!!");

        accountService.registerAccount(requestDto);

        log.info("registerAccount Controller End!!");

        return ResponseEntity.ok().build();
    }
    // 로그인
    @PostMapping("/api/account/login")
    public String login(@RequestBody Map<String, String> account) {

        log.info("login Start!!");

        String obj = accountService.login(account);

        log.info("login End!!");

        return obj;
    }

    @PostMapping("/api/account/findPassword")
    public ResponseEntity findPassword(@RequestBody Map<String, String> account) {

        log.info("findPassword Start!!");

        log.info(account.get("email"));

        accountService.findPw(account.get("email"));

        log.info("findPassword End!!");

        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/account/findId")
    public ResponseEntity findId(@RequestBody Map<String, String> account) {

        log.info("findId Start!!");

        log.info(account.get("email"));

        accountService.findId(account.get("email"));

        log.info("findId End!!");

        return ResponseEntity.ok().build();
    }
}