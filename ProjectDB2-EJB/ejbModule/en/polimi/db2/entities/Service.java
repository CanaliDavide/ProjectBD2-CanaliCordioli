package en.polimi.db2.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the service database table.
 * 
 */
@Entity
@NamedQueries({ 
	@NamedQuery(name = "Service.findAll", query = "SELECT s FROM Service s"),
	@NamedQuery(name = "Service.findByIds", query = "SELECT s FROM Service s where s.id in ?1") 
})
public class Service implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private float feeGiga;

	private float feeMinutes;

	private float feeSms;

	private float giga;

	private int minutes;

	private int sms;

	private String type;

	// bi-directional many-to-one association to PackageService
	@OneToMany(mappedBy = "service")
	private List<PackageService> packageServices;

	// bi-directional many-to-many association to PackageData
	@ManyToMany
	@JoinTable(name = "package_service", joinColumns = { @JoinColumn(name = "idService") }, inverseJoinColumns = {
			@JoinColumn(name = "idPackage") })
	private List<PackageData> packageData;

	public Service() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getFeeGiga() {
		return this.feeGiga;
	}

	public void setFeeGiga(float feeGiga) {
		this.feeGiga = feeGiga;
	}

	public float getFeeMinutes() {
		return this.feeMinutes;
	}

	public void setFeeMinutes(float feeMinutes) {
		this.feeMinutes = feeMinutes;
	}

	public float getFeeSms() {
		return this.feeSms;
	}

	public void setFeeSms(float feeSms) {
		this.feeSms = feeSms;
	}

	public float getGiga() {
		return this.giga;
	}

	public void setGiga(float giga) {
		this.giga = giga;
	}

	public int getMinutes() {
		return this.minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public int getSms() {
		return this.sms;
	}

	public void setSms(int sms) {
		this.sms = sms;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<PackageService> getPackageServices() {
		return this.packageServices;
	}

	public void setPackageServices(List<PackageService> packageServices) {
		this.packageServices = packageServices;
	}

	public PackageService addPackageService(PackageService packageService) {
		getPackageServices().add(packageService);
		packageService.setService(this);

		return packageService;
	}

	public PackageService removePackageService(PackageService packageService) {
		getPackageServices().remove(packageService);
		packageService.setService(null);

		return packageService;
	}

	public List<PackageData> getPackageData() {
		return this.packageData;
	}

	public void setPackageData(List<PackageData> packageData) {
		this.packageData = packageData;
	}

}