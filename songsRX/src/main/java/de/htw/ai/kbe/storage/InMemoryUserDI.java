package de.htw.ai.kbe.storage;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.htw.ai.kbe.bean.Song;
import de.htw.ai.kbe.bean.User;

public class InMemoryUserDI implements IUserDI {

	private EntityManagerFactory emf;

	@Inject
	public InMemoryUserDI(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Override
	public User getUser(String userId) {

		EntityManager em = emf.createEntityManager();
		userId = "'" + userId + "'";
		try {
			Query q = em.createQuery("SELECT u FROM User u where userId = " + userId);
			List<User> u = q.getResultList();
			if (u.isEmpty()) {
				return null;
			} else {
				return u.get(0);
			}

		} finally {
			em.close();
		}

	}

	public synchronized Collection<User> getAllUsers() {

		EntityManager em = emf.createEntityManager();
		try {
			TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
			return query.getResultList();
		} finally {
			em.close();
		}

	}
}
