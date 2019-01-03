package de.htw.ai.kbe;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import de.htw.ai.kbe.bean.Song;
import de.htw.ai.kbe.bean.User;
import de.htw.ai.kbe.storage.InMemorySongDI;

public class App {

    // entspricht <persistence-unit name="songDB-PU" transaction-type="RESOURCE_LOCAL"> in persistence.xml
    private static final String PERSISTENCE_UNIT_NAME = "songDB";

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {

    	

    
        // Datei persistence.xml wird automatisch eingelesen, beim Start der Applikation
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

        // EntityManager bietet Zugriff auf Datenbank
        EntityManager em = factory.createEntityManager();

        try {
	
            // Create: neuen User anlegen
       
        	
          // em.getTransaction().begin();
          
         //  User user = new User("User3","user3","2019");
           
          // em.persist(user);

            // Alle User aus der DB lesen mit JPQL
            Query q = em.createQuery("SELECT u FROM User u");
            @SuppressWarnings("unchecked")
            List<User> userList = q.getResultList();
            System.out.println("All users - size: " + userList.size());
            for (User u : userList) {
                System.out.println("Id: " + u.getId() + " with firstName: " + u.getFirstName());
            }
            /*
            // Read
            int user3Id = user.getId();
            User user3FromDB = em.find(User.class, user3Id);
            System.out.println("Found bobUser: " + user3FromDB);

            // Update
            user3FromDB.setFirstName("User3veränderterName");

            // Check that update happened:
            q = em.createQuery("SELECT u FROM User u where id = " + user3FromDB.getId());
            userList = q.getResultList();
            for (User u : userList) {
                System.out.println("after update Id: " + u.getId() + " with firstName: " + u.getFirstName());
            }
            /*
            // Delete
            em.remove(user3FromDB);

            // Check that delete happened:
            q = em.createQuery("SELECT u FROM User u");
            userList = q.getResultList();
            System.out.println("after delete - size: " + userList.size());
			*/
            // commit transaction
            em.getTransaction().commit();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            // EntityManager nach Datenbankaktionen wieder freigeben
            em.close();
            // Freigabe am Ende der Applikation
            factory.close();
        }
        
    }
    
}


//2. Probleme
//XML root bei Song und User hat komische ausgabe bei einzelner get deshalb erstmal rausgenommen
//Post hat bereits geklappt -> allerdings keine ausgabe der erstellten id

	/*

		   Song  song = new Song("Can’t Stop the Feeling","Justin Timberlake","Trolls","2016");
           em.persist(song);
           
           song = new Song("Mom","Meghan Trainor, Kelli Trainor","Thank You","2016");
           em.persist(song);
           
           song = new Song("Team","Iggy Azalea","2016");
           em.persist(song);
           
           song = new Song("Ghostbusters (I'm not a fraid)","Fall Out Boy, Missy Elliott","Ghostbusters","2016");
           em.persist(song);
      
           song = new Song("Bad Things","Camila Cabello, Machine Gun Kelly","Bloom","2017");
           em.persist(song);
         
           song = new Song("I Took a Pill in Ibiza","Mike Posner","At Night, Alone.","2016");
           em.persist(song);
           
           song = new Song("i hate u, i love u","Gnash","Top Hits 2017","2017");
           em.persist(song);
           
           song = new Song("No","Meghan Trainor","Thank You","2016");
           em.persist(song);
           
           song = new Song("Private Show","Britney Spears","Glory","2016");
           em.persist(song);
           
          song = new Song("7 Years","Lukas Graham","Lukas Graham (Blue Album)","2015");
           em.persist(song);

*/