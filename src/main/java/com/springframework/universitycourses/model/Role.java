package com.springframework.universitycourses.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "roles")
public class Role
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;

	@Builder
	public Role(final Long id, final String name)
	{
		this.id = id;
		this.name = name;
	}
}
