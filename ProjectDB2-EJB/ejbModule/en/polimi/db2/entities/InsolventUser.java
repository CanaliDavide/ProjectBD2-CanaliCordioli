package en.polimi.db2.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the insolvent_user database table.
 * 
 */
@Entity
@Table(name="insolvent_user")
@NamedQuery(name="InsolventUser.findAll", query="SELECT i FROM InsolventUser i")
public class InsolventUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idUser;

	private String mail;

	private String username;

	public InsolventUser() {
	}

	public int getIdUser() {
		return this.idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}