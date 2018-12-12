package de.htw.ai.kbe.services;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.inject.*;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.htw.ai.kbe.bean.User;
import de.htw.ai.kbe.storage.IUserDI;

//Achtung Testklasse nach s.24 umschreiben 
@Path("/auth")
public class UserWebService {
	private IUserDI userDI;
	private static Map<String, String> tokens;
	private static String testToken = "test";

	@Inject
	public UserWebService(IUserDI aUser) {
		this.userDI = aUser;
		tokens = Collections.synchronizedMap(new HashMap<>());
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response getToken(@QueryParam("userId") String userId) {
		if (userDI.getUser(userId)) {
			String token = generateToken(userId);
			tokens.put(userId, token);
			return Response.ok(token).build();
		}

		return Response.status(Response.Status.FORBIDDEN).entity("User hat keine Berechtigung").build();
	}

	private String generateToken(String userId) {
		String token = UUID.randomUUID().toString();
		token = token.replace("-", "");
		return token;
	}

	public static boolean isValidToken(String token) {
		if (tokens != null) {
			return tokens.containsValue(token);
		} 
		
		if (token.equals(testToken)) {
			return true;
		}
		
		return false;
	}
}
