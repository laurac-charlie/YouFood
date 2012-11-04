package com.supinfo.youfood.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Zone {

	@Id @GeneratedValue
	@Column(name="idZone")
	private Integer id;
	@ManyToOne
	@JoinColumn
	private Waiter assignedWaiter;
	@ManyToOne
	@JoinColumn
	private Restaurant theRestaurant;
	
	public Zone(){}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Waiter getAssignedWaiter() {
		return assignedWaiter;
	}

	public void setAssignedWaiter(Waiter assignedWaiter) {
		this.assignedWaiter = assignedWaiter;
	}

	public Restaurant getTheRestaurant() {
		return theRestaurant;
	}

	public void setTheRestaurant(Restaurant theRestaurant) {
		this.theRestaurant = theRestaurant;
	}


}