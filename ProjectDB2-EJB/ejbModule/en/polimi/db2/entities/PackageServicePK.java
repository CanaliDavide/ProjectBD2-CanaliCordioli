package en.polimi.db2.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the package_service database table.
 * 
 */
@Embeddable
public class PackageServicePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private int idPackage;

	@Column(insertable=false, updatable=false)
	private int idService;

	public PackageServicePK() {
	}
	public int getIdPackage() {
		return this.idPackage;
	}
	public void setIdPackage(int idPackage) {
		this.idPackage = idPackage;
	}
	public int getIdService() {
		return this.idService;
	}
	public void setIdService(int idService) {
		this.idService = idService;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PackageServicePK)) {
			return false;
		}
		PackageServicePK castOther = (PackageServicePK)other;
		return 
			(this.idPackage == castOther.idPackage)
			&& (this.idService == castOther.idService);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idPackage;
		hash = hash * prime + this.idService;
		
		return hash;
	}
}