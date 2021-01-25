/*
 * package com.cognizant.truyum.model;
 * 
 * import javax.validation.constraints.NotBlank; import
 * javax.validation.constraints.NotNull;
 * 
 * import org.slf4j.Logger; import org.slf4j.LoggerFactory;
 * 
 * public class Users { public Users() { super(); LOGGER.info("In Users Class");
 * } public Users(String username, String password) { super(); this.username =
 * username; this.password = password; LOGGER.info("In Users Class"); } final
 * Logger LOGGER = LoggerFactory.getLogger(Users.class);
 * 
 * @NotNull
 * 
 * @NotBlank private String username;
 * 
 * @NotNull
 * 
 * @NotBlank private String password;
 * 
 * public String getUsername() { return username; } public void
 * setUsername(String username) { this.username = username; } public String
 * getPassword() { return password; } public void setPassword(String password) {
 * this.password = password; }
 * 
 * }
 */

package com.cognizant.truyum.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name="user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="us_id")
	private int id;
	
	@NotBlank( message ="userName must not be blank")
	@NotNull( message ="Name must not be null")
	@Size(max = 60, message = "Name should not be more than 60 Characters")
	@Column(name="us_username")
	private String userName;
	
	@Size(max = 200, message = "Password should not be greater than 200 characters")
	@Column(name="us_password")
	private String password;
	
	@ManyToMany( fetch = FetchType.EAGER )
	@JoinTable(name = "user_role",
	joinColumns = @JoinColumn(name = "ur_us_id"),
	inverseJoinColumns = @JoinColumn(name = "ur_ro_id"))
	private Set<Role> roleList;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "cart",
	joinColumns = @JoinColumn(name = "ct_us_id"),
	inverseJoinColumns = @JoinColumn(name = "ct_pr_id"))
	private Set<MenuItem> menuItemList;
	
}

