package en.polimi.db2.services;

import java.util.Collection;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import en.polimi.db2.entities.UserData;
import en.polimi.db2.exceptions.CredentialsException;

@Stateless
@LocalBean
public class UserSrv {
	@PersistenceContext(unitName = "ProjectDB2-EJB")
	protected EntityManager em;

	public UserSrv() {
	}

	public UserData createUser(String username, String password, String mail, boolean isEmployee, boolean isInsolvent) {
		UserData user = new UserData(mail, password,username , isEmployee, isInsolvent);
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
	
	public UserData checkCredentials(String mail, String pwd) throws CredentialsException, NonUniqueResultException {
		List<UserData> uList = null;
		try {
			uList = em.createNamedQuery("User.checkCredentials", UserData.class).setParameter(1, mail).setParameter(2, pwd)
					.getResultList();
		} catch (PersistenceException e) {
			throw new CredentialsException("Could not verify credentals");
		}
		if (uList.isEmpty())
			return null;
		else if (uList.size() == 1)
			return uList.get(0);
		throw new NonUniqueResultException("More than one user registered with same credentials");

	}
}
