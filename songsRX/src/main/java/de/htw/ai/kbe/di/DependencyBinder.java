
package de.htw.ai.kbe.di;

import javax.inject.Singleton;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import de.htw.ai.kbe.storage.ISongDI;
import de.htw.ai.kbe.storage.InMemorySongDI;


public class DependencyBinder extends AbstractBinder{

	@Override
	protected void configure() {
		// TODO Auto-generated method stub
		bind(InMemorySongDI.class).to(ISongDI.class).in(Singleton.class);
		
	}

}
