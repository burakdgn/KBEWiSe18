package de.htw.ai.kbe.songServlet;

import java.util.List;

public class App {

	public static void main(String[] args) {

		try {
			List<Song> readSongs = SongServlet.readJSONToSongs("outJacksonSongs.json");
			readSongs.forEach(s -> {
				System.out.println(s.toString());
			});
			 SongServlet.writeSongsToJSON(readSongs, "outJacksonSongs.json");
		} catch (Exception e) { // Was stimmt hier nicht?
		}

	}

}
