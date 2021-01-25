package com.cognizant.truyum.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name="menu_item")
public class MenuItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="me_id")
	private int id;
	
	@Size(min = 2, max = 100, message = "Item Name should have characters between 2 and 100")
	@Column(name="me_name")
	private String name;
	
	@PositiveOrZero(message="Item Price should be greater than or equal to zero")
	@Column(name="me_price")
	private BigDecimal price;
	
	@Column(name="me_active")
	private String active;
	
	@Temporal(TemporalType.DATE)
	@Column(name="me_date_of_launch")
	private Date dateOfLaunch;
	
	@Column(name="me_category")
	private String category;
	
	@Column(name="me_free_delivery")
	private String freeDelivery;
	
	public MenuItem(int id, String name, BigDecimal price, String active, Date dateOfLaunch, String category,
			String freeDelivery) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.active = active;
		this.dateOfLaunch = dateOfLaunch;
		this.category = category;
		this.freeDelivery = freeDelivery;
	}
	public MenuItem() {		
		super();
	}
}
