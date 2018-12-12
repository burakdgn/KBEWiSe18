package de.htw.ai.kbe.storage;

import java.util.Collection;

import de.htw.ai.kbe.bean.Song;
import de.htw.ai.kbe.bean.User;

public interface IUserDI {
	public Boolean getUser(String userId);
	public Collection<User> getAllUsers();	
}
