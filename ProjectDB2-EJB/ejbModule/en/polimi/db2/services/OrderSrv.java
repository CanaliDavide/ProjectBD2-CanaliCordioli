package en.polimi.db2.services;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
		em.persist(order);
		return order;
	}
}
