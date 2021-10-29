package en.polimi.db2.services;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import en.polimi.db2.entities.OptionalData;
import en.polimi.db2.entities.OrderData;
import en.polimi.db2.entities.OrderOption;
import en.polimi.db2.entities.PackageData;
import en.polimi.db2.entities.UserData;
import en.polimi.db2.entities.Validityperiod;


@Stateless
@LocalBean
public class OrderSrv {
	@PersistenceContext(unitName = "ProjectDB2-EJB")
	protected EntityManager em;
	
	public OrderSrv() {}
	
	public OrderData createOrder(Date dataActivation, Timestamp dateTime, boolean isValid, int numberOfInvalid, float totalCost,
			List<OptionalData> optionalData, PackageData packageData, UserData userData, Validityperiod validityperiod) {
		OrderData order = new OrderData(dataActivation, dateTime, isValid, numberOfInvalid, totalCost,
				optionalData, packageData, userData, validityperiod);
		
		em.find(UserData.class, userData.getId()).setIsInsolvent(!isValid);
	
		em.persist(order);
		return order;
	}

	public List<OrderData> findAllRejectedWithUserId(int id) {
		TypedQuery<OrderData> query = em.createQuery("select o from OrderData o where o.userData = ?1 and o.isValid = 0", OrderData.class);
		return query.setParameter(1, em.find(UserData.class, id)).getResultList();
	}
}