package de.htw.ai.kbe.storage;

import java.util.Collection;
import de.htw.ai.kbe.bean.Song;

public interface ISongDI {

	public Song getSong(Integer id); 
	public Collection<Song> getAllSongs(); 
	public Integer addSong(Song song); 
	public boolean updateSong(Song song); 
	public Song deleteSong(Integer id);
	
	
}
