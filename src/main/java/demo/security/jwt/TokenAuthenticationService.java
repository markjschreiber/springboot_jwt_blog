package demo.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;


public class TokenAuthenticationService {

    private long EXPIRATIONTIME = 1000 * 60 * 60 * 24 * 10; // 10 days
    private String secret = "ThisIsASecret";
    private String tokenPrefix = "Bearer";
    private String headerString = "Authorization";
    
    /**
     * Generate a JWT and add it to the {@code response}
     * @param response the signed compact JWT is added as a header 
     * @param username the username to store in the JWT
     */
    public void addAuthentication(HttpServletResponse response, String username)
    {
        // We generate a token now.
        String JWT = Jwts.builder()
                    .setSubject(username)
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                    .signWith(SignatureAlgorithm.HS512, secret)
                    .compact();
        response.addHeader(headerString,tokenPrefix + " "+ JWT);
    }

    /**
     * If there is a JWT in the header of the {@request} the JWT will be decrypted and verifiied, and an {@code Authentication} 
     * will be created for the {@code username} in that JWT. Otherwise {@code null} will returned.
     * @param request will be examined for a JWT in the header
     * @return {@code AuthenticatedUser} representing the user name in the JWT or null if there is no JWT (user is not authenticated).
     */
    public Authentication getAuthentication(HttpServletRequest request)
    {
        String token = request.getHeader(headerString);
        if(token != null)
        {
            // parse the token.
            String username = Jwts.parser()
                        .setSigningKey(secret)
                        .parseClaimsJws(token)
                        .getBody()
                        .getSubject();
            if(username != null) // we managed to retrieve a user
            {
                return new AuthenticatedUser(username);
            }
        }
        return null;
    }
}
