package com.springframework.universitycourses.configuration.security;

import com.springframework.universitycourses.configuration.AuthenticationConfigConstants;
import com.springframework.universitycourses.configuration.services.AuthenticationUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JWTAuthenticationFilter extends OncePerRequestFilter
{
	@Autowired
	private JWTTokenGenerator tokenGenerator;

	@Autowired
	private AuthenticationUserDetailService userDetailService;

	@Override
	protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
			final FilterChain filterChain)
			throws ServletException, IOException
	{
		String token = getJWTTokenFromRequest(request);
		if (StringUtils.hasText(token) && tokenGenerator.validateToken(token))
		{
			String username = tokenGenerator.getUsernameFromJWT(token);

			UserDetails userDetails = userDetailService.loadUserByUsername(username);
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,
					userDetails.getAuthorities());
			authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}
		filterChain.doFilter(request, response);
	}

	private String getJWTTokenFromRequest(HttpServletRequest httpServletRequest)
	{
		String bearerToken = httpServletRequest.getHeader(AuthenticationConfigConstants.HEADER_STRING);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(AuthenticationConfigConstants.TOKEN_PREFIX))
		{
			return bearerToken.substring(7);
		}
		return null;
	}
}
