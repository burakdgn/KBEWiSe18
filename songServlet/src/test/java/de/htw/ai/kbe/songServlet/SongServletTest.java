package de.htw.ai.kbe.songServlet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import de.htw.ai.kbe.songServlet.SongServlet;

public class SongServletTest {

	private SongServlet servlet;
	private MockServletConfig config;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;

	private final static String JSON_STRING = "/home/s0560390/Dokumente/songs.json";

	@Before
	public void setUp() throws ServletException {
		servlet = new SongServlet();
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		config = new MockServletConfig();
		config.addInitParameter("pathToJSON", JSON_STRING);
		servlet.init(config); // throws ServletException
		request.addHeader("Accept", "*");
	}

	@Test
	public void initShouldSetJSON() {
		assertEquals(JSON_STRING, servlet.getJSON());

	}

	@Test
	public void doGetShouldShowSongWithID() throws ServletException, IOException {
		request.addParameter("songId", "6");
		servlet.doGet(request, response);

		assertEquals("text/plain", response.getContentType());
		assertTrue(response.getContentAsString().contains(
				"{\"id\":6,\"title\":\"I Took a Pill in Ibiza\",\"artist\":\"Mike Posner\",\"album\":\"At Night, Alone.\",\"released\":2016}"));
		// assertTrue(response.getContentAsString().contains(JSON_STRING));

	}

	@Test
	public void doGetShouldShowErrorMessageForEmptySongRequestParam() throws ServletException, IOException {
		request.addParameter("songId", "");
		servlet.doGet(request, response);

		assertEquals("text/plain", response.getContentType());
		assertTrue(response.getContentAsString()
				.contains("Bitte geben Sie in der URL nach dem Parameter songId eine ID an bspw songId=6"));
		assertTrue(response.getContentAsString().contains("400 Bad Request"));
		// assertTrue(response.getContentAsString().contains(JSON_STRING));
	}

	@Test
	public void doGetShouldShowErrorMessageForNonExistentSongParam() throws ServletException, IOException {

		request.addParameter("songId", "25");

		servlet.doGet(request, response);

		assertEquals("text/plain", response.getContentType());
		assertTrue(response.getContentAsString().contains("Die von Ihnen angegebene songID existiert nicht."));
		assertTrue(response.getContentAsString().contains("404 Not Found"));
		// assertTrue(response.getContentAsString().contains(JSON_STRING));

	}

	@Test
	public void doGetShouldShowAllSongs() throws ServletException, IOException {

		request.addParameter("all");

		servlet.doGet(request, response);
		assertEquals("text/plain", response.getContentType());
		assertTrue(response.getContentAsString().contains(
				"{\"id\":10,\"title\":\"7 Years\",\"artist\":\"Lukas Graham\",\"album\":\"Lukas Graham (Blue Album)\",\"released\":2015}"
						+ "{\"id\":9,\"title\":\"Private Show\",\"artist\":\"Britney Spears\",\"album\":\"Glory\",\"released\":2016}"
						+ "{\"id\":8,\"title\":\"No\",\"artist\":\"Meghan Trainor\",\"album\":\"Thank You\",\"released\":2016}"
						+ "{\"id\":7,\"title\":\"i hate u, i love u\",\"artist\":\"Gnash\",\"album\":\"Top Hits 2017\",\"released\":2017}"
						+ "{\"id\":6,\"title\":\"I Took a Pill in Ibiza\",\"artist\":\"Mike Posner\",\"album\":\"At Night, Alone.\",\"released\":2016}"
						+ "{\"id\":5,\"title\":\"Bad Things\",\"artist\":\"Camila Cabello, Machine Gun Kelly\",\"album\":\"Bloom\",\"released\":2017}"
						+ "{\"id\":4,\"title\":\"Ghostbusters (I\\u0027m not a fraid)\",\"artist\":\"Fall Out Boy, Missy Elliott\",\"album\":\"Ghostbusters\",\"released\":2016}"
						+ "{\"id\":3,\"title\":\"Team\",\"artist\":\"Iggy Azalea\",\"released\":2016}"
						+ "{\"id\":2,\"title\":\"Mom\",\"artist\":\"Meghan Trainor, Kelli Trainor\",\"album\":\"Thank You\",\"released\":2016}"
						+ "{\"id\":1,\"title\":\"Can?t Stop the Feeling\",\"artist\":\"Justin Timberlake\",\"album\":\"Trolls\",\"released\":2016}"));

		assertTrue(response.getContentAsString().contains(JSON_STRING));
	}

