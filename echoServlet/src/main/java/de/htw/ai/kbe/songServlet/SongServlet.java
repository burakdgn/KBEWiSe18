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
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SongServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private String pathToJSON = null;

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
	static List<Song> readJSONToSongs(String filename) throws FileNotFoundException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		try (InputStream is = new BufferedInputStream(new FileInputStream(filename))) {
			return (List<Song>) objectMapper.readValue(is, new TypeReference<List<Song>>() {
			});
		}
	}

	// Write a List<Song> to a JSON-file
	static void writeSongsToJSON(List<Song> songs, String filename) throws FileNotFoundException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		try (OutputStream os = new BufferedOutputStream(new FileOutputStream(filename))) {
			objectMapper.writeValueAsString(songs);
			objectMapper.writeValue(os, songs);
		}
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// alle Parameter (keys)
		Enumeration<String> paramNames = request.getParameterNames();

		String responseStr = "";
		String param = "";
		while (paramNames.hasMoreElements()) {
			param = paramNames.nextElement();

			if (param.equals("all")) {

				responseStr += "Alle Songs des JSON File aus dem Pfad" + pathToJSON + " werden ausgegegeben:" + "\n\n";
				List<Song> songs = readJSONToSongs(pathToJSON);

				for (Song s : songs) {

					responseStr += s.toString() + "\n";

				}

			}

			if (param.equals("songId")) {

				List<Song> songs = readJSONToSongs(pathToJSON);
				String songId = request.getParameter(param);

				if (!songId.equals("")) {

					for (Song s : songs) {

						if (s.getId().toString().equals(songId)) {

							responseStr += s.toString() + "\n";

						}
					}

					if (responseStr.equals("")) {

						responseStr += "Die von ihnen angegebene songID existiert nicht.\n";

					}
				} else {

					responseStr += "Bitte geben sie in der URL nach dem Parameter songId eine ID an bspw songId=6\n";

				}

			}
		}
		response.setContentType("text/plain");
		try (PrintWriter out = response.getWriter()) {
			responseStr += "\n Your request will be sent to " + pathToJSON;
			out.println(responseStr);
		}
	}
}


//wichtige Befehle 
/*
 * mvn clean package jar -vft target/songServlet.war cp target/songServlet.war
 * jar -vft target/songServlet.war
 * /home/s0560390/Dokumente/apache-tomcat-9.0.13/webapps
 * 
 */

//Seiten URL
/*
 * Alle Songs 
 * http://localhost:8080/songServlet/songServlet?all
 * 
 * Einzelner Song
 * localhost:8080/songServlet/songServlet?songId=6
 * 
 */

//Pfade in den folgenden Files verändert
/*
 * web.xml - den init parameter value zu JSON TestKlasse den Pfad zu JSON
 * SongServlet - den JSON_String
 * 
 * 
 * Was ich heute machen will SongServletTest für alle Songs und für einen
 * einzelnen Song SongServlet - einen Song mit ID erkennen -> vllt noch den HTTP
 * StatusCodes auseinander setzen
 * 
 * 
 * 
 * 
 * 
 */
