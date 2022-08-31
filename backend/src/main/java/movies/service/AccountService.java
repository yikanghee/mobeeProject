package movies.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import movies.Utils.MailUtil;
import movies.domain.Account;
import movies.dto.AccountFindRequestDto;
import movies.dto.AccountRequestDto;
import movies.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;


@Service
@Slf4j
@RequiredArgsConstructor
public class AccountService{

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    public Account registerAccount(AccountRequestDto requestDto){
        return accountRepository.save(
                Account.builder()
                    .username(requestDto.getUsername())
                    .password(passwordEncoder.encode(requestDto.getPassword())).email(requestDto.getEmail())
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build()
        );
    }

    public Account findPw(AccountFindRequestDto accountFindRequestDto) {
        Account result = accountRepository.findByEmail(accountFindRequestDto.getEmail());

        if (result.isE) {
            throw new IllegalArgumentException("가입된 유저가 없습니다");
        }else {
            MailUtil mail = new MailUtil();
            mail.sendMail(result);
        }

        return result;
    }

    public Account findById(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("찾을 유저가 없습니다")
        );
        return account;
    }

}
