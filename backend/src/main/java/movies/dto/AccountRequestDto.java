package movies.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequestDto {

    private String username;
    private String email;
    private String password;
    private String passwordConfirm;
}
