package en.polimi.db2.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the order_data database table.
 * 
 */
@Entity
@Table(name="order_data")
@NamedQuery(name="OrderData.findAll", query="SELECT o FROM OrderData o")
public class OrderData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.DATE)
	private Date dataActivation;

	private Timestamp dateTime;

	private boolean isValid;

	private int numberOfInvalid;

	private float totalCost;

	//bi-directional many-to-many association to OptionalData
	@ManyToMany
	@JoinTable(
		name="order_option"
		, joinColumns={
			@JoinColumn(name="idOrder")
			}
		, inverseJoinColumns={
			@JoinColumn(name="idOptional")
			}
		)
	private List<OptionalData> optionalData;

	//bi-directional many-to-one association to PackageData
	@ManyToOne
	@JoinColumn(name="idPackage")
	private PackageData packageData;

	//bi-directional many-to-one association to UserData
	@ManyToOne
	@JoinColumn(name="idUser")
	private UserData userData;

	//bi-directional many-to-one association to Validityperiod
	@ManyToOne
	@JoinColumn(name="idValidityPeriod")
	private Validityperiod validityperiod;

	//bi-directional many-to-one association to OrderOption
	@OneToMany(mappedBy="orderData")
	private List<OrderOption> orderOptions;

	public OrderData() {
	}
	
	

	public OrderData(Date dataActivation, Timestamp dateTime, boolean isValid, int numberOfInvalid, float totalCost,
			List<OptionalData> optionalData, PackageData packageData, UserData userData, Validityperiod validityperiod) {
		this.dataActivation = dataActivation;
		this.dateTime = dateTime;
		this.isValid = isValid;
		this.numberOfInvalid = numberOfInvalid;
		this.totalCost = totalCost;
		this.optionalData = optionalData;
		this.packageData = packageData;
		this.userData = userData;
		this.validityperiod = validityperiod;
	}



	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDataActivation() {
		return this.dataActivation;
	}

	public void setDataActivation(Date dataActivation) {
		this.dataActivation = dataActivation;
	}

	public Timestamp getDateTime() {
		return this.dateTime;
	}

	public void setDateTime(Timestamp dateTime) {
		this.dateTime = dateTime;
	}

	public boolean getIsValid() {
		return this.isValid;
	}

	public void setIsValid(boolean isValid) {
		this.isValid = isValid;
	}

	public int getNumberOfInvalid() {
		return this.numberOfInvalid;
	}

	public void setNumberOfInvalid(int numberOfInvalid) {
		this.numberOfInvalid = numberOfInvalid;
	}

	public float getTotalCost() {
		return this.totalCost;
	}

	public void setTotalCost(float totalCost) {
		this.totalCost = totalCost;
	}

	public List<OptionalData> getOptionalData() {
		return this.optionalData;
	}

	public void setOptionalData(List<OptionalData> optionalData) {
		this.optionalData = optionalData;
	}

	public PackageData getPackageData() {
		return this.packageData;
	}

	public void setPackageData(PackageData packageData) {
		this.packageData = packageData;
	}

	public UserData getUserData() {
		return this.userData;
	}

	public void setUserData(UserData userData) {
		this.userData = userData;
	}

	public Validityperiod getValidityperiod() {
		return this.validityperiod;
	}

	public void setValidityperiod(Validityperiod validityperiod) {
		this.validityperiod = validityperiod;
	}

	public List<OrderOption> getOrderOptions() {
		return this.orderOptions;
	}

	public void setOrderOptions(List<OrderOption> orderOptions) {
		this.orderOptions = orderOptions;
	}

	public OrderOption addOrderOption(OrderOption orderOption) {
		getOrderOptions().add(orderOption);
		orderOption.setOrderData(this);

		return orderOption;
	}

	public OrderOption removeOrderOption(OrderOption orderOption) {
		getOrderOptions().remove(orderOption);
		orderOption.setOrderData(null);

		return orderOption;
	}

}