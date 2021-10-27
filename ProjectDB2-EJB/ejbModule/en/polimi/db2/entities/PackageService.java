package en.polimi.db2.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the package_service database table.
 * 
 */
@Entity
@Table(name="package_service")
@NamedQuery(name="PackageService.findAll", query="SELECT p FROM PackageService p")
public class PackageService implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PackageServicePK id;

	//bi-directional many-to-one association to PackageData
	@ManyToOne
	@JoinColumn(name="idPackage")
	private PackageData packageData;

	//bi-directional many-to-one association to Service
	@ManyToOne
	@JoinColumn(name="idService")
	private Service service;

	public PackageService() {
	}

	public PackageServicePK getId() {
		return this.id;
	}

	public void setId(PackageServicePK id) {
		this.id = id;
	}

	public PackageData getPackageData() {
		return this.packageData;
	}

	public void setPackageData(PackageData packageData) {
		this.packageData = packageData;
	}

	public Service getService() {
		return this.service;
	}

	public void setService(Service service) {
		this.service = service;
	}

}