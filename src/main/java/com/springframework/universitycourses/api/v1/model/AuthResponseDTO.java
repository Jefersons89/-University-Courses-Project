package com.springframework.universitycourses.api.v1.model;

import com.springframework.universitycourses.configuration.AuthenticationConfigConstants;
import lombok.Data;


@Data
public class AuthResponseDTO
{
	private String accessToken;
	private String tokenType = AuthenticationConfigConstants.TOKEN_PREFIX;

	public AuthResponseDTO(final String accessToken)
	{
		this.accessToken = accessToken;
	}
}
