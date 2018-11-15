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
        servlet.init(config); //throws ServletException
    }

    @Test
    public void initShouldSetJSON() {
    	assertEquals(JSON_STRING, servlet.getJSON());	
    	
    }

    @Test
    public void doGetShouldShowSongWithID() {}
    
    @Test
    public void doGetShouldShowAllSongs() {}
    
    @Test
    public void doPostShoulSaveNewSong() {}
    
}
