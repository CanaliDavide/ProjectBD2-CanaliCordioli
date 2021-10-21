package en.polimi.db2.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the alert database table.
 * 
 */
@Entity
@NamedQuery(name="Alert.findAll", query="SELECT a FROM Alert a")
public class Alert implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String email;

	private Timestamp lastReject;

	private float totalCost;

	private String username;

	//bi-directional many-to-one association to UserData
	@ManyToOne
	@JoinColumn(name="idUser")
	private UserData userData;

	public Alert() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Timestamp getLastReject() {
		return this.lastReject;
	}

	public void setLastReject(Timestamp lastReject) {
		this.lastReject = lastReject;
	}

	public float getTotalCost() {
		return this.totalCost;
	}

	public void setTotalCost(float totalCost) {
		this.totalCost = totalCost;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public UserData getUserData() {
		return this.userData;
	}

	public void setUserData(UserData userData) {
		this.userData = userData;
	}

}