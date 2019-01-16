package de.htw.ai.kbe.storage;

import java.util.Collection;

import de.htw.ai.kbe.bean.Song;
import de.htw.ai.kbe.bean.Songlist;

public interface ISongListDI {
	public Songlist findSongListById(Integer id); 
	public Collection<Songlist> findAllSongLists(String userId); 
	public Integer saveSongList(Songlist songList); 
	public void deleteSongList(Integer id); 
}
