package ca.ttms.beans;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import ca.ttms.beans.enums.Roles;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class for users of ttms
 * 
 * @author Hamza & Akshat 
 * date: 2023/03/07 
 */

//Uses this class as an object for JPA
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String firstname;
	private String lastname;
	private String username;
	private String email;
	private String password;

	@Enumerated(EnumType.STRING)
	private Roles role;

	@OneToMany(mappedBy = "user")
	private List<Token> tokens;

	// Returns the authority of the user
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	// Returns the password
	@Override
	public String getPassword() {
		return password;
	}

	// Returns the username
	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	// For now it's always true but locks may be added
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	// For now it's always true but credential expiration may be added
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	// For now it's always true but disabling account may be added
	@Override
	public boolean isEnabled() {
		return true;
	}

}
