package en.polimi.db2.tests;

import java.io.IOException;
import java.util.Collection;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import en.polimi.db2.entities.UserData;
import en.polimi.db2.services.UserService;

public class TestJPA_Database {
	 public static void main(String[] args) throws IOException {
	        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjectDB-JPA");
	        EntityManager em = emf.createEntityManager();
	        UserService service = new UserService(em);
	        
	        Scanner in = new Scanner(System.in);
	        String action;
	        try {
	            while (true) {
	                System.out.println("\n\n\n[L]ist| [A]dd | [R]emove | [Q]uit: \n");
	                action = in.next();
	                if ((action.length() == 0) || action.toUpperCase().charAt(0) == 'Q') {
	                    break;
	                }
	    
	                switch (action.toUpperCase().charAt(0)) {
	                    case 'A':
	                        System.out.println("Enter User  name: \n");
	                        String username = in.next();
	    
	                        System.out.println("Enter User password: \n");
	                        String password = in.next();
	    
	                        System.out.println("Enter User mail: \n");
	                        String mail = in.next();
	                        
	                        try {
	                        	em.getTransaction().begin();
	                        	UserData user = service.createUser(username, password, mail, false, true);
	                        	em.getTransaction().commit();
	                        	System.out.println("\n\nCreated " + user);
	                        	}
	                        catch(Exception e){
	                        	System.out.print("Error in creating user");
	                        }
	                        break;
	    
	                    case 'L':
	                        Collection<UserData> users = service.findAllUser();
	                        System.out.println("\n\nFound users: ");
	                        for (UserData u : users)
	                            System.out.println("\t" + u);                        
	                        break;
	    
	                    case 'R':
	                    	int id;
	                        System.out.println("Enter int value for User id: \n");
	                        id = in.nextInt();
	                        System.out.print(id);
	                        
	                        em.getTransaction().begin();
	                        service.removeUser(id);
	                        em.getTransaction().commit();
	                        
	                        System.out.println("\n\nRemoved User " + id);
	                        break;
	                    default:
	                        continue;
	                }
	            }
	        } finally {        
	            // close the EntityManager when done
	            em.close();
	            emf.close();
	            
	            in.close();
	        }
	    }
}
