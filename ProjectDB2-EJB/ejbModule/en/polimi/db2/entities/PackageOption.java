package en.polimi.db2.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the package_option database table.
 * 
 */
@Entity
@Table(name="package_option")
@NamedQuery(name="PackageOption.findAll", query="SELECT p FROM PackageOption p")
public class PackageOption implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PackageOptionPK id;

	//bi-directional many-to-one association to PackageData
	@ManyToOne
	@JoinColumn(name="idPackage")
	private PackageData packageData;

	//bi-directional many-to-one association to OptionalData
	@ManyToOne
	@JoinColumn(name="idOptional")
	private OptionalData optionalData;

	public PackageOption() {
	}

	public PackageOptionPK getId() {
		return this.id;
	}

	public void setId(PackageOptionPK id) {
		this.id = id;
	}

	public PackageData getPackageData() {
		return this.packageData;
	}

	public void setPackageData(PackageData packageData) {
		this.packageData = packageData;
	}

	public OptionalData getOptionalData() {
		return this.optionalData;
	}

	public void setOptionalData(OptionalData optionalData) {
		this.optionalData = optionalData;
	}

}