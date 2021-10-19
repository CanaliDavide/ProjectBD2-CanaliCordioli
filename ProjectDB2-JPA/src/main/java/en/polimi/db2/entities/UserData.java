package en.polimi.db2.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the user_data database table.
 * 
 */
@Entity
@Table(name="user_data")
@NamedQueries({
	@NamedQuery(name = "UserData.findAll", query = "SELECT u FROM UserData u"),
	@NamedQuery(name = "UserData.checkCredentials", query = "SELECT u FROM UserData u WHERE u.username = ?1 AND u.password = ?2")
	})

public class UserData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private boolean isEmployee;

	private boolean isInsolvent;

	private String mail;

	private String password;

	private String username;

	//bi-directional many-to-one association to Alert
	@OneToMany(mappedBy="userData")
	private List<Alert> alerts;

	//bi-directional many-to-one association to OrderData
	@OneToMany(mappedBy="userData")
	private List<OrderData> orderData;

	public UserData() {
	}
	
	public UserData(String username, String password, String mail, boolean isEmployee, boolean isInsolvent) {
		this.username = username;
		this.password = password;
		this.mail = mail;
		this.isEmployee = isEmployee;
		this.isInsolvent = isInsolvent;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Object getIsEmployee() {
		return this.isEmployee;
	}

	public void setIsEmployee(boolean isEmployee) {
		this.isEmployee = isEmployee;
	}

	public Object getIsInsolvent() {
		return this.isInsolvent;
	}

	public void setIsInsolvent(boolean isInsolvent) {
		this.isInsolvent = isInsolvent;
	}

	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Alert> getAlerts() {
		return this.alerts;
	}

	public void setAlerts(List<Alert> alerts) {
		this.alerts = alerts;
	}

	public Alert addAlert(Alert alert) {
		getAlerts().add(alert);
		alert.setUserData(this);

		return alert;
	}

	public Alert removeAlert(Alert alert) {
		getAlerts().remove(alert);
		alert.setUserData(null);

		return alert;
	}

	public List<OrderData> getOrderData() {
		return this.orderData;
	}

	public void setOrderData(List<OrderData> orderData) {
		this.orderData = orderData;
	}

	public OrderData addOrderData(OrderData orderData) {
		getOrderData().add(orderData);
		orderData.setUserData(this);

		return orderData;
	}

	public OrderData removeOrderData(OrderData orderData) {
		getOrderData().remove(orderData);
		orderData.setUserData(null);

		return orderData;
	}
	
    public String toString() {
        return "User id: " + getId() + " name: " + getUsername() + " password: " + getPassword();
    }

}