	@Test
	public void doGetShouldShowIDWithoutAcceptHeader() throws ServletException, IOException {
		request.addParameter("songId", "6");
		request.addHeader("Accept", "*/*");

		servlet.doGet(request, response);
		assertEquals("text/plain", response.getContentType());

		assertEquals("text/plain", response.getContentType());
		assertTrue(response.getContentAsString().contains(
				"{\"id\":6,\"title\":\"I Took a Pill in Ibiza\",\"artist\":\"Mike Posner\",\"album\":\"At Night, Alone.\",\"released\":2016}"));
	}

	@Test
	public void doGetShouldShowIDWithAcceptHeaderApplicationJSON() throws ServletException, IOException {
		request.addParameter("songId", "6");
		request.addHeader("Accept", "application/json");

		servlet.doGet(request, response);
		assertEquals("text/plain", response.getContentType());

		assertEquals("text/plain", response.getContentType());
		assertTrue(response.getContentAsString().contains(
				"{\"id\":6,\"title\":\"I Took a Pill in Ibiza\",\"artist\":\"Mike Posner\",\"album\":\"At Night, Alone.\",\"released\":2016}"));
	}

	@Test
	public void doGetShouldShowIDWithHeaderStar() throws ServletException, IOException {
		request.addParameter("songId", "6");
		request.addHeader("Accept", "*");

		servlet.doGet(request, response);
		assertEquals("text/plain", response.getContentType());

		assertEquals("text/plain", response.getContentType());
		assertTrue(response.getContentAsString().contains(
				"{\"id\":6,\"title\":\"I Took a Pill in Ibiza\",\"artist\":\"Mike Posner\",\"album\":\"At Night, Alone.\",\"released\":2016}"));
	}

	@Test
	public void doGetShouldShowErrorMessageWithWrongParam() throws ServletException, IOException {
		request.addParameter("wrongParamater");
		request.addHeader("Accept", "*");

		servlet.doGet(request, response);
		assertEquals("text/plain", response.getContentType());

		assertEquals("text/plain", response.getContentType());
		assertTrue(response.getContentAsString().contains(
				"Bitte geben Sie in der URL einen richtigen Parameter an bspw. songsServlet?all oder songsServlet?songId=6\n"));
	}

	@Test
	public void doPostShouldSaveNewSong() throws ServletException, IOException {
		String payload = "{\"id\":700,\"title\":\"7 Years\",\"artist\":\"Lukas Graham\",\"album\":\"Lukas Graham (Blue Album)\",\"released\":2015}";
		request.setContent(payload.getBytes());
		request.setContentType("application/json");

		servlet.doPost(request, response);

		assertTrue(response.getHeader("location").contains("http://localhost:8080/songsServlet?songId="));

	}

	@Test
	public void doPostShouldShowErrorMessageWrongContentType() throws ServletException, IOException {
		String payload = "{\"id\":10,\"title\":\"7 Years\",\"artist\":\"Lukas Graham\",\"album\":\"Lukas Graham (Blue Album)\",\"released\":2015}";
		request.setContent(payload.getBytes());
		request.setContentType("application/xml");

		servlet.doPost(request, response);

		assertTrue(response.getContentAsString().contains("Eine Datei mit falschem content-type wurde gepostet"));

	}

	@Test
	public void doPostShouldShowErrorMessageEmptyPayload() throws ServletException, IOException {
		String payload = "";
		request.setContent(payload.getBytes());
		request.setContentType("application/json");
		servlet.doPost(request, response);

		assertTrue(response.getContentAsString().contains("Die JSON ist leer es wird kein neuer Song erzeugt"));

	}

	@Test
	public void doPostShouldShowErrorMessageWrongFormat() throws ServletException, IOException {
		String payload = "ichbinnichtimJSONFormat";
		request.setContent(payload.getBytes());
		request.setContentType("application/json");

		servlet.doPost(request, response);

		assertTrue(response.getContentAsString().contains("Dateiinhalt nicht im JSON-Format."));

	}

}
