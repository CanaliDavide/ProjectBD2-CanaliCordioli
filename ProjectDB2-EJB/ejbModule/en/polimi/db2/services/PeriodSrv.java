package en.polimi.db2.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import en.polimi.db2.entities.Validityperiod;

@Stateless
@LocalBean
public class PeriodSrv {
	@PersistenceContext(unitName = "ProjectDB2-EJB")
	protected EntityManager em;
	
	public List<Validityperiod> findAllPeriods() {
		TypedQuery<Validityperiod> query = em.createNamedQuery("Validityperiod.findAll", Validityperiod.class);
		return query.getResultList();
	}
	
	public Validityperiod findValidityWithId(int id) {
		return em.find(Validityperiod.class, id);
	}
	
	public List<Validityperiod> findByIds(List<Integer> ids){
		if(ids.isEmpty()) {
			List<Validityperiod> prd = new ArrayList<Validityperiod>();
			return prd;
		}
		TypedQuery<Validityperiod> query = em.createNamedQuery("Validityperiod.findByIds", Validityperiod.class);
		return query.setParameter(1, ids).getResultList();
	}
}
