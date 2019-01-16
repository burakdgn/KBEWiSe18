package de.htw.ai.kbe.storage;

import java.util.Collection;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import de.htw.ai.kbe.bean.Song;
import de.htw.ai.kbe.bean.Songlist;
import de.htw.ai.kbe.bean.User;

public class DBSongList implements ISongListDI {

	private EntityManagerFactory emf;

	@Inject
	private IUserDI userDI;

	@Inject
	public DBSongList(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Override
	public Songlist findSongListById(Integer id) {
		EntityManager em = emf.createEntityManager();
		Songlist entity = null;
		try {
			entity = em.find(Songlist.class, id);

		} finally {
			em.close();
		}

		return entity;
	}

	@Override
	public Collection<Songlist> findAllSongLists(String userId) {
		EntityManager em = emf.createEntityManager();
		try {
			String userString = "'" + userId + "'";
			TypedQuery<Songlist> query = em.createQuery("SELECT s FROM Songlist s WHERE user = " + userString,
					Songlist.class);
			return query.getResultList();
		} finally {
			em.close();
		}

	}

	@Override
	public Integer saveSongList(Songlist songList) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			Songlist songListToPost = new Songlist(songList.getUser(), songList.isPrivate(), songList.getSongs(),
					songList.getName());
			em.persist(songListToPost);
			transaction.commit();
			return songListToPost.getId();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error adding SongList: " + e.getMessage());
			transaction.rollback();
			throw new PersistenceException("Could not persist entity: " + e.toString());
		} finally {
			em.close();
		}

	}

	@Override
	public void deleteSongList(Integer id) {
		Songlist songlist;
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			songlist = em.find(Songlist.class, id);
			em.remove(songlist);
			em.getTransaction().commit();
		} finally {
			em.close();
		}

	}

}
