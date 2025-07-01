package com.FlighSystem.domain;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.FlighSystem.token.Token;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
@EntityListeners(AuditingEntityListener.class) // Enables auditing
public class User implements UserDetails {
	private static final long serialVersionUID = 7321374061017039662L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String fullName;

	
	@Column(unique = true, nullable = false) // Ensure email is unique and not null
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String username;
    
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Role role;
	
	
	@Embedded
	private Address address;

	private Integer age;
	

	@Enumerated(EnumType.STRING)
	private Gender gender;




	@OneToMany(mappedBy = "user")
	private List<Token> tokens;

	private Date passwordChangeTime;

    
	@CreatedDate
	@Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime createDate;
	
	@LastModifiedDate
	@Column(insertable  = false)
    @Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime lastModified ;

	public User(String fullName, String email, String password, String username, Role role, Address address,
			Integer age, Gender gender) {
		super();
		this.fullName = fullName;
		this.email = email;
		this.password = password;
		this.username = username;
		this.role = role;
		this.address = address;
		this.age = age;
		this.gender = gender;
	}

	
	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
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

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_"+ role.name()));
	}

	// method to check if password is expired or not we can use it if we want
	public boolean isPasswordExpired() {

		if (this.passwordChangeTime == null)
			return false;

		long currentTime = System.currentTimeMillis();
		long lastPasswordChangedTime = this.passwordChangeTime.getTime();

		return currentTime > lastPasswordChangedTime + PASSWORD_EXPIRATION_TIME;

	}



	private static final long PASSWORD_EXPIRATION_TIME = 30L * 24L * 60L * 60L * 1000L;

}
