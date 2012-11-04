package com.supinfo.youfood.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Customer {

	@Column(name="idCustomer")
	@Id @GeneratedValue
	private int id;

	@OneToMany(mappedBy="theCustomer")
	private List<Order> orders;

	@ManyToOne
	@JoinColumn
	private Waiter responsableWaiter;
	
	public Customer() {}

	public int getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Waiter getResponsableWaiter() {
		return responsableWaiter;
	}

	public void setResponsableWaiter(Waiter responsableWaiter) {
		this.responsableWaiter = responsableWaiter;
	}

}