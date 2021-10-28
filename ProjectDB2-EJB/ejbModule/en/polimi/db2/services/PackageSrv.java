package en.polimi.db2.services;

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
	
	public PackageData findPackageWithId(int id) {
		return em.find(PackageData.class, id);
	}
	
	public Float totalCostForPackage(int idPack, int idValidity, List<Integer> idOptionals) {
		TypedQuery<Float> query = em.createQuery("select (sum(feeMonthly)+feeMonth)*month from package_option join optional_data on idOptional = id, validityperiod AS vp WHERE idPackage = ?1 and vp.id = ?2 and idOptional in ?3", Float.class);
		return query.setParameter(1, idPack).setParameter(2, idValidity).setParameter(3, idOptionals).getSingleResult();
	}
	
}
