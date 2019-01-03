
package de.htw.ai.kbe.di;

import javax.inject.Singleton;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import de.htw.ai.kbe.storage.ISongDI;
import de.htw.ai.kbe.storage.IUserDI;
import de.htw.ai.kbe.storage.InMemorySongDI;
import de.htw.ai.kbe.storage.InMemoryUserDI;


public class DependencyBinder extends AbstractBinder{

	@Override
	protected void configure() {
		// TODO Auto-generated method stub
		bind (Persistence.createEntityManagerFactory("songDB")).to(EntityManagerFactory.class);
		bind(InMemorySongDI.class).to(ISongDI.class).in(Singleton.class);
		bind(InMemoryUserDI.class).to(IUserDI.class).in(Singleton.class);
	}

}
