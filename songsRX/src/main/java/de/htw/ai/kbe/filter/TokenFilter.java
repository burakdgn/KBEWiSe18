package de.htw.ai.kbe.filter;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import de.htw.ai.kbe.services.UserWebService;

import java.io.IOException;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class TokenFilter implements ContainerRequestFilter {
//	UserWebService userWebService;
//
//	@Inject
//	public TokenFilter(UserWebService userWebService) {
//		this.userWebService = userWebService;
//	}

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

		if (authorizationHeader == null || !UserWebService.isValidToken(authorizationHeader)) {
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
		} else {
			System.out.println("Valid");
			requestContext.abortWith(Response.ok("Valid").build());
		}
	}
}