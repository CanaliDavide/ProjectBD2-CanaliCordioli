package en.polimi.db2.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the suspended_order database table.
 * 
 */
@Entity
@Table(name="suspended_order")
@NamedQuery(name="SuspendedOrder.findAll", query="SELECT s FROM SuspendedOrder s")
public class SuspendedOrder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idOrder;

	private String namePack;

	private String mail;
	
	public SuspendedOrder() {
	}

	public int getIdOrder() {
		return this.idOrder;
	}

	public String getNamePack() {
		return namePack;
	}

	public void setNamePack(String namePack) {
		this.namePack = namePack;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
}