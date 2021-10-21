package en.polimi.db2.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the order_option database table.
 * 
 */
@Entity
@Table(name="order_option")
@NamedQuery(name="OrderOption.findAll", query="SELECT o FROM OrderOption o")
public class OrderOption implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	//bi-directional many-to-one association to OptionalData
	@ManyToOne
	@JoinColumn(name="idOptional")
	private OptionalData optionalData;

	//bi-directional many-to-one association to OrderData
	@ManyToOne
	@JoinColumn(name="idOrder")
	private OrderData orderData;

	public OrderOption() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public OptionalData getOptionalData() {
		return this.optionalData;
	}

	public void setOptionalData(OptionalData optionalData) {
		this.optionalData = optionalData;
	}

	public OrderData getOrderData() {
		return this.orderData;
	}

	public void setOrderData(OrderData orderData) {
		this.orderData = orderData;
	}

}