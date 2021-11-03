package en.polimi.db2.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: PackageValidity
 *
 */
@Entity
@Table(name="package_validity")
public class PackageValidity implements Serializable {

	@EmbeddedId
	private PackageValidityPK id;
	
	//bi-directional many-to-one association to PackageData
	@ManyToOne
	@JoinColumn(name="idPackage")
	private PackageData packageData;

	//bi-directional many-to-one association to Service
	@ManyToOne
	@JoinColumn(name="idValidity")
	private Validityperiod validityPeriod;
	
	private static final long serialVersionUID = 1L;

	public PackageValidity() {
		super();
	}   

	public PackageValidityPK getId() {
		return this.id;
	}

	public void setId(PackageValidityPK id) {
		this.id = id;
	}

	public PackageData getPackageData() {
		return packageData;
	}

	public void setPackageData(PackageData packageData) {
		this.packageData = packageData;
	}

	public Validityperiod getValidity() {
		return validityPeriod;
	}

	public void setValidity(Validityperiod validity) {
		this.validityPeriod = validity;
	}
	
	
}
