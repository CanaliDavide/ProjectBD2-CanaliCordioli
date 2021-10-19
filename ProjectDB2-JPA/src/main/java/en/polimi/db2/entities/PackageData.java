package en.polimi.db2.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the package_data database table.
 * 
 */
@Entity
@Table(name="package_data")
@NamedQuery(name="PackageData.findAll", query="SELECT p FROM PackageData p")
public class PackageData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String name;

	//bi-directional many-to-one association to OrderData
	@OneToMany(mappedBy="packageData")
	private List<OrderData> orderData;

	//bi-directional many-to-one association to PackageService
	@OneToMany(mappedBy="packageData")
	private List<PackageService> packageServices;

	//bi-directional many-to-many association to Service
	@ManyToMany(mappedBy="packageData")
	private List<Service> services;

	public PackageData() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
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

	public OrderData addOrderData(OrderData orderData) {
		getOrderData().add(orderData);
		orderData.setPackageData(this);

		return orderData;
	}

	public OrderData removeOrderData(OrderData orderData) {
		getOrderData().remove(orderData);
		orderData.setPackageData(null);

		return orderData;
	}

	public List<PackageService> getPackageServices() {
		return this.packageServices;
	}

	public void setPackageServices(List<PackageService> packageServices) {
		this.packageServices = packageServices;
	}

	public PackageService addPackageService(PackageService packageService) {
		getPackageServices().add(packageService);
		packageService.setPackageData(this);

		return packageService;
	}

	public PackageService removePackageService(PackageService packageService) {
		getPackageServices().remove(packageService);
		packageService.setPackageData(null);

		return packageService;
	}

	public List<Service> getServices() {
		return this.services;
	}

	public void setServices(List<Service> services) {
		this.services = services;
	}

}