package en.polimi.db2.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the seller_optional database table.
 * 
 */
@Entity
@Table(name="seller_optional")
@NamedQuery(name="SellerOptional.findAll", query="SELECT s FROM SellerOptional s")
public class SellerOptional implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idOptional;

	private float feeMonth;

	private String nameOpt;

	private float totEarn;

	public SellerOptional() {
	}

	public int getIdOptional() {
		return this.idOptional;
	}

	public void setIdOptional(int idOptional) {
		this.idOptional = idOptional;
	}

	public float getFeeMonth() {
		return this.feeMonth;
	}

	public void setFeeMonth(float feeMonth) {
		this.feeMonth = feeMonth;
	}

	public String getNameOpt() {
		return this.nameOpt;
	}

	public void setNameOpt(String nameOpt) {
		this.nameOpt = nameOpt;
	}

	public float getTotEarn() {
		return this.totEarn;
	}

	public void setTotEarn(float totEarn) {
		this.totEarn = totEarn;
	}

}