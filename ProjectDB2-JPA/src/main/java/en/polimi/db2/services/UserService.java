package en.polimi.db2.services;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import en.polimi.db2.entities.UserData;

public class UserService {
	protected EntityManager em;

    public UserService(EntityManager em) {
        this.em = em;
    }

    public UserData createUser(String username, String password, String mail, boolean isEmployee, boolean isInsolvent) {
        UserData user = new UserData(username, password, mail, isEmployee, isInsolvent);
        em.persist(user);
        return user;
    }

    public void removeUser(int id) {
    	UserData user = findUser(id);
        if (user != null) {
            em.remove(user);
        }else {
        	System.out.print("User not found");
        }
    }

    public UserData findUser(int id) {
        return em.find(UserData.class, id);
    }

    public Collection<UserData> findAllUser() {
        TypedQuery<UserData> query = em.createNamedQuery("UserData.findAll", UserData.class);
        return query.getResultList();
    }
}