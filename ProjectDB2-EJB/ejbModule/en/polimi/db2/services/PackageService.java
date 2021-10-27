package en.polimi.db2.services;

import java.util.Collection;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import en.polimi.db2.entities.OrderData;
import en.polimi.db2.entities.PackageData;

@Stateless
@LocalBean
public class PackageService {
	@PersistenceContext(unitName = "ProjectDB2-EJB")
	protected EntityManager em;
	
	public PackageService() {}
	
	public PackageData createPackage(String name, List<OrderData> orderData, List<en.polimi.db2.entities.PackageService> packageServices) {
		PackageData newPackage = new PackageData(name, orderData, packageServices);
		em.persist(newPackage);
		return newPackage;
	}
	
	public Collection<PackageData> findAllPackage() {
		TypedQuery<PackageData> query = em.createNamedQuery("PackageData.findAll", PackageData.class);
		return query.getResultList();
	}
	
}
