package com.rq.manager.authusers.jwt;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.rq.manager.authusers.constants.Constants;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * The Class JwtEntryPoint.
 */
public class JwtEntryPoint implements AuthenticationEntryPoint{

	/**
	 * Commence.
	 *
	 * @param request the request
	 * @param response the response
	 * @param authException the auth exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ServletException the servlet exception
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, Constants.UNAUTHORIZED);
	}
}
