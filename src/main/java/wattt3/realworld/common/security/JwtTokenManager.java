package wattt3.realworld.common.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtTokenManager {

    private static final long TOKEN_VALID_TIME = 30 * 60 * 1000L;

    private final Key key;

    public JwtTokenManager(@Value("${jwt.secret}") String secretKey) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    public String generate(String email) {
        Date now = new Date();

        return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + TOKEN_VALID_TIME))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    public boolean isValid(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid Jwt Token");
        } catch (ExpiredJwtException e) {
            log.info("Expired Jwt Token");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported Jwt Token");
        } catch (IllegalArgumentException e) {
            log.info("Illegal Jwt Token");
        }
        return false;
    }
}
