package de.htw.ai.kbe.storage;

import java.util.Collection;
import java.util.List;

import de.htw.ai.kbe.bean.Song;

public interface ISongDI {

	public Song findSongById(Integer id); 
	public Collection<Song> findAllSongs(); 
	public Integer saveSong(Song song); 
	public void updateSong(Song song, Integer id); 

	
	
	
	
}
