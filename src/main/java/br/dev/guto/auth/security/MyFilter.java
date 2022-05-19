package br.dev.guto.auth.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class MyFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {
			System.out.println("I'm inside this filter");

			if (request.getHeader("Authorization") != null) {
				Authentication authentication = MyJwtTokenUtil.getAuthenticationToken(request);
				if (authentication != null) {
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
				else {
					response.setStatus(401);
				}
			}

			filterChain.doFilter(request, response);
		} catch (Exception e) {
			response.sendError(401, "Error on Token");
			filterChain.doFilter(request, response);
		}
	}

}
