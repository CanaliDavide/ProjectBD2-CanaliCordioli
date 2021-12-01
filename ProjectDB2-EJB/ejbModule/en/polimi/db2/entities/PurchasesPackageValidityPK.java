package en.polimi.db2.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the purchases_package_validity database table.
 * 
 */
@Embeddable
public class PurchasesPackageValidityPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int idPack;

	private int idValidity;

	public PurchasesPackageValidityPK() {
	}
	public int getIdPack() {
		return this.idPack;
	}
	public void setIdPack(int idPack) {
		this.idPack = idPack;
	}
	public int getIdValidity() {
		return this.idValidity;
	}
	public void setIdValidity(int idValidity) {
		this.idValidity = idValidity;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PurchasesPackageValidityPK)) {
			return false;
		}
		PurchasesPackageValidityPK castOther = (PurchasesPackageValidityPK)other;
		return 
			(this.idPack == castOther.idPack)
			&& (this.idValidity == castOther.idValidity);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idPack;
		hash = hash * prime + this.idValidity;
		
		return hash;
	}
}