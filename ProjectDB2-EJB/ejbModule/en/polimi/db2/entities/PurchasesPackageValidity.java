package en.polimi.db2.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the purchases_package_validity database table.
 * 
 */
@Entity
@Table(name="purchases_package_validity")
@NamedQuery(name="PurchasesPackageValidity.findAll", query="SELECT p FROM PurchasesPackageValidity p")
public class PurchasesPackageValidity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PurchasesPackageValidityPK id;

	private String namePack;

	private int numPurc;

	public PurchasesPackageValidity() {
	}

	public PurchasesPackageValidityPK getId() {
		return this.id;
	}

	public void setId(PurchasesPackageValidityPK id) {
		this.id = id;
	}

	public String getNamePack() {
		return this.namePack;
	}

	public void setNamePack(String namePack) {
		this.namePack = namePack;
	}

	public int getNumPurc() {
		return this.numPurc;
	}

	public void setNumPurc(int numPurc) {
		this.numPurc = numPurc;
	}

}