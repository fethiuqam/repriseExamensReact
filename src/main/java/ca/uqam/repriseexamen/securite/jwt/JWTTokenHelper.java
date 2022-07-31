package ca.uqam.repriseexamen.securite.jwt;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTTokenHelper {

    @Value("${jwt.auth.app}")
    private String appName;

    @Value("${jwt.auth.secret_key}")
    private String secretKey;

    @Value("${jwt.auth.expires_in}")
    private int expiresIn;

    private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    private Claims getClaimsDuToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    public String getUsernameDuToken(String token) {
        String username;
        try {
            final Claims claims = this.getClaimsDuToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }


    public String genererToken(String username) throws InvalidKeySpecException, NoSuchAlgorithmException {

        return Jwts.builder()
                .setIssuer( appName )
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(genererDateExpiration())
                .signWith( SIGNATURE_ALGORITHM, secretKey )
                .compact();
    }

    private Date genererDateExpiration() {
        return new Date(new Date().getTime() + expiresIn * 1000);
    }

    public Boolean validerToken(String token, UserDetails userDetails) {
        final String username = getUsernameDuToken(token);
        return
                username != null &&
                        username.equals(userDetails.getUsername()) &&
                        !tokenEstExpiree(token)
                ;
    }

    public boolean tokenEstExpiree(String token) {
        Date expireDate = getDateExpiration(token);
        return expireDate.before(new Date());
    }

    private Date getDateExpiration(String token) {
        Date expireDate;
        try {
            final Claims claims = this.getClaimsDuToken(token);
            expireDate = claims.getExpiration();
        } catch (Exception e) {
            expireDate = null;
        }
        return expireDate;
    }

    public Date getDateDePublicationDuToken(String token) {
        Date issueAt;
        try {
            final Claims claims = this.getClaimsDuToken(token);
            issueAt = claims.getIssuedAt();
        } catch (Exception e) {
            issueAt = null;
        }
        return issueAt;
    }

    public String getToken(HttpServletRequest request) {
        String auth = getAuthFromCookies(request);
        return auth;
    }

    public String getAuthFromCookies(HttpServletRequest request) {
        if (request.getCookies() != null){
            return Arrays.stream(request.getCookies())
                    .filter(c -> c.getName().equals("token"))
                    .findFirst()
                    .map(Cookie::getValue)
                    .orElse(null);
        }
        else {
            return null;
        }
    }

}