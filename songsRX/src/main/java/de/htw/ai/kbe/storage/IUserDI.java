package de.htw.ai.kbe.storage;

import java.util.Collection;
import java.util.List;

import de.htw.ai.kbe.bean.Song;
import de.htw.ai.kbe.bean.User;

public interface IUserDI {
	public User getUser(String userId);
	public Collection<User> getAllUsers();
}
