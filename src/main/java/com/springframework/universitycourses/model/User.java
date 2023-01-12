package com.springframework.universitycourses.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
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

	public User(final Long id, final String firstName, final String lastName,
			final String email)
	{
		super(id);
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
}
