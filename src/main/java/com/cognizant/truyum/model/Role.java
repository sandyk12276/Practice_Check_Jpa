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
@Table(name="role")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ro_id")
	private int id;
	
	@NotBlank
	@NotNull
	@Size(max = 45, message = "Name should not be more than 45 Characters")
	@Column(name="ro_name")
	private String roleName;
	
	@ManyToMany(mappedBy = "roleList", fetch = FetchType.EAGER)
	private Set<User> userList;
	
}
