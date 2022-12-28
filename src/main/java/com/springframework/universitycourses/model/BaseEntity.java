package com.springframework.universitycourses.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


public class BaseEntity implements Serializable
{
	@NotNull
	private Long id;

	@NotNull(message = "Name is mandatory")
	private Long code;

	@NotBlank(message = "Name is mandatory")
	private String title;

	@NotBlank(message = "Name is mandatory")
	private String description;

	public BaseEntity(final Long id, final Long code, final String title, final String description)
	{
		this.id = id;
		this.code = code;
		this.title = title;
		this.description = description;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(final Long id)
	{
		this.id = id;
	}

	public Long getCode()
	{
		return code;
	}

	public void setCode(final Long code)
	{
		this.code = code;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(final String title)
	{
		this.title = title;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(final String description)
	{
		this.description = description;
	}
}
