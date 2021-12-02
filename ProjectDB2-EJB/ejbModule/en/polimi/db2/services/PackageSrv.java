package en.polimi.db2.services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import en.polimi.db2.entities.OptionalData;
import en.polimi.db2.entities.PackageData;
import en.polimi.db2.entities.Service;
import en.polimi.db2.entities.Validityperiod;

@Stateless
@LocalBean
public class PackageSrv {
	@PersistenceContext(unitName = "ProjectDB2-EJB")
	protected EntityManager em;

	public PackageSrv() {
	}

	public PackageData createPackage(String name, List<OptionalData> optionalData, List<Service> services,
			List<Validityperiod> validityPeriods) {
		PackageData newPackage = new PackageData(name, optionalData, services, validityPeriods);
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
		if (idOptionals.isEmpty()) {
			TypedQuery<Float> query = em.createQuery(
					"select vp.feeMonth*vp.month from Validityperiod vp" + " where vp.id = ?1 ", Float.class);
			try {
				return query.setParameter(1, idValidity).getSingleResult().doubleValue();
			} catch (Exception e) {
				return null;
			}
		} else {
			TypedQuery<Double> query = em.createQuery(
					"select (sum(od.feeMonthly)+vp.feeMonth)*vp.month from PackageOption po join OptionalData od on po.id.idOptional = od.id, Validityperiod vp"
							+ " where po.id.idPackage = ?1 and vp.id = ?2 and od.id in ?3",
					Double.class);
			try {
				return query.setParameter(1, idPack).setParameter(2, idValidity).setParameter(3, idOptionals)
						.getSingleResult();
			} catch (Exception e) {
				return null;
			}
		}
	}
}
