package en.polimi.db2.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the order_option database table.
 * 
 */
@Embeddable
public class OrderOptionPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private int idOrder;

	@Column(insertable=false, updatable=false)
	private int idOptional;

	public OrderOptionPK() {
	}
	public int getIdOrder() {
		return this.idOrder;
	}
	public void setIdOrder(int idOrder) {
		this.idOrder = idOrder;
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
		if (!(other instanceof OrderOptionPK)) {
			return false;
		}
		OrderOptionPK castOther = (OrderOptionPK)other;
		return 
			(this.idOrder == castOther.idOrder)
			&& (this.idOptional == castOther.idOptional);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idOrder;
		hash = hash * prime + this.idOptional;
		
		return hash;
	}
}