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

	//bi-directional many-to-many association to OptionalData
	@ManyToMany
	@JoinTable(
		name="package_option"
		, joinColumns={
			@JoinColumn(name="idPackage")
			}
		, inverseJoinColumns={
			@JoinColumn(name="idOptional")
			}
		)
	private List<OptionalData> optionalData;

	//bi-directional many-to-one association to PackageService
	@OneToMany(mappedBy="packageData")
	private List<PackageService> packageServices;

	//bi-directional many-to-many association to Service
	@ManyToMany
	@JoinTable(
		name="package_service"
		, joinColumns={
			@JoinColumn(name="idPackage")
			}
		, inverseJoinColumns={
			@JoinColumn(name="idService")
			}
		)
	private List<Service> services;

	//bi-directional many-to-one association to PackageOption
	@OneToMany(mappedBy="packageData")
	private List<PackageOption> packageOptions;
	
	@OneToMany(mappedBy="packageData")
	private List<PackageValidity> packageValidities;
	
	@ManyToMany
	@JoinTable(
			name = "package_validity"
			, joinColumns= {
					@JoinColumn(name="idPackage")
					}
			, inverseJoinColumns= {
					@JoinColumn(name="idValidity")
					}
			)
	private List<Validityperiod> validityPeriods;

	public PackageData() {
	}

	public PackageData(String name, List<OptionalData> optionalData, List<Service> services, List<Validityperiod> validityPeriods) {
		this.name = name;
		this.optionalData = optionalData;
		this.services = services;
		this.validityPeriods = validityPeriods;
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

	public List<OptionalData> getOptionalData() {
		return this.optionalData;
	}

	public void setOptionalData1(List<OptionalData> optionalData) {
		this.optionalData = optionalData;
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

	public List<PackageOption> getPackageOptions() {
		return this.packageOptions;
	}

	public void setPackageOptions(List<PackageOption> packageOptions) {
		this.packageOptions = packageOptions;
	}

	public PackageOption addPackageOption(PackageOption packageOption) {
		getPackageOptions().add(packageOption);
		packageOption.setPackageData(this);

		return packageOption;
	}

	public PackageOption removePackageOption(PackageOption packageOption) {
		getPackageOptions().remove(packageOption);
		packageOption.setPackageData(null);

		return packageOption;
	}

	public List<Validityperiod> getValidityPeriods() {
		return validityPeriods;
	}

	public void setValidityPeriods(List<Validityperiod> validityPeriods) {
		this.validityPeriods = validityPeriods;
	}

}