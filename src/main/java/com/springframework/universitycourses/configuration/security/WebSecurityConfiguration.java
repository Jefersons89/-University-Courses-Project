package com.springframework.universitycourses.configuration.security;

import com.springframework.universitycourses.configuration.AuthenticationConfigConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration
{
	private final JwtAuthEntryPoint authEntryPoint;

	@Autowired
	public WebSecurityConfiguration(final JwtAuthEntryPoint authEntryPoint)
	{
		this.authEntryPoint = authEntryPoint;
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder()
	{
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
	{
		http
				.cors().and().csrf().disable()
				.exceptionHandling().authenticationEntryPoint(authEntryPoint)
				.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authorizeRequests()
				.antMatchers(HttpMethod.POST,
						AuthenticationConfigConstants.URL_STUDENT,
						AuthenticationConfigConstants.URL_TEACHER,
						AuthenticationConfigConstants.AUTH).permitAll()
				.antMatchers(AuthenticationConfigConstants.H2_CONSOLE).permitAll()
				.antMatchers(HttpMethod.GET,
						AuthenticationConfigConstants.URL_STUDENT + "/**").hasAnyAuthority("STUDENT", "TEACHER", "ADMIN")
				.antMatchers(HttpMethod.GET,
						AuthenticationConfigConstants.URL_TEACHER + "/**").hasAnyAuthority("TEACHER", "ADMIN")
				.antMatchers(HttpMethod.GET,
						AuthenticationConfigConstants.URL_ASSIGNMENTS + "/**").hasAnyAuthority("STUDENT", "TEACHER", "ADMIN")
				.antMatchers(HttpMethod.GET,
						AuthenticationConfigConstants.URL_COURSES + "/**").hasAnyAuthority("STUDENT", "TEACHER", "ADMIN")
				.antMatchers(HttpMethod.GET,
						AuthenticationConfigConstants.URL_ENROLLMENTS + "/**").hasAnyAuthority("STUDENT", "TEACHER", "ADMIN")
				.antMatchers(HttpMethod.PUT,
						AuthenticationConfigConstants.URL_TEACHER + "/**").hasAnyAuthority("TEACHER", "ADMIN")
				.antMatchers(HttpMethod.PUT,
						AuthenticationConfigConstants.URL_STUDENT + "/**").hasAnyAuthority("STUDENT", "ADMIN")
				.antMatchers(HttpMethod.PUT,
						AuthenticationConfigConstants.URL_ENROLLMENTS + "/**").hasAnyAuthority("TEACHER", "ADMIN")
				.antMatchers(HttpMethod.DELETE,
						AuthenticationConfigConstants.URL_TEACHER + "/**").hasAnyAuthority("TEACHER", "ADMIN")
				.antMatchers(HttpMethod.DELETE,
						AuthenticationConfigConstants.URL_STUDENT + "/**").hasAnyAuthority("STUDENT", "ADMIN")
				.anyRequest().authenticated()
				.and()
				.headers().frameOptions().sameOrigin();

		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception
	{
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public JWTAuthenticationFilter jwtAuthenticationFilter()
	{
		return new JWTAuthenticationFilter();
	}
}
