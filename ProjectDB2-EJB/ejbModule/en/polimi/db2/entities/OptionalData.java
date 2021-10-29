package en.polimi.db2.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the optional_data database table.
 * 
 */
@Entity
@Table(name="optional_data")
@NamedQueries({
	@NamedQuery(name="OptionalData.findAll", query="SELECT o FROM OptionalData o"),
	@NamedQuery(name="OptionalData.findByIds", query="SELECT o FROM OptionalData o where o.id in ?1")
})
public class OptionalData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private float feeMonthly;

	private String name;

	//bi-directional many-to-many association to OrderData
	@ManyToMany(mappedBy="optionalData")
	private List<OrderData> orderData;

	//bi-directional many-to-one association to OrderOption
	@OneToMany(mappedBy="optionalData")
	private List<OrderOption> orderOptions;

	//bi-directional many-to-many association to PackageData
	@ManyToMany(mappedBy="optionalData")
	private List<PackageData> packageData;

	//bi-directional many-to-one association to PackageOption
	@OneToMany(mappedBy="optionalData")
	private List<PackageOption> packageOptions;

	public OptionalData() {
	}
	
	public OptionalData(float feeMonthly, String name) {
		this.feeMonthly = feeMonthly;
		this.name = name;
	}



	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getFeeMonthly() {
		return this.feeMonthly;
	}

	public void setFeeMonthly(float feeMonthly) {
		this.feeMonthly = feeMonthly;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<OrderData> getOrderData() {
		return this.orderData;
	}

	public void setOrderData(List<OrderData> orderData) {
		this.orderData = orderData;
	}

	public List<OrderOption> getOrderOptions() {
		return this.orderOptions;
	}

	public void setOrderOptions(List<OrderOption> orderOptions) {
		this.orderOptions = orderOptions;
	}

	public OrderOption addOrderOption(OrderOption orderOption) {
		getOrderOptions().add(orderOption);
		orderOption.setOptionalData(this);

		return orderOption;
	}

	public OrderOption removeOrderOption(OrderOption orderOption) {
		getOrderOptions().remove(orderOption);
		orderOption.setOptionalData(null);

		return orderOption;
	}

	public List<PackageData> getPackageData() {
		return this.packageData;
	}

	public void setPackageData1(List<PackageData> packageData) {
		this.packageData = packageData;
	}

	public List<PackageOption> getPackageOptions() {
		return this.packageOptions;
	}

	public void setPackageOptions(List<PackageOption> packageOptions) {
		this.packageOptions = packageOptions;
	}

	public PackageOption addPackageOption(PackageOption packageOption) {
		getPackageOptions().add(packageOption);
		packageOption.setOptionalData(this);

		return packageOption;
	}

	public PackageOption removePackageOption(PackageOption packageOption) {
		getPackageOptions().remove(packageOption);
		packageOption.setOptionalData(null);

		return packageOption;
	}

}