package de.htw.ai.kbe.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;



@Entity
public class Song {

	
	@Id //kennzeichnet das Identitätsattribut entspricht dem PK (primary key)
	@GeneratedValue(strategy = GenerationType.IDENTITY)//bedeutet, dass der PK automatisch durch die DB vergeben wird
	private int id;
	
	private String title;
	private String artist;
	private String album;
	private String released;
	
	public Song() {}

	public Song(String title, String artist, String album, String released) {
	this.title = title;
	this.artist = artist;
	this.album = album;
	this.released = released;
	
	
	}
	
	public Song(String title, String artist,String released) {
	this.title = title;
	this.artist = artist;
	this.released = released;
	
	
	}

	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}

	
	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	
	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getReleased() {
		return released;
	}


	public void setReleased(String released) {
		this.released = released;
	}

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", album='" + album + '\'' +
                ", released=" + released +
                '}';
    }

}

