package com.rq.manager.authusers.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.rq.manager.authusers.constants.Constants;
import com.rq.manager.authusers.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;

/**
 * The Class JwtAuthenticationFilter.
 */
@NoArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	/** The jwt util. */
	@Autowired
    private JwtService jwtUtil;
    
    /** The user service. */
    @Autowired
    private UserService userService;
	
	/**
	 * Do filter internal.
	 *
	 * @param request the request
	 * @param response the response
	 * @param filterChain the filter chain
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String authorizationHeader = request.getHeader(Constants.AUTHORIZATION);
		
		String userName = null;
		String jwt = null;
		
		if(authorizationHeader != null && authorizationHeader.startsWith(Constants.BEARER)) {
			jwt = authorizationHeader.substring(7);
			userName = jwtUtil.extractUserName(jwt);
		}
		
		if(userName != null && 
				SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userService.loadUserByUsername(userName);
				if(jwtUtil.validateToken(jwt,userDetails)) {
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
							new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
					usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
		}
		filterChain.doFilter(request, response);
	}
}