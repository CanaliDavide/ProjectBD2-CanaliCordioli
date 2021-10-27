package en.polimi.db2.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the package_option database table.
 * 
 */
@Embeddable
public class PackageOptionPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private int idPackage;

	@Column(insertable=false, updatable=false)
	private int idOptional;

	public PackageOptionPK() {
	}
	public int getIdPackage() {
		return this.idPackage;
	}
	public void setIdPackage(int idPackage) {
		this.idPackage = idPackage;
	}
	public int getIdOptional() {
		return this.idOptional;
	}
	public void setIdOptional(int idOptional) {
		this.idOptional = idOptional;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PackageOptionPK)) {
			return false;
		}
		PackageOptionPK castOther = (PackageOptionPK)other;
		return 
			(this.idPackage == castOther.idPackage)
			&& (this.idOptional == castOther.idOptional);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idPackage;
		hash = hash * prime + this.idOptional;
		
		return hash;
	}
}