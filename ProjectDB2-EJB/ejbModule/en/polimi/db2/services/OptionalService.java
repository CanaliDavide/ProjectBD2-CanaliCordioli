package en.polimi.db2.services;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import en.polimi.db2.entities.OptionalData;

@Stateless
@LocalBean
public class OptionalService {
	@PersistenceContext(unitName = "ProjectDB2-EJB")
	protected EntityManager em;
	
	public OptionalService() {}

	public OptionalData createOptional(float feeMonthly, String name) {
		OptionalData optional = new OptionalData(feeMonthly, name);
		em.persist(optional);
		return optional;
	}
}
