package movies.service;

import movies.domain.Account;
import movies.dto.AccountRequestDto;
import movies.dto.MailDto;

import java.util.Map;

public interface AccountService {

    Account registerAccount(AccountRequestDto requestDto);

    String login(Map<String, String> account);

    Account findByUsername(String username);

    MailDto findPw(String email);

    MailDto findId(String email);

    void updatePassword(String str, String email);


}
