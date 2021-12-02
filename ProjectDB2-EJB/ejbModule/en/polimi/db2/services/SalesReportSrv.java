package en.polimi.db2.services;

import java.util.Collection;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import en.polimi.db2.entities.UserData;

@Stateless
@LocalBean
public class SalesReportSrv {
	@PersistenceContext(unitName = "ProjectDB2-EJB")
	protected EntityManager em;

	public SalesReportSrv() {
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> totalPurchasePerPackage() {
		Query query = em.createQuery("select o.idPack , o.namePack , o.numPurc"
				+ " from PurchasesPackage o");
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> totalPurchasePerPerckageAndValidity() {
		Query query = em.createQuery(
				"select o.id.idPack , o.namePack, o.numPurc, vp.month"
				+ " from PurchasesPackageValidity o join Validityperiod vp on o.id.idValidity = vp.id");
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> packageValue() {
		Query query = em.createQuery(
				"select o.idPack , o.namePack , o.valueOptional, o.valueNoOptional"
						+ " from PurchasesPackage o");
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> avgOptionalsPerPackage() {
		Query query = em.createQuery("select o.idPack , o.namePack , o.averageOpt"
				+ " from PurchasesPackage o");
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> mostValueOptional() {
		Query query = em.createQuery("select o.idOptional, o.nameOpt, o.totEarn"
				+ " from SellerOptional o"
				+ " order by o.totEarn desc");
		return query.setMaxResults(1).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Object> findAllSuspended() {
		Query query = em.createQuery("select o.idOrder , o.namePack , o.mail"
				+ " from SuspendedOrder o");
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Object> findAllAlert() {
		Query query = em.createQuery("select o.email , o.totalCost, o.lastReject"
				+ " from Alert o");
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Object> findAllInsolvent() {
		Query query = em.createQuery("select o.idUser, o.username, o.mail"
				+ " from InsolventUser o");
		return query.getResultList();
	}


}
