package movies.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {


    private String username;

    private String email;

    private String password;


}