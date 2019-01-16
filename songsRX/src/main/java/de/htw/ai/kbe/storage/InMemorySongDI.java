package de.htw.ai.kbe.storage;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.htw.ai.kbe.bean.Song;
import de.htw.ai.kbe.bean.User;

public class InMemorySongDI implements ISongDI {

	private EntityManagerFactory emf;

	@Inject
	public InMemorySongDI(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Override
	public synchronized Collection<Song> findAllSongs() {

		EntityManager em = emf.createEntityManager();
		try {
			TypedQuery<Song> query = em.createQuery("SELECT s FROM Song s", Song.class);
			return query.getResultList();
		} finally {
			em.close();
		}

	}

	@Override
	public synchronized Song findSongById(Integer id) {
		EntityManager em = emf.createEntityManager();
		Song entity = null;
		try {
			entity = em.find(Song.class, id);
			
		} finally {
			em.close();
		}
		
		return entity;

	}

	@Override
	public Integer saveSong(Song song) {

		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			// MUST set the contact in every address
			// Song songPOST = song;
			Song songPOST = new Song(song.getTitle(), song.getArtist(), song.getAlbum(), song.getReleased());
			em.persist(songPOST);
			transaction.commit();
			return songPOST.getId();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error adding contact: " + e.getMessage());
			transaction.rollback();
			throw new PersistenceException("Could not persist entity: " + e.toString());
		} finally {
			em.close();
		}

	}

	@Override
	public void updateSong(Song song, Integer id) {
	
		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		
	
		try {
			transaction.begin();
			Song entity = em.find(Song.class, id);
			
			
			entity.setTitle(song.getTitle());
			entity.setArtist(song.getArtist());
			entity.setAlbum(song.getAlbum());
			entity.setReleased(song.getReleased());

			transaction.commit();
			

		} finally {
			em.close();
		}
		
		
	}

}
