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
	
	private int idUser;

	private String email;

	private Timestamp lastReject;

	private float totalCost;

	private String username;

	
	public Alert() {
	}
	
	

	public Alert(String email, Timestamp lastReject, float totalCost, String username, int idUser) {
		this.email = email;
		this.idUser = idUser;
		this.lastReject = lastReject;
		this.totalCost = totalCost;
		this.username = username;
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

	public int getIdUser() {
		return idUser;
	}



	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

}