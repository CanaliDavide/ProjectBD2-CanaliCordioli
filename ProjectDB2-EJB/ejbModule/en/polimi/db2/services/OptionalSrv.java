package en.polimi.db2.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import en.polimi.db2.entities.OptionalData;

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
		if(ids.isEmpty()) {
			List<OptionalData> opt = new ArrayList<OptionalData>();
			return opt;
		}
		TypedQuery<OptionalData> query = em.createNamedQuery("OptionalData.findByIds", OptionalData.class);
		return query.setParameter(1, ids).getResultList();
	}
	
	public List<OptionalData> findAll(){
		TypedQuery<OptionalData> query = em.createNamedQuery("OptionalData.findAll", OptionalData.class);
		return query.getResultList();
	}	
}