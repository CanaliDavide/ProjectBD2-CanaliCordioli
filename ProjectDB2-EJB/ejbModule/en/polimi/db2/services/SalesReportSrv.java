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


}
