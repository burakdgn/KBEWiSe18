package de.htw.ai.kbe.services;

import static org.junit.Assert.assertEquals;

import javax.inject.Singleton;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;


import de.htw.ai.kbe.storage.IUserDI;
import de.htw.ai.kbe.storage.InMemoryUserDI;

public class UserWebServiceTest extends JerseyTest{
	
	@Override
	protected Application configure() {
		return new ResourceConfig(UserWebService.class).register(new AbstractBinder() {

			@Override
			protected void configure() {
				bind(InMemoryUserDI.class).to(IUserDI.class).in(Singleton.class);

			}
		});
	}

	
	@Test
	public void getTokenWithNonExisitingUserId() {
		Response response = target("/auth").queryParam("userId", "NonExisitingUser").request().header(HttpHeaders.AUTHORIZATION, "test").get();
		assertEquals(403, response.getStatus());
	}

	@Test
	public void getTokenWithExisitingUserId() {
		Response response = target("/auth").queryParam("userId", "mmuster").request().header(HttpHeaders.AUTHORIZATION, "test").get();
		assertEquals(200, response.getStatus());
	}
	
}
