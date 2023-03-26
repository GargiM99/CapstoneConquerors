package ca.ttms.services;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * Service for JWTokens such as extracting claims, signing, generating
 * 
 * @author Hamza 
 * date: 2023/03/08 
 */

@Service
public class JWTService {

	/**
	 * Key of HS512
	 */
	private static final String SECRET_KEY = "782F413F4428472B4B6150645367566B5970337336763979244226452948404D6351655468576D5A7134743777217A25432A462D4A614E645267556A586E3272";
	
	/**
	 * Length of how long until a key will expire in milliseconds
	 */
	private static final long TOKEN_DURATION = 1000 * 60 * 24;
	
	
	/**
	 * Extract the subject from the JWToken which is the username
	 * 
	 * @param jwtoken
	 * @return String
	 */
	public String extractSubject(String jwtoken) {
		return extractClaims(jwtoken, Claims::getSubject);
	}
	
	/**
	 * Checks if JWT is valid based on the subject and expiration date
	 * 
	 * @param jwtoken
	 * @param userDetails
	 * @return boolean
	 */
	public boolean isJwtokenValid(String jwtoken, UserDetails userDetails) {
		final String subject = extractSubject(jwtoken);
		return (subject.equals(userDetails.getUsername())) && !isJwtokenExpired(jwtoken);
	}

	/**
	 * Checks if token is expired by comparing the current time to the JWTs
	 * 
	 * @param jwtoken
	 * @return boolean
	 */
	public boolean isJwtokenExpired(String jwtoken) {
		return extractExpirationDate(jwtoken).before(new Date());
	}

	/**
	 * Extracts the expiration date from the JWTs
	 * 
	 * @param jwtoken
	 * @return Date
	 */
	public Date extractExpirationDate(String jwtoken) {
		return extractClaims(jwtoken, Claims::getExpiration);
	}

	/**
	 * Extract certain claims using various functions
	 * 
	 * @param jwtoken: A string JWT whose claims need to be extracted
	 * @param claimsResolver: A function which will extract certain claims
	 * @return: A claim such as the subject which is a Jwts object
	 */
	public <T> T extractClaims(String jwtoken, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(jwtoken);
		return claimsResolver.apply(claims);
	}

	/**
	 * Generates a blank token with no extra claims
	 * 
	 * @param userDetails
	 * @return String
	 */
	public String generateJwtoken(UserDetails userDetails) {
		return generateJwtoken(new HashMap<>(), userDetails);
	}
	
	/**
	 * Generates a JWT using the extra claims, and the user's username as subject
	 * 
	 * @param extraClaims
	 * @param userDetails
	 * @return String
	 */
	public String generateJwtoken(Map<String, Object> extraClaims, UserDetails userDetails) {
		return Jwts
				.builder()
				.setClaims(extraClaims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TOKEN_DURATION))
				.signWith(getSecretKey(), SignatureAlgorithm.HS512).compact();
	}
	
	/**
	 * Generates a JWT using the extra claims, and the duaration is specifed
	 * 
	 * @param extraClaims
	 * @param userDetails
	 * @param tokenDuration
	 * @return String
	 */
	public String generateJwtoken(Map<String, Object> extraClaims, 
			UserDetails userDetails, long tokenDuration ) {
		return Jwts
				.builder()
				.setClaims(extraClaims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + tokenDuration))
				.signWith(getSecretKey(), SignatureAlgorithm.HS512).compact();
	}

	/**
	 * Extracts all the claims from a JWT by creating a JWTS object
	 * 
	 * @param jwtoken
	 * @return Claims
	 */
	public Claims extractAllClaims(String jwtoken) {
		return Jwts
				.parserBuilder()
				.setSigningKey(getSecretKey())
				.build()
				.parseClaimsJws(jwtoken)
				.getBody();
	}
	
	/**
	 * Gets the secret key in HS format
	 * 
	 * @return Key
	 */
	private Key getSecretKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
