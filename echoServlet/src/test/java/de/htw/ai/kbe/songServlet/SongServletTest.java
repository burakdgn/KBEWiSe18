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

    private final static String JSON_STRING = "/home/rechner/Dokumente/songs.json";
    
    @Before
    public void setUp() throws ServletException {
        servlet = new SongServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        config = new MockServletConfig();
        config.addInitParameter("pathToJSON", JSON_STRING);
        servlet.init(config); //throws ServletException
    }

    @Test
    public void initShouldSetJSON() {
    	assertEquals(JSON_STRING, servlet.getJSON());	
    	
    }

    @Test
    public void doGetShouldShowSongWithID() throws ServletException, IOException {
    	
    	  request.addParameter("songId" ,"6");
    	  
    	  servlet.doGet(request, response);
    	  
    	  assertEquals("text/plain", response.getContentType());    	
    	  assertTrue(response.getContentAsString().contains("Song [id=6, title=I Took a Pill in Ibiza, artist=Mike Posner, album=At Night, Alone., released=2016]"));
    	  assertTrue(response.getContentAsString().contains(JSON_STRING)); 
    	  //  System.out.println(response.getContentAsString());

    	
    }
    
    @Test
    public void doGetShouldShowErrorMessageForEmptySongRequestParam() throws ServletException, IOException {
    	
    	  request.addParameter("songId" ,"");
    	
    	  servlet.doGet(request, response);
    	  
    	  assertEquals("text/plain", response.getContentType());    	
    	  assertTrue(response.getContentAsString().contains("Bitte geben sie in der URL nach dem Parameter songId eine ID an bspw songId=6"));
    	  assertTrue(response.getContentAsString().contains(JSON_STRING)); 
  

    	
    }
    
    
    @Test
    public void doGetShouldShowErrorMessageForNonExistentSongParam() throws ServletException, IOException {
    	
    	  request.addParameter("songId" ,"25");
    	
    	  servlet.doGet(request, response);
    	  
    	  assertEquals("text/plain", response.getContentType());    	
    	  assertTrue(response.getContentAsString().contains("Die von ihnen angegebene songID existiert nicht."));
    	  assertTrue(response.getContentAsString().contains(JSON_STRING)); 
    
    	
    }
    
    @Test
    public void doGetShouldShowAllSongs() throws ServletException, IOException {
    	
      request.addParameter("all");
  	 
      servlet.doGet(request, response);
  	  
      assertEquals("text/plain", response.getContentType());    	
      assertTrue(response.getContentAsString().contains("Song [id=10, title=7 Years, artist=Lukas Graham, album=Lukas Graham (Blue Album), released=2015]"));
      assertTrue(response.getContentAsString().contains("Song [id=5, title=Bad Things, artist=Camila Cabello, Machine Gun Kelly, album=Bloom, released=2017]"));
      assertTrue(response.getContentAsString().contains("Song [id=1, title=Can?t Stop the Feeling, artist=Justin Timberlake, album=Trolls, released=2016]"));
  	  assertTrue(response.getContentAsString().contains(JSON_STRING)); 
    	
    	
    	
    }
    
    @Test
    public void doPostShoulSaveNewSong() {}
    
}
