package com.cts.mba.util;



import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;


@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

	private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);





	@Override
	public void commence(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response,
			AuthenticationException authException) throws IOException, javax.servlet.ServletException {
		// TODO Auto-generated method stub
		logger.error("Unauthorized error: {}", authException.getMessage());
		response.sendError(javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
		
	}

}