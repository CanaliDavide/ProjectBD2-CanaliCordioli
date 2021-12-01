package en.polimi.db2.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the suspended_order database table.
 * 
 */
@Entity
@Table(name="suspended_order")
@NamedQuery(name="SuspendedOrder.findAll", query="SELECT s FROM SuspendedOrder s")
public class SuspendedOrder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idOrder;

	private Timestamp dataTime;

	private int idPack;

	private int idValidity;

	private float totalCost;

	public SuspendedOrder() {
	}

	public int getIdOrder() {
		return this.idOrder;
	}

	public void setIdOrder(int idOrder) {
		this.idOrder = idOrder;
	}

	public Timestamp getDataTime() {
		return this.dataTime;
	}

	public void setDataTime(Timestamp dataTime) {
		this.dataTime = dataTime;
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

	public float getTotalCost() {
		return this.totalCost;
	}

	public void setTotalCost(float totalCost) {
		this.totalCost = totalCost;
	}

}