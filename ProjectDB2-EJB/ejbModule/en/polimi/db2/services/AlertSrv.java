package en.polimi.db2.services;

import java.sql.Timestamp;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import en.polimi.db2.entities.Alert;
import en.polimi.db2.entities.UserData;

@Stateless
@LocalBean
public class AlertSrv {
	@PersistenceContext(unitName = "ProjectDB2-EJB")
	protected EntityManager em;
	
	public AlertSrv() {}
	
	public Alert createAlert(String mail, String username, UserData user, Timestamp lastReject, float totalCost) {
		Alert alert = new Alert(mail, lastReject, totalCost, username, user);
		em.persist(alert);
		return alert;
	}

}
