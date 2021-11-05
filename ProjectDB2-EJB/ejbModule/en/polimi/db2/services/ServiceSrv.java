package en.polimi.db2.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import en.polimi.db2.entities.Service;

@Stateless
@LocalBean
public class ServiceSrv {
	@PersistenceContext(unitName = "ProjectDB2-EJB")
	protected EntityManager em;
	
	public ServiceSrv() {}
	
	public List<Service> findAll(){
		TypedQuery<Service> query = em.createNamedQuery("Service.findAll", Service.class);
		return query.getResultList();
	}
	
	public List<Service> findByIds(List<Integer> ids){
		if(ids.isEmpty()) {
			List<Service> srv = new ArrayList<Service>();
			return srv;
		}
		TypedQuery<Service> query = em.createNamedQuery("Service.findByIds", Service.class);
		return query.setParameter(1, ids).getResultList();
	}
}
