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

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.htw.ai.kbe.bean.Song;

public class InMemorySongDI implements ISongDI {

	private static Map<Integer, Song> storage;
	private final static String JSON_SOURCE = "/songs.json";
	private static int idCounter = 0;

	public InMemorySongDI() {
		initSomeSongs(getFile());
	}

	private static void initSomeSongs(List<Song> songList) {
		storage = Collections.synchronizedMap(new HashMap<>());
		if (songList == null) {
			songList = new ArrayList<>();
		}
		songList.stream().filter(s -> s.getId() != null).sorted(Comparator.comparing(Song::getId))
				.forEach(s -> storage.put(s.getId(), s));
		for(Song s : storage.values()){
            if(s.getId() > idCounter)
                idCounter = s.getId();
        }
	}

	public synchronized static List<Song> getFile() {
		List<Song> songs = new ArrayList<>();
		ObjectMapper objectMapper = new ObjectMapper();
		try (InputStream inputStream = InMemorySongDI.class.getResourceAsStream(JSON_SOURCE)) {
			songs = objectMapper.readValue(inputStream, new TypeReference<List<Song>>() {
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

		return songs;

	}

	@Override
	public synchronized Collection<Song> getAllSongs() {
		return storage.values();
	}

	@Override
	public synchronized Song getSong(Integer id) {
		return storage.get(id);

	}

	@Override
	public synchronized Integer addSong(Song song) {
		song.setId(++idCounter);
		storage.put(song.getId(), song);
		return song.getId();
	}

	@Override
	public synchronized Song updateSong(Song song, Integer id) {

		if (storage.containsKey(id)) {
			song.setId(id);
			storage.put(song.getId(), song);

		}
		return song;
	}

	@Override
	public synchronized Song deleteSong(Integer id) {
		return storage.remove(id);
	}

	public static Song contentToSong(String content) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			Song song = objectMapper.readValue(content, Song.class);
			return song;
		} catch (IOException e) {

		}

		try {
			JAXBContext context = JAXBContext.newInstance(Song.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			Song song = (Song) unmarshaller.unmarshal(new StringReader(content));
			return song;
		} catch (Exception e) {

		}
		return null;
	}
}
