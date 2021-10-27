package en.polimi.db2.services;

import java.util.Collection;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import en.polimi.db2.entities.OptionalData;
import en.polimi.db2.entities.OrderData;
import en.polimi.db2.entities.PackageData;
import en.polimi.db2.entities.PackageOption;
import en.polimi.db2.entities.PackageService;
import en.polimi.db2.entities.Service;

@Stateless
@LocalBean
public class PackageSrv {
	@PersistenceContext(unitName = "ProjectDB2-EJB")
	protected EntityManager em;
	
	public PackageSrv() {}
	
	public PackageData createPackage(String name, List<OrderData> orderData, List<OptionalData> optionalData,
			List<PackageService> packageServices, List<Service> services, List<PackageOption> packageOptions) {
		PackageData newPackage = new PackageData(name, orderData, optionalData,
				packageServices, services, packageOptions);
		em.persist(newPackage);
		return newPackage;
	}
	
	public List<PackageData> findAllPackage() {
		TypedQuery<PackageData> query = em.createNamedQuery("PackageData.findAll", PackageData.class);
		return query.getResultList();
	}
	
}
