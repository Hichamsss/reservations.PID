package be.iccbxl.pid.Security;


import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import be.iccbxl.pid.Model.User;
import lombok.Data;

@Data
public class CustomUserDetails implements UserDetails{

    
	private static final long serialVersionUID = 1L;
	
	private Long id;
	  private String username;
      private String password;
      private String firstname;
      private String lastname;
      private String email;
      private String langue;
      private LocalDateTime createad;
      
      private List<GrantedAuthority> authorities;
      
	  
	  
	  public CustomUserDetails(User user)
		{
		  this.id = user.getId();
		this.username = user.getLogin();
		this.password = user.getPassword();
		this.firstname = user.getFirstname();
		this.lastname = user.getLastname();
		this.email = user.getEmail();
		this.langue = user.getLangue();
		this.createad = user.getCreated_at();

		
	    this.authorities = user.getRoles().stream()
	            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole()))
	            .collect(Collectors.toList());
		  
		}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	

}
