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
public class Waiter extends User{

	@Column(name="idWaiter")
	@Id @GeneratedValue
	private int id;

	@Column(nullable= false,unique=true)
	private String login;

	private String password;

	private String firstName;

	private String lastName;
	@ManyToOne
	@JoinColumn
	private Restaurant theRestaurant;
	@OneToMany(mappedBy="assignedWaiter")
	private List<Zone> zones;
	
	public Waiter() {}
	
	public Waiter(String login, String password, String firstName,String lastName) 
	{
		this.login = login;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public Waiter(String login, String password, String firstName,String lastName,Restaurant rest) 
	{
		this.login = login;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.theRestaurant = rest;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Restaurant getTheRestaurant() {
		return theRestaurant;
	}

	public void setTheRestaurant(Restaurant theRestaurant) {
		this.theRestaurant = theRestaurant;
	}

	public List<Zone> getZones() {
		return zones;
	}

	public void setZones(List<Zone> zones) {
		this.zones = zones;
	}
	
	
}