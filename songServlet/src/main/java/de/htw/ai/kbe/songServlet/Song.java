package de.htw.ai.kbe.songServlet;

public class Song {

    private Integer id;
	private String title;
	private String artist;
	private String album;
	private Integer released;

	public Song() { 
	}

	public Song(String title, String artist, String album, Integer released) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.released = released;
    }

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getArtist() {
		return artist;
	}
	
	public String getAlbum() {
		return album;
	}
	
	public Integer getReleased() {
		return released;
	}

    @Override
    public String toString() {
        return "Song [id=" + id + ", title=" + title + ", artist=" + artist + ", album=" + album + ", released="
                + released + "]";
    }
	
}
