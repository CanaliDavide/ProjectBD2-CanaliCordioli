package en.polimi.db2.services;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import en.polimi.db2.entities.OrderData;
import en.polimi.db2.entities.OrderOption;
import en.polimi.db2.entities.PackageData;
import en.polimi.db2.entities.UserData;
import en.polimi.db2.entities.Validityperiod;


@Stateless
@LocalBean
public class OrderService {
	@PersistenceContext(unitName = "ProjectDB2-EJB")
	protected EntityManager em;
	
	public OrderService() {}
	
	public OrderData createOrder(Date dataActivation, Timestamp dateTime, int numberOfInvalid,
			float totalCost, PackageData packageData, UserData userData, Validityperiod validityperiod,
			List<OrderOption> orderOptions) {
		OrderData order = new OrderData(dataActivation, dateTime, numberOfInvalid,
				totalCost, packageData, userData, validityperiod, orderOptions);
		em.persist(order);
		return order;
	}
}
