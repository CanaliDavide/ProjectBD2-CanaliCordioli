package en.polimi.db2.services;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import en.polimi.db2.entities.OptionalData;
import en.polimi.db2.entities.OrderData;
import en.polimi.db2.entities.PackageData;
import en.polimi.db2.entities.UserData;
import en.polimi.db2.entities.Validityperiod;

@Stateless
@LocalBean
public class OrderSrv {
	@PersistenceContext(unitName = "ProjectDB2-EJB")
	protected EntityManager em;

	public OrderSrv() {
	}

	public OrderData createOrder(Date dataActivation, Timestamp dateTime, boolean isValid, int numberOfInvalid,
			float totalCost, List<OptionalData> optionalData, PackageData packageData, UserData userData,
			Validityperiod validityperiod) {

		OrderData order = new OrderData(dataActivation, dateTime, isValid, numberOfInvalid, totalCost, optionalData,
				packageData, userData, validityperiod);

		UserData user = em.find(UserData.class, userData.getId());
		if (!user.getIsInsolvent() && !isValid)
			user.setIsInsolvent(true);

		em.persist(order);
		return order;
	}

	public List<OrderData> findAllRejectedWithUserId(int id) {
		TypedQuery<OrderData> query = em
				.createQuery("select o from OrderData o where o.userData = ?1 and o.isValid = 0", OrderData.class);
		return query.setParameter(1, em.find(UserData.class, id)).getResultList();
	}

	public OrderData findRejectedOrderOfUser(int idOrder, int idUser) {
		TypedQuery<OrderData> query = em.createQuery(
				"select o from OrderData o where o.userData = ?1 and o.id = ?2 and o.isValid = 0", OrderData.class);
		try {
			return query.setParameter(1, em.find(UserData.class, idUser)).setParameter(2, idOrder).getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	public OrderData buyInsolvent(int idOrder, int idUser, boolean isValid) {
		OrderData order = em.find(OrderData.class, idOrder);
		if (order != null) {
			if (order.getUserData().getId() == idUser) {
				if (isValid) {
					order.setIsValid(true);
					em.flush();
					UserData user = em.find(UserData.class, idUser);
					if (findAllRejectedWithUserId(idUser) == null) {
						user.setIsInsolvent(false);
					}
				} else {
					order.setNumberOfInvalid(order.getNumberOfInvalid() + 1);
				}
			}
		}
		return order;
	}

	public Integer numberOfFailedPay(int userId) {
		TypedQuery<Integer> query = em
				.createQuery("select sum(o.numberOfInvalid) from OrderData o where o.userData.id = ?1", Integer.class);
		try {
			return query.getFirstResult();
		}catch(Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> totalPurchasePerPackage() {
		Query query = em.createQuery("select o.packageData.id, count(o) from OrderData o group by o.packageData.id");
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> totalPurchasePerPerckageAndValidity() {
		Query query = em.createQuery(
				"select o.packageData.id, o.validityperiod.month, count(o) from OrderData o group by o.packageData.id, o.validityperiod.id");
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> packageValue() {
		Query query = em.createQuery(
				"select o.packageData.id, sum(o.totalCost), sum(o.totalCost)-sum(opt.feeMonthly*o.validityperiod.month)"
						+ " from OrderData o left join o.packageData.packageOptions po"
						+ " join OptionalData opt on po.id.idOptional = opt.id" + " group by o.packageData.id");
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> avgOptionalsPerPackage() {
		Query query = em.createQuery("select o.packageData.id,count(o.id),count(distinct(o.id))"
				+ " from OrderData o left join o.orderOptions po"
				+ " join OptionalData opt on po.id.idOptional = opt.id" + " group by o.packageData.id");
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> mostValueOptional() {
		Query query = em.createQuery("select opt.id, sum(opt.feeMonthly*o.validityperiod.month)"
				+ " from OrderData o join o.orderOptions op" + " join OptionalData opt on opt.id = op.id.idOptional"
				+ " group by opt.id" + " order by sum(opt.feeMonthly*o.validityperiod.month) desc");
		return query.setMaxResults(1).getResultList();
	}

	public List<OrderData> findAllSuspended() {
		TypedQuery<OrderData> query = em.createNamedQuery("OrderData.findAllSuspended", OrderData.class);
		return query.getResultList();
	}
}