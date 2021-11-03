package en.polimi.db2.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the validityperiod database table.
 * 
 */
@Entity
@NamedQueries({ @NamedQuery(name = "Validityperiod.findAll", query = "SELECT v FROM Validityperiod v"),
		@NamedQuery(name = "Validityperiod.findByIds", query = "SELECT v FROM Validityperiod v where v.id in ?1") })
public class Validityperiod implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private float feeMonth;

	private int month;

	// bi-directional many-to-one association to OrderData
	@OneToMany(mappedBy = "validityperiod")
	private List<OrderData> orderData;

	// bi-directional many-to-one association to PackageService
	@OneToMany(mappedBy = "validityPeriod")
	private List<PackageValidity> packageValidities;

	// bi-directional many-to-many association to PackageData
	@ManyToMany
	@JoinTable(name = "package_validity", joinColumns = { @JoinColumn(name = "idValidity") }, inverseJoinColumns = {
			@JoinColumn(name = "idPackage") })
	private List<PackageData> packageData;

	public Validityperiod() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getFeeMonth() {
		return this.feeMonth;
	}

	public void setFeeMonth(float feeMonth) {
		this.feeMonth = feeMonth;
	}

	public int getMonth() {
		return this.month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public List<OrderData> getOrderData() {
		return this.orderData;
	}

	public void setOrderData(List<OrderData> orderData) {
		this.orderData = orderData;
	}

	public OrderData addOrderData(OrderData orderData) {
		getOrderData().add(orderData);
		orderData.setValidityperiod(this);

		return orderData;
	}

	public OrderData removeOrderData(OrderData orderData) {
		getOrderData().remove(orderData);
		orderData.setValidityperiod(null);

		return orderData;
	}

}