package ca.ttms.components;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import ca.ttms.repositories.TokenRepo;
import ca.ttms.services.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * Component for filtering though request for security
 * 
 * @author Hamza 
 * date: 2023/03/08 
 */

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JWTService jwtService;
	private final UserDetailsService userDetailsService;
	private final TokenRepo tokenRepo;

	@Override
	/**
	 * Filter that will check the header, and username for authentication
	 */
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain) throws ServletException, IOException {

		final String authHeader = request.getHeader("Authorization");
		final String jwtoken;
		final String subject;

		/**
		 * Checks if there is a JWToken
		 */
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		/**
		 * Gets all the values for the header excluding Bearer_ and the subject
		 */
		jwtoken = authHeader.substring(7);
		subject = jwtService.extractSubject(jwtoken);

		/**
		 * Checks if there is a subject and it hasn't already been logged in
		 */
		if (subject != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(subject);

			/**
			 * Checks if token is valid using the token and database
			 */
			var dbToken = tokenRepo.findByToken(jwtoken);
			if (!dbToken.get().expired && !dbToken.get().revoked 
					&& jwtService.isJwtokenValid(jwtoken, userDetails)) {

				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());

				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		filterChain.doFilter(request, response);
	}
}
