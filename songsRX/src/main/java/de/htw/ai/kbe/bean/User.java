package de.htw.ai.kbe.bean;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;




@Entity
public class User {

	@Id //kennzeichnet das Identitätsattribut entspricht dem PK (primary key)
	@GeneratedValue(strategy = GenerationType.IDENTITY)//bedeutet, dass der PK automatisch durch die DB vergeben wird
	private int id;
	
	private String userId;

	private String lastName;

	private String firstName;

	
    public User() {
    	
       
    }
	
	
	//Eig ist in den Folien dieser Konstruktor leer weiß aber nicht wie ich dann
	//einen User erzeugen soll und źur Datenbank zu pushen
    public User(String userId, String firstName, String lastName ) {
    
    	this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
       
    }
	
	public int getId() {
		return id;
	}
	

	public void setUserId(String userId) {
		this.userId = userId;
	}

	
	public String getUserId() {
		return userId;
	}
	

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getLastName() {
		return lastName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstName() {
		return firstName;
	}








	
}
