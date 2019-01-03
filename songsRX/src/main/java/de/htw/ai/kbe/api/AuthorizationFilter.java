package de.htw.ai.kbe.api;



import java.io.IOException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class AuthorizationFilter implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext containerRequestContext) throws IOException {

		String authToken = containerRequestContext.getHeaderString("Authorization");

		if (authToken == null || authToken.equals("")) {
			if (!containerRequestContext.getUriInfo().getPath().contains("auth")) {
				throw new WebApplicationException(Response.Status.UNAUTHORIZED);
			}
		} else {
			if (!UserWebService.isValidToken(authToken)) {
				throw new WebApplicationException(Response.Status.UNAUTHORIZED);
			}
		}

	}

}
