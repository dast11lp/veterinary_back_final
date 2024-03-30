package io.demo.veterinary.filter;

import java.io.IOException;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.demo.veterinary.jwt.JwtUtil;
import io.demo.veterinary.service.MyUserDetailService;

 
@Component
public class CustomAuthorizationFilter extends OncePerRequestFilter{
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private MyUserDetailService myUserDetailService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String authHeader = request.getHeader("Authorization");
		
		
		if(authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")) {
			String jwt = authHeader.substring(7);
			
			if(jwt == null || jwt.isBlank()) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token in Bearer Header");
			}else {
				String email = this.jwtUtil.jwtVerifier(jwt);
				UserDetails userDetails = this.myUserDetailService.loadUserByUsername(email);
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
				if(SecurityContextHolder.getContext().getAuthentication() == null) {
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			}
		}
		
		filterChain.doFilter(request, response);
	}

}