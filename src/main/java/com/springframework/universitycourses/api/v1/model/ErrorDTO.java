package com.springframework.universitycourses.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDTO
{
	private Integer errorCode;
	private String errorMessage;
}
