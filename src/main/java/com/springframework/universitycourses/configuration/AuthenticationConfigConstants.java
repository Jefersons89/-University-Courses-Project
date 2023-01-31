package com.springframework.universitycourses.configuration;

public class AuthenticationConfigConstants
{
	public static final String SECRET = "Java_to_Dev_Secret";
	public static final long EXPIRATION_TIME = 700000000;
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String H2_CONSOLE = "/h2-console/**";
	public static final String AUTH = "/api/auth/**";
	public static final String URL_STUDENT = "/students";
	public static final String URL_TEACHER = "/teachers";
	public static final String URL_ASSIGNMENTS = "/assignments";
	public static final String URL_COURSES = "/courses";
	public static final String URL_ENROLLMENTS = "/enrollments";
}
