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
	
	public PackageData createPackage(String name, List<OptionalData> optionalData, List<Service> services) {
		PackageData newPackage = new PackageData(name, optionalData, services);
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
	
	public Double totalCostForPackage(int idPack, int idValidity, List<Integer> idOptionals) {
		TypedQuery<Double> query = em.createQuery("select (sum(od.feeMonthly)+vp.feeMonth)*vp.month from PackageOption po join OptionalData od on po.id.idOptional = od.id, Validityperiod vp"
				+ " where po.id.idPackage = ?1 and vp.id = ?2 and od.id in ?3", Double.class);
		return query.setParameter(1, idPack).setParameter(2, idValidity).setParameter(3, idOptionals).getSingleResult();
	}
	
}
