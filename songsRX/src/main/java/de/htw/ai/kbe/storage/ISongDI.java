package de.htw.ai.kbe.storage;

import java.util.Collection;
import de.htw.ai.kbe.bean.Song;

public interface ISongDI {

	public Song getSong(Integer id); 
	public Collection<Song> getAllSongs(); 
	public Integer addSong(Song song); 
	public Song updateSong(Song song, Integer id); 
	public Song deleteSong(Integer id);
	
	
}
