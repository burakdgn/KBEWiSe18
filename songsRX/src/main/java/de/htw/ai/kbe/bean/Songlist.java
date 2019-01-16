
package de.htw.ai.kbe.bean;

import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@XmlRootElement(name = "songlist")
@XmlAccessorType(XmlAccessType.FIELD)
public class Songlist {
	@Id // kennzeichnet das Identitätsattribut entspricht dem PK (primary key)
	@GeneratedValue(strategy = GenerationType.IDENTITY) // bedeutet, dass der PK automatisch durch die DB vergeben wird
	private int id;

	private boolean isPrivate;
	
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;

	private String name;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "Songlist_Song", joinColumns = {@JoinColumn(name = "songlistId")}, inverseJoinColumns = {@JoinColumn(name = "songId")})
	@XmlElementWrapper(name = "songs")
	@XmlElement(name = "song")
	@JsonProperty(value = "songs")
	private List<Song> songs;

	public Songlist() {

	}

	public Songlist(User user, boolean isPrivate, List<Song> songs,String name) {
		this.user = user;
		this.isPrivate = isPrivate;
		this.songs = songs;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isPrivate() {
		return isPrivate;
	}

	public void setPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Song> getSongs() {
		return songs;
	}

	public void setSongs(List<Song> songs) {
		this.songs = songs;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "songlist [id=" + id + ", isPrivate=" + isPrivate + ", user=" + user + ", name=" + name + ", songs="
				+ songs + "]";
	}

	

}
