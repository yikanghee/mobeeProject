package movies.dto;

import lombok.Getter;

@Getter
public class AccountRequestDto {

    private String username;
    private String email;
    private String password;
    private String passwordConfirm;
}
