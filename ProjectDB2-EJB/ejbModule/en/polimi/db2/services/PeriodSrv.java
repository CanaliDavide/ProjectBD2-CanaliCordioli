package en.polimi.db2.services;

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
	
	public List<Validityperiod> findAllPeriode() {
		TypedQuery<Validityperiod> query = em.createNamedQuery("Validityperiod.findAll", Validityperiod.class);
		return query.getResultList();
	}
}
