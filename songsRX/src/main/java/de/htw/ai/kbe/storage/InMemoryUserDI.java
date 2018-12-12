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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.htw.ai.kbe.bean.Song;
import de.htw.ai.kbe.bean.User;

public class InMemoryUserDI implements IUserDI {

	private static Map<Integer, User> storage;
	private final static String JSON_SOURCE = "/user.json";

	public InMemoryUserDI() {
		initSomeUsers(getFile());
	}

	private static void initSomeUsers(List<User> userList) {
		storage = Collections.synchronizedMap(new HashMap<>());
		if (userList == null) {
			userList = new ArrayList<>();
		}
		userList.stream().filter(s -> s.getId() != null).sorted(Comparator.comparing(User::getId))
				.forEach(s -> storage.put(s.getId(), s));
	}

	public static List<User> getFile() {
		List<User> users = new ArrayList<>();
		ObjectMapper objectMapper = new ObjectMapper();
		try (InputStream inputStream = InMemoryUserDI.class.getResourceAsStream(JSON_SOURCE)) {
			users = objectMapper.readValue(inputStream, new TypeReference<List<User>>() {
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

		return users;

	}

	@Override
	public Boolean getUser(String userId) {
		for (User u : storage.values()) {
			if (u.getUserId().equals(userId)) {
				return true;
			}
		}

		return false;
	}

	public Collection<User> getAllUsers() {
		return storage.values();
	}	
}
