package de.htw.ai.kbe.services;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;

import org.junit.Test;

import de.htw.ai.kbe.api.SongsWebService;
import de.htw.ai.kbe.bean.Song;
import de.htw.ai.kbe.storage.ISongDI;
import de.htw.ai.kbe.storage.InMemorySongDI;

import static org.junit.Assert.assertEquals;

import javax.inject.Singleton;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.HttpHeaders;

import javax.ws.rs.core.Response;

public class SongsRXWebServiceTest extends JerseyTest {

	@Override
	protected Application configure() {
		return new ResourceConfig(SongsWebService.class).register(new AbstractBinder() {

			@Override
			protected void configure() {
				bind(InMemorySongDI.class).to(ISongDI.class).in(Singleton.class);

			}
		});
	}

	/*
	// Testcases für Put
	@Test
	public void putWithNonExistingIdXML() {
		Song s = new Song.Builder("New Song").artist("New Artist").album("new Album").released("12/12/2012").build();
		Response response = target("/songs/666").request().header(HttpHeaders.AUTHORIZATION, "test").put(Entity.xml(s));
		assertEquals(400, response.getStatus());
	}

	@Test
	public void putWithNonExistingIdJSON() {
		Song s = new Song.Builder("New Song").artist("New Artist").album("new Album").released("12/12/2012").build();
		Response response = target("/songs/666").request().header(HttpHeaders.AUTHORIZATION, "test")
				.put(Entity.json(s));
		assertEquals(400, response.getStatus());
	}

	@Test
	public void putWithExistingIdXML() {
		Song s = new Song.Builder("New Song").artist("New Artist").album("new Album").released("12/12/2012").id(1)
				.build();

		Response response = target("/songs/1").request().header(HttpHeaders.AUTHORIZATION, "test").put(Entity.xml(s));
		assertEquals(204, response.getStatus());
	}

	@Test
	public void putWithExistingIdJSON() {
		Song s = new Song.Builder("New Song").artist("New Artist").album("new Album").released("12/12/2012").id(1)
				.build();

		Response response = target("/songs/1").request().header(HttpHeaders.AUTHORIZATION, "test").put(Entity.json(s));
		assertEquals(204, response.getStatus());
	}

	/*
	@Test
	public void putWithoutAuthorizationXML() {
		Song s = new Song.Builder("New Song").artist("New Artist").album("new Album").released("12/12/2012").id(1)
				.build();
		Response response = target("/songs/1").request().header(HttpHeaders.AUTHORIZATION, "").put(Entity.xml(s));
		assertEquals(401, response.getStatus());
	}

	@Test
	public void putWithoutAuthorizationJSON() {
		Song s = new Song.Builder("New Song").artist("New Artist").album("new Album").released("12/12/2012").id(1)
				.build();
		Response response = target("/songs/1").request().header(HttpHeaders.AUTHORIZATION, "").put(Entity.json(s));
		assertEquals(401, response.getStatus());
	}
	*/
	

}
