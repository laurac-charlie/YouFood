package com.supinfo.youfood.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Order {

	@Column(name="idOrder")
	@Id @GeneratedValue
	private int id;

	@OneToMany
	@JoinTable(joinColumns = @JoinColumn(name = "idOrder"), inverseJoinColumns = @JoinColumn(name = "idMeal"))
	private List<Meal> meals= new ArrayList<Meal>();;

	private float totalPrice;

	@ManyToOne
	@JoinColumn
	private Customer theCustomer;
	
	private Date orderDate;

	public Order() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Meal> getMeals() {
		return meals;
	}

	public void setMeals(List<Meal> meals) {
		this.meals = meals;
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Customer getTheCustomer() {
		return theCustomer;
	}

	public void setTheCustomer(Customer theCustomer) {
		this.theCustomer = theCustomer;
	}


	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public float getCalculPrice() {
		return 0;
	}

}