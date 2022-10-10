package movies.service.Impl;

import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import movies.config.JwtTokenProvider;
import movies.domain.Account;
import movies.dto.AccountRequestDto;
import movies.dto.MailDto;
import movies.exception.AccountException;
import movies.exception.result.AccountExceptionResult;
import movies.repository.AccountRepository;
import movies.service.AccountService;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final JwtTokenProvider jwtTokenProvider;

    private final PasswordEncoder passwordEncoder;

    @Value("${naver.address}")
    private String FROM_ADDRESS;

    @Value("${naver.id}")
    private String hostId;

    @Value("${naver.pw}")
    private String hostPw;

    @Value("${naver.id}")
    private String HOSTID;

    @Value("${naver.pw}")
    private String HOSTPW;

    public Account registerAccount(AccountRequestDto requestDto) {

        Optional<Account> found = accountRepository.findByUsername(requestDto.getUsername());

        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 ID가 존재합니다.");
        }
        if (!requestDto.getPassword().equals(requestDto.getPasswordConfirm())) {
            throw new IllegalArgumentException("pw가 일치하지 않습니다.");
        }

        return accountRepository.save(
                Account.builder()
                        .username(requestDto.getUsername())
                        .password(passwordEncoder.encode(requestDto.getPassword())).email(requestDto.getEmail())
                        .roles(Collections.singletonList("ROLE_USER"))
                        .build()
        );
    }

    public String login(Map<String, String> account) {

        Account acc = accountRepository.findByUsername(account.get("username")).orElseThrow(() -> new AccountException(AccountExceptionResult.ACCOUNT_NOT_FOUND));

        if (!passwordEncoder.matches(account.get("password"), acc.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        JsonObject obj = new JsonObject();
        obj.addProperty("token", jwtTokenProvider.createToken(acc.getUsername(), acc.getRoles()));
        obj.addProperty("refreshtoken", jwtTokenProvider.createrefreshToken(acc.getUsername(), acc.getRoles()));

        log.info("로그인 token 값 : " + obj.toString());

        return obj.toString();
    }

    public Account findByUsername(String username) {
        Account account = accountRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("찾을 유저가 없습니다")
        );
        return account;
    }


    public MailDto findPw(String email) {

        log.info("findPw Start!!");

        Optional<Account> account = accountRepository.findAllByEmail(email);

        if (!account.isPresent()) {
            throw new IllegalArgumentException("유효한 이메일이 아닙니다");
        }

        String userName = account.get().getUsername();

        String str = getTempPassword();
        MailDto dto = new MailDto();
        dto.setAddress(email);
        dto.setTitle(userName + "님의 MOBEE 임시비밀번호 안내 이메일 입니다.");
        dto.setMessage("<p> 안녕하세요. MOBEE 임시비밀번호 안내 관련 이메일 입니다." + "[" + userName + "]" + "님의 임시 비밀번호는 "
                + str + " 입니다. </p>");
        updatePassword(str, email);

        mailSend(dto);

        log.info("findPw End!!");

        return dto;
    }

    public MailDto findId(String email) {

        log.info("findId Start!!");

        Optional<Account> account = accountRepository.findAllByEmail(email);

        String userName = account.get().getUsername();

        MailDto dto = new MailDto();
        dto.setAddress(email);
        dto.setTitle(userName + "님의 아이디 안내 이메일 입니다.");
        dto.setMessage("<p> 안녕하세요. 아이디 안내 관련 이메일 입니다." + "[" + userName + "]" + "님의 아이디는 "
                + userName + " 입니다. </p>");

        mailSend(dto);

        log.info("findId End!!");

        return dto;
    }

    public void updatePassword(String str, String email) {

        log.info("updatePassword Start!!");

        Optional<Account> account = accountRepository.findAllByEmail(email);

        log.info(account.get().getEmail());

        if (!account.isPresent()) {
            throw new IllegalArgumentException("존재하는 이메일이 없습니다.");
        }

        String username = account.get().getUsername();

        log.info("username = " + username);
        String password = passwordEncoder.encode(str);
        log.info("password = " + password);

        accountRepository.updatePassword(username, password);

        log.info("updatePassword End!!");

    }

    private String getTempPassword() {

        log.info("getTempPassword Start!!");

        char[] charSet = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        String str = "";

        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }

        log.info("getTempPassword End!!");

        return str;
    }

    private void mailSend(MailDto mailDto) {

        String charSet = "utf-8";
        String hostSMTP = "smtp.naver.com";
        String hostSMTPid = HOSTID;
        String hostSMTPpwd = HOSTPW;

        String fromEmail = FROM_ADDRESS;
        String fromName = "MOBEE";

        String mail = mailDto.getAddress();

        try {
            HtmlEmail email = new HtmlEmail();
            email.setDebug(true);
            email.setCharset(charSet);
            email.setSSL(true);
            email.setHostName(hostSMTP);
            email.setSmtpPort(587);

            email.setAuthentication(hostSMTPid, hostSMTPpwd);
            email.setTLS(true);
            email.addTo(mail, charSet);
            email.setFrom(fromEmail, fromName, charSet);
            email.setSubject(mailDto.getTitle());
            email.setHtmlMsg(mailDto.getMessage());
            email.send();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
