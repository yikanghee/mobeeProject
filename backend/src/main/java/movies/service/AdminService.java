package movies.service;

import lombok.RequiredArgsConstructor;
import movies.repository.AccountRepository;
import movies.repository.CommentRepository;
import movies.repository.HeartRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AccountRepository accountRepository;
    private final HeartRepository heartRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void deleteAccount(Long id) {

        heartRepository.deleteByAccountId(id);
        commentRepository.deleteByAccountId(id);

        accountRepository.deleteById(id);

    }
}
