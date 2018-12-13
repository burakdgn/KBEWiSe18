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
	public Response getAllSongs(@HeaderParam("Accept") String acceptHeader) {
		if (acceptHeader.equals("application/json")) {
			return Response.ok(songDI.getAllSongs()).build();
		} else if (acceptHeader.equals("application/xml")) {
			GenericEntity<Collection<Song>> entity = new GenericEntity<Collection<Song>>(songDI.getAllSongs()) {};
			return Response.ok(entity).build();
		}

		return Response.status(Response.Status.NOT_FOUND).build();
	}

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getSong(@HeaderParam("Accept") String acceptHeader, @PathParam("id") Integer id) {
		if (acceptHeader.equals("application/json") || acceptHeader.equals("application/xml")) {
			if (songDI.getSong(id) != null) {
				return Response.ok(songDI.getSong(id)).build();
			} else {
				return Response.status(Response.Status.NOT_FOUND).build();
			}
		} 

		return Response.status(Response.Status.NOT_FOUND).build();
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces(MediaType.TEXT_PLAIN)
	public Response createSong(@Context UriInfo uriInfo, String content)
			throws JsonParseException, JsonMappingException, IOException, JAXBException {
		Song song = InMemorySongDI.contentToSong(content);
		if (song != null) {
			int newId = songDI.addSong(song);
			UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
			uriBuilder.path(Integer.toString(newId));

			return Response.created(uriBuilder.build()).build();
		} 

		return Response.status(Response.Status.BAD_REQUEST).build();
	}

	@PUT
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("/{id}")
	public Response updateSong(@Context UriInfo uriInfo, @PathParam("id") Integer id, String content) {
		Song song = InMemorySongDI.contentToSong(content);
		if (song != null) {
			song = songDI.updateSong(song, id);

			if (song.getId() == id) {
				return Response.status(Response.Status.NO_CONTENT).build();
			} else {
				return Response.status(Response.Status.BAD_REQUEST).build();
			}
		}

		return Response.status(Response.Status.BAD_REQUEST).build();
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response deleteSong(@PathParam("id") Integer id) {

		if (songDI.deleteSong(id) != null) {
			return Response.status(Response.Status.NO_CONTENT).build();
		} 

		return Response.status(Response.Status.NOT_FOUND).build();
	}

}
