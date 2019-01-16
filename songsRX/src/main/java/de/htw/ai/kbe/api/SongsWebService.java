package de.htw.ai.kbe.api;

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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import de.htw.ai.kbe.bean.Song;

import de.htw.ai.kbe.storage.ISongDI;
import de.htw.ai.kbe.storage.InMemorySongDI;

@Path("/songs")
public class SongsWebService {

	private ISongDI songDI;

	@Inject
	public SongsWebService(ISongDI aSong) {
		this.songDI = aSong;
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getAllSongs() {

		return Response.ok(songDI.findAllSongs()).build();

	}

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getSong(@PathParam("id") Integer id) {

		return Response.ok(songDI.findSongById(id)).build();

	}

	@Context
	private UriInfo uriInfo;

	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces(MediaType.TEXT_PLAIN)
	public Response createSong(Song song) {

		int newId = songDI.saveSong(song);
		UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
		uriBuilder.path(Integer.toString(newId));
		return Response.created(uriBuilder.build()).build();

	}

	@PUT
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/{id}")
	public Response updateSong(Song song, @PathParam("id") Integer id) {

		songDI.updateSong(song, id);
		return Response.status(Response.Status.NO_CONTENT).build();
		
	}

}


