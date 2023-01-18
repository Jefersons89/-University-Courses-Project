package com.springframework.universitycourses.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity
{
	@Column(name = "first_name")
	@NotBlank(message = "First Name is mandatory")
	@NotNull(message = "First Name is mandatory")
	private String firstName;

	@Column(name = "last_name")
	@NotBlank(message = "Last Name is mandatory")
	@NotNull(message = "Last Name is mandatory")
	private String lastName;

	@Column(name = "email", unique = true)
	@Email(message = "Email is mandatory")
	@NotNull(message = "Email is mandatory")
	private String email;

	@Column(name = "password")
	@NotBlank(message = "Password id mandatory")
	@NotNull(message = "Password id mandatory")
	private String password;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private Set<Role> roles = new HashSet<>();

	public User(final Long id, final String firstName, final String lastName,
			final String email, final String password,
			final Set<Role> roles)
	{
		super(id);
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.roles = roles;
	}
}
