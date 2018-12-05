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
import de.htw.ai.kbe.client.SongsClient;
import de.htw.ai.kbe.storage.ISongDI;
import de.htw.ai.kbe.storage.InMemorySongDI;

//Achtung Testklasse nach s.24 umschreiben 
@Path("/songs")
public class SongsWebService {

	private ISongDI songDI;

	@Inject
	public SongsWebService(ISongDI aSong) {
		this.songDI = aSong;
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Collection<Song> getAllSongs(@HeaderParam("Accept") String acceptHeader) {
		if (acceptHeader.equals("application/json")) {
			return songDI.getAllSongs();
		} else if (acceptHeader.equals("application/xml")) {
			return songDI.getAllSongs();
		}

		return null;
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
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
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
		} else {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}

	@PUT
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("/{id}")
	public Response updateSong(@Context UriInfo uriInfo, String acceptHeader, @PathParam("id") Integer id) {
		// TODO implement

		return Response.status(Response.Status.NOT_FOUND).build();

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

/*
 * Testcases id in URL ist "xyz" -> Statuscode -> 404 oder 400 -> macht Jersey
 * automatisch ! 22 gibt es nicht -> Statuscode 404 -> selber implementieren bei
 * Put keine delta updates -> ist okay wenn eine Atribut nicht gesetzt ist wird
 * überschrieben idInUrl = idInPayload = 204 -> also update war erfolgreich ->
 * schickt nichts zurück IdInUrl ist 7 aber in Payload schickt er mir die 6 ->
 * soll das richtige zurückgeben nach dem du PUT machst IDs unterscheiden sich
 * -> fehler zurückschicken -> 400 Statuscode oder ignoriere die id in payload
 * und schreibe neue id rein bei falschem Format -> json wird geschickt aber
 * inhalt falsch gibt jersey allein 400 zurück wenn er update für einen song
 * machen muss mindestens titel nicht null sein jedesmal wenn er neuen token
 * will einen neuen token geben neue songs weg wenn man server neu startet
 */