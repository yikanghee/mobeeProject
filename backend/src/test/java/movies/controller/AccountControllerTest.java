package movies.controller;

import movies.dto.AccountRequestDto;
import movies.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {

    @InjectMocks
    private AccountController accountController;

    @Mock
    private AccountServiceImpl accountService;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
    }

//    @DisplayName("회원 가입 성공")
//    @Test
//    void 회원가입성공() throws Exception {
//        //given
//        AccountRequestDto accountDto = AccountRequestDto();
//        Mockito.doReturn(null).when(accountService).findByUsername(accountDto.getUsername());
//
//        //when
//        ResultActions actions = mockMvc.perform(
//                MockMvcRequestBuilders.post("")
//        )
//
//
//        //then
//    }

    private AccountRequestDto AccountRequestDto() {

        AccountRequestDto accountDto = new AccountRequestDto();
        accountDto.setUsername("test");
        accountDto.setEmail("test@test.com");
        accountDto.setPassword("1234");
        accountDto.setPasswordConfirm("1234");
        return accountDto;
    }



}
