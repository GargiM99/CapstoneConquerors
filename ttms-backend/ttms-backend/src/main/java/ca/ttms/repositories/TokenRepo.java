package ca.ttms.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import ca.ttms.beans.Token;

/**
 * Repository class for Token class
 * 
 * @author Hamza & Akshat
 * date: 2023/03/07
 */

public interface TokenRepo extends JpaRepository<Token, Integer>{
	  @Query(value = 
			  """
		      SELECT t FROM Token t INNER JOIN user u\s
		      ON t.user.id = u.id\s
		      WHERE u.id = :id AND (t.expired = false OR t.revoked = false)\s
		      """)
		List<Token> findAllValidTokenByUser(Integer id);

		Optional<Token> findByToken(String token);
		
		@Transactional
		@Modifying
		@Query("DELETE FROM Token t WHERE t.user IN (SELECT u FROM User u WHERE u.username = :un)")
		Integer deleteUserTokens(@Param("un") String username);
}
