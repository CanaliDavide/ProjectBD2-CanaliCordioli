package en.polimi.db2.entities;

import java.io.Serializable;
import java.lang.Integer;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * ID class for entity: PackageValidity
 *
 */ 
@Embeddable
public class PackageValidityPK  implements Serializable {   
   
	@Column(insertable=false, updatable=false)
	private Integer idPackage; 
	@Column(insertable=false, updatable=false)
	private Integer idValidity;
	private static final long serialVersionUID = 1L;

	public PackageValidityPK() {}

	

	public Integer getIdPackage() {
		return this.idPackage;
	}

	public void setIdPackage(Integer idPackage) {
		this.idPackage = idPackage;
	}
	

	public Integer getIdValidity() {
		return this.idValidity;
	}

	public void setIdValidity(Integer idValidity) {
		this.idValidity = idValidity;
	}
	
   
	/*
	 * @see java.lang.Object#equals(Object)
	 */	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof PackageValidityPK)) {
			return false;
		}
		PackageValidityPK other = (PackageValidityPK) o;
		return true
			&& (getIdPackage() == null ? other.getIdPackage() == null : getIdPackage().equals(other.getIdPackage()))
			&& (getIdValidity() == null ? other.getIdValidity() == null : getIdValidity().equals(other.getIdValidity()));
	}
	
	/*	 
	 * @see java.lang.Object#hashCode()
	 */	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (getIdPackage() == null ? 0 : getIdPackage().hashCode());
		result = prime * result + (getIdValidity() == null ? 0 : getIdValidity().hashCode());
		return result;
	}
   
   
}
