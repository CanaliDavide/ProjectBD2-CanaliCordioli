package en.polimi.db2.services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import en.polimi.db2.entities.OptionalData;
import en.polimi.db2.entities.PackageData;

@Stateless
@LocalBean
public class OptionalSrv {
	@PersistenceContext(unitName = "ProjectDB2-EJB")
	protected EntityManager em;
	
	public OptionalSrv() {}

	public OptionalData createOptional(float feeMonthly, String name) {
		OptionalData optional = new OptionalData(feeMonthly, name);
		em.persist(optional);
		return optional;
	}
	
	public List<OptionalData> findByIds(List<Integer> ids){
		TypedQuery<OptionalData> query = em.createNamedQuery("OptionalData.findByIds", OptionalData.class);
		return query.setParameter(1, ids).getResultList();
	}
	
}
