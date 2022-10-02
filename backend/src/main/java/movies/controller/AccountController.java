package movies.controller;


import lombok.extern.slf4j.Slf4j;
import movies.domain.Account;
import movies.domain.Message;
import movies.dto.AccountFindRequestDto;
import movies.repository.AccountRepository;
import movies.service.AccountService;
import com.google.gson.JsonObject;
import movies.config.JwtTokenProvider;
import movies.dto.AccountRequestDto;
import lombok.RequiredArgsConstructor;

import movies.service.ExpiredRefreshTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@Slf4j
public class AccountController {

    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AccountRepository accountRepository;
    private final ExpiredRefreshTokenService expiredRefreshTokenService;

    @PostMapping("/api/signup")
    public ResponseEntity registerAccount(@RequestBody AccountRequestDto requestDto) {

        Optional<Account> found = accountRepository.findByUsername(requestDto.getUsername());

        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 ID가 존재합니다.");
        }
        if (!requestDto.getPassword().equals(requestDto.getPasswordConfirm())) {
            throw new IllegalArgumentException("pw가 일치하지 않습니다.");
        }

        accountService.registerAccount(requestDto);
        return ResponseEntity.ok().build();
    }
    // 로그인
    @PostMapping("/api/login")
    public String login(@RequestBody Map<String, String> account) {

        Account acc = accountRepository.findByUsername(account.get("username")).orElseThrow(() -> new IllegalArgumentException("가입되지 않은 회원 입니다."));

        if (!passwordEncoder.matches(account.get("password"), acc.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        JsonObject obj = new JsonObject();
        obj.addProperty("token", jwtTokenProvider.createToken(acc.getUsername(), acc.getRoles()));
        obj.addProperty("refreshtoken", jwtTokenProvider.createrefreshToken(acc.getUsername(), acc.getRoles()));

        log.info("로그인 token 값 : " + obj.toString());

        return obj.toString();
    }

    @PostMapping("/api/pw-find")
    public ResponseEntity findPw(@RequestBody AccountFindRequestDto accountFindRequestDto) {

        accountService.findPw(accountFindRequestDto);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/refresh")
    public String tokenRefresh(@RequestBody Map<String, String> account) throws IllegalArgumentException {

        Optional<Account> accountOptional = accountRepository.findByUsername(account.get("username"));
        Account acc = accountOptional.get();

        JsonObject obj = new JsonObject();

        obj.addProperty("token", jwtTokenProvider.createToken(acc.getUsername(), acc.getRoles()));

        log.info("로그인 token 값 : " + obj.toString());

        return obj.toString();
    }
}