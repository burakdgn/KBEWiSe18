
package de.htw.ai.kbe.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
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
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import com.mysql.jdbc.NotImplemented;

import de.htw.ai.kbe.bean.Song;
import de.htw.ai.kbe.bean.Songlist;
import de.htw.ai.kbe.storage.ISongListDI;

@Path("/songLists")
public class SongListWebService {

	private ISongListDI songListDI;

	@Context
	private UriInfo uriInfo;

	@Inject
	public SongListWebService(ISongListDI aSongListDI) {
		this.songListDI = aSongListDI;
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getAllSongsLists(@HeaderParam("Authorization") String authHeader,
			@QueryParam("userId") String userId) {
		List<Songlist> songlists = (List<Songlist>) songListDI.findAllSongLists(userId);
		List<Songlist> visibleSonglists = new ArrayList<Songlist>();
		for (Songlist songlist : songlists) {
			if (isVisible(songlist, authHeader, userId)) {
				visibleSonglists.add(songlist);
			}
		}
		GenericEntity<Collection<Songlist>> entity = new GenericEntity<Collection<Songlist>>(visibleSonglists) {
		};
		return Response.ok(entity).build();
	}

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getSongList(@PathParam("id") Integer id, @HeaderParam("Authorization") String authHeader) {
		if (songListDI.findSongListById(id) != null) {
			if (isVisible(songListDI.findSongListById(id), authHeader, "no User")) {
				return Response.ok(songListDI.findSongListById(id)).build();
			} else {
				return Response.status(Response.Status.FORBIDDEN).build();
			}
		} else {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

	}

	@POST
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces(MediaType.TEXT_PLAIN)
	public Response createSongList(@Valid Songlist songList) {
		List<Song> songs = songList.getSongs();
		List<Song> validSongs = new ArrayList<>();
		for (Song s : songs) {
			if (s.getId() != null || s != null) {
				validSongs.add(s);
			}
		}

		songList.setSongs(validSongs);
		int newId = songListDI.saveSongList(songList);
		UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
		uriBuilder.path(Integer.toString(newId));
		return Response.created(uriBuilder.build()).build();
	}

	@DELETE
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/{id}")
	public Response deleteSongList(@PathParam("id") Integer id, @HeaderParam("Authorization") String authHeader) {
		Songlist songlist = songListDI.findSongListById(id);
		if (!isVisible(songlist, authHeader, "no Users")) {
			return Response.status(Status.UNAUTHORIZED).build();
		}

		songListDI.deleteSongList(id);
		return Response.status(Status.NO_CONTENT).build();
	}

	public boolean isVisible(Songlist songlist, String authHeader, String userId) {
		if (songlist != null) {
			if (userId.equals(UserWebService.getUserFromToken(authHeader))) {
				return true;
			} else {
				return !songlist.isPrivate()
						|| songlist.getUser().getUserId().equals(UserWebService.getUserFromToken(authHeader));
			}
		} else {
			return false;
		}
	}

}
