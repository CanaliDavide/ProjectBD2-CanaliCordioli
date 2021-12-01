package en.polimi.db2.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the purchases_package database table.
 * 
 */
@Entity
@Table(name="purchases_package")
@NamedQuery(name="PurchasesPackage.findAll", query="SELECT p FROM PurchasesPackage p")
public class PurchasesPackage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idPack;

	private float averageOpt;

	private String namePack;

	private int numPurc;

	private float valueNoOptional;

	private float valueOptional;

	public PurchasesPackage() {
	}

	public int getIdPack() {
		return this.idPack;
	}

	public void setIdPack(int idPack) {
		this.idPack = idPack;
	}

	public float getAverageOpt() {
		return this.averageOpt;
	}

	public void setAverageOpt(float averageOpt) {
		this.averageOpt = averageOpt;
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

	public float getValueNoOptional() {
		return this.valueNoOptional;
	}

	public void setValueNoOptional(float valueNoOptional) {
		this.valueNoOptional = valueNoOptional;
	}

	public float getValueOptional() {
		return this.valueOptional;
	}

	public void setValueOptional(float valueOptional) {
		this.valueOptional = valueOptional;
	}

}