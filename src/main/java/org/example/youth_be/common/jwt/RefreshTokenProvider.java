package org.example.youth_be.common.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.StringUtils;
import org.example.youth_be.common.exceptions.YouthBadRequestException;
import org.example.youth_be.common.exceptions.YouthNotFoundException;
import org.example.youth_be.user.domain.UserEntity;
import org.example.youth_be.user.enums.UserRoleEnum;
import org.example.youth_be.user.repository.UserRepository;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.sql.Date;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class RefreshTokenProvider implements TokenProvider {
    private final JwtProperties jwtProperties;
    private final SecretKey key;
    private final JwtParser parser;
    private final UserRepository userRepository;

    public RefreshTokenProvider(JwtProperties jwtProperties, UserRepository userRepository) {
        this.jwtProperties = jwtProperties;
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getSecretKey()));
        this.parser = Jwts.parserBuilder().setSigningKey(key).build();
        this.userRepository = userRepository;
    }

    @Override
    public TokenClaim getClaim(String token) {
        TokenClaim tokenClaim = TokenClaim.of(parser.parseClaimsJws(token).getBody());
        if (!StringUtils.equals(tokenClaim.getSub(), SUBJECT_REFRESH_TOKEN)) {
            throw new UnsupportedJwtException("토큰의 sub가 일치하지 않습니다.");
        }
        return tokenClaim;
    }

    @Override
    public String generateToken(Long userId, UserRoleEnum userRole) {
        ZoneId KST = ZoneId.of("Asia/Seoul");
        ZonedDateTime issuedAt = ZonedDateTime.now(KST).truncatedTo(ChronoUnit.SECONDS);
        ZonedDateTime expiration = issuedAt.plusDays(jwtProperties.getRefreshValidityInDays());
        return Jwts.builder()
                .claim(KEY_USER_ID, userId)
                .claim(KEY_USER_ROLE, userRole)
                .setSubject(SUBJECT_REFRESH_TOKEN)
                .setIssuedAt(Date.from(issuedAt.toInstant()))
                .setExpiration(Date.from(expiration.toInstant()))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public UserEntity getUserFromToken(String token) {
        Claims claims = getClaims(token);
        Long userId = claims.get("userId", Long.class);
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new YouthNotFoundException("존재하지 않는 사용자입니다.", null));
        return user;
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            throw new YouthBadRequestException("토큰이 유효하지 않습니다.", null);
        }
    }
}
