package movies.service;

import lombok.RequiredArgsConstructor;
import movies.domain.ExpiredRefreshToken;
import movies.repository.ExpiredRefreshTokenRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExpiredRefreshTokenService {

    private final ExpiredRefreshTokenRepository expiredRefreshTokenRepository;

    public boolean isExpiredToken(String token) {
        return expiredRefreshTokenRepository.existsByToken(token);
    }

    public ExpiredRefreshToken addExpiredToken(String token) {
        ExpiredRefreshToken saveToken = ExpiredRefreshToken.builder()
                .token(token)
                .build();
        return expiredRefreshTokenRepository.save(saveToken);
    }
}
