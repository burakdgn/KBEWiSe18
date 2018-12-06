package de.htw.ai.kbe.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Collection;

import javax.inject.*;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.htw.ai.kbe.bean.Song;
import de.htw.ai.kbe.bean.User;
import de.htw.ai.kbe.client.SongsClient;
import de.htw.ai.kbe.storage.ISongDI;
import de.htw.ai.kbe.storage.IUserDI;
import de.htw.ai.kbe.storage.InMemorySongDI;
import de.htw.ai.kbe.storage.InMemoryUserDI;

//Achtung Testklasse nach s.24 umschreiben 
@Path("/auth")
public class UserWebService {


	private IUserDI userDI;

	@Inject
	public UserWebService(IUserDI aUser) {
		this.userDI = aUser;
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response getToken( @QueryParam("userId") String userId) {
	
		if(userDI.getUser(userId)) {
			String token = userDI.generateToken(userId);
			
			
			return Response.ok(token).build();
			
		}

		return Response.status(Response.Status.FORBIDDEN).entity("User hat keine Berechtigung").build();
	}
	
	
	@GET
	@Path("/user")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<User> getAllUsers(){
		return userDI.getAllUsers();
	}
	
}

