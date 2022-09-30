package movies.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import movies.domain.Account;
import movies.repository.AccountRepository;
import movies.repository.CommentRepository;
import movies.repository.HeartRepository;
import movies.service.AccountService;
import movies.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final AccountRepository accountRepository;

    private final AdminService adminService;

    @GetMapping("/admin/account")
    public String account(Model model) {

        List<Account> accounts = accountRepository.findAll();

        model.addAttribute("accounts",accounts);

        return "admin/account";
    }

    @GetMapping("/admin/delete")
    public String deleteAccount(@RequestParam("id") Long id,
                                RedirectAttributes attributes) {

        adminService.deleteAccount(id);

        attributes.addFlashAttribute("message", "계정을 삭제했습니다");
        return "redirect:/admin/account";
    }

}
