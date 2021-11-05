package en.polimi.db2.services;

import java.util.Collection;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import en.polimi.db2.entities.UserData;

@Stateless
@LocalBean
public class UserSrv {
	@PersistenceContext(unitName = "ProjectDB2-EJB")
	protected EntityManager em;

	public UserSrv() {
	}

	public UserData createUser(String username, String password, String mail, boolean isEmployee, boolean isInsolvent) {
		UserData user = new UserData(mail, password, username, isEmployee, isInsolvent);
		em.persist(user);
		return user;
	}

	public void removeUser(int id) {
		UserData user = findUser(id);
		if (user != null) {
			em.remove(user);
		} else {
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

	public UserData checkCredentials(String mail, String pwd) {
		List<UserData> uList = null;
		try {
			uList = em.createNamedQuery("UserData.checkCredentials", UserData.class).setParameter(1, mail)
					.setParameter(2, pwd).getResultList();
		} catch (Exception e) {
			return null;
		}
		if (uList.isEmpty() || uList.size() != 1)
			return null;
		else
			return uList.get(0);
	}

	public List<UserData> findAllInsolvent() {
		TypedQuery<UserData> query = em.createNamedQuery("UserData.findAllInsolvent", UserData.class);
		return query.getResultList();
	}
}
