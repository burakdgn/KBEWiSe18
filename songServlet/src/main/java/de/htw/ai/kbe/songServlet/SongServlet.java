package de.htw.ai.kbe.songServlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class SongServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private String pathToJSON = null;
	
	private static Integer idCounter = 10;
	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		// Beispiel: Laden eines Konfigurationsparameters aus der web.xml
		this.pathToJSON = servletConfig.getInitParameter("pathToJSON");

	}

	protected String getJSON() {
		return this.pathToJSON;
	}

	// Reads a list of songs from a JSON-file into List<Song>
	@SuppressWarnings("unchecked")
	private static synchronized List<Song> readJSONToSongs(String filename) throws FileNotFoundException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		try (InputStream is = new BufferedInputStream(new FileInputStream(filename))) {
			return (List<Song>) objectMapper.readValue(is, new TypeReference<List<Song>>() {
			});
		}
	}

	// Write a List<Song> to a JSON-file
	private static synchronized void writeSongsToJSON(List<Song> songs, String filename)
			throws FileNotFoundException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		try (OutputStream os = new BufferedOutputStream(new FileOutputStream(filename))) {
			objectMapper.writeValueAsString(songs);
			objectMapper.writeValue(os, songs);
		}
	}

	private static synchronized List<Song> addSong(Song song, List<Song> songs) {
		song.setId(++idCounter);
		songs.add(song);
		
		return songs;
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// alle Parameter (keys)
		Enumeration<String> paramNames = request.getParameterNames();

		String responseStr = "";
		String param = "";

		if (request.getHeader("Accept").equals("*") || request.getHeader("Accept").equals("application/json")
				|| request.getHeader("Accept").equals("*/*")) {
			while (paramNames.hasMoreElements()) {
				param = paramNames.nextElement();
				if (param.equals("all")) {
					responseStr += "Alle Songs des JSON File aus dem Pfad" + pathToJSON + " werden ausgegegeben:"
							+ "\n\n";
					List<Song> songs = readJSONToSongs(pathToJSON);

					for (Song s : songs) {
						responseStr += new Gson().toJson(s);
					}
				} else if (param.equals("songId")) {
					List<Song> songs = readJSONToSongs(pathToJSON);
					String songId = request.getParameter(param);
					if (!songId.equals("")) {
						for (Song s : songs) {
							if (s.getId().toString().equals(songId)) {
								responseStr = new Gson().toJson(s);
							}
						}

						if (responseStr.equals("")) {
							response.setStatus(HttpServletResponse.SC_NOT_FOUND);
							responseStr += "Die von Ihnen angegebene songID existiert nicht.\n" + response.getStatus()
									+ " " + HttpStatus.getStatusText(response.getStatus()) + "\n";
						}
					} else {
						response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						responseStr += "Bitte geben Sie in der URL nach dem Parameter songId eine ID an bspw songId=6\n"
								+ response.getStatus() + " " + HttpStatus.getStatusText(response.getStatus()) + "\n";
					}
				} else {
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					responseStr += "Bitte geben Sie in der URL einen richtigen Parameter an bspw. songsServlet?all oder songsServlet?songId=6\n"
							+ response.getStatus() + " " + HttpStatus.getStatusText(response.getStatus()) + "\n";

				}
			}
		} else {
			response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
			responseStr += "Accept Header falsch.\n" + response.getStatus() + " "
					+ HttpStatus.getStatusText(response.getStatus()) + "\n";
		}

		response.setContentType("text/plain");
		try (PrintWriter out = response.getWriter()) {
			// responseStr += "\n Your request will be sent to " + pathToJSON;
			out.println(responseStr);
		}

	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();

		ServletInputStream inputStream = request.getInputStream();
		byte[] inBytes = IOUtils.toByteArray(inputStream);

		try (PrintWriter out = response.getWriter()) {
			String payload = new String(inBytes);
			if (request.getContentType().equals("application/json")) {
				if (payload.equals("") || payload == null) {
					out.println("Die JSON ist leer es wird kein neuer Song erzeugt");
				} else {
					if (isJSONValid(payload)) {
						Song song = objectMapper.readValue(payload, Song.class);

						List<Song> songs = readJSONToSongs(pathToJSON);
						songs = addSong(song, songs);
						String url = "http://localhost:8080/songsServlet?songId=" + song.getId();
						writeSongsToJSON(songs, pathToJSON);
						response.addHeader("location", url);

					} else {
						response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
						out.println("Dateiinhalt nicht im JSON-Format.\n" + response.getStatus() + " "
								+ HttpStatus.getStatusText(response.getStatus()) + "\n");
					}
				}
			} else {
				out.println("Eine Datei mit falschem content-type wurde gepostet");
			}
		}
	}

	private static boolean isJSONValid(String jsonInString) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			mapper.readTree(jsonInString);
			return true;
		} catch (IOException e) {
			return false;
		}
	}

}