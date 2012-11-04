package com.supinfo.youfood.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@XmlRootElement
public class ProductType implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name="idType")
	@Id @GeneratedValue
	private int id;

	@Column(nullable= false,unique=true)
	private String name;

	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name="Meal_Type", joinColumns = @JoinColumn(name = "idType"), inverseJoinColumns = @JoinColumn(name = "idMeal"))
	private List<Meal> meals = new ArrayList<Meal>();;
	
	public ProductType() {}

	public ProductType(String name) {
		this.name = name;
	}

	@XmlAttribute
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@XmlElement
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlTransient
	public List<Meal> getMeals() {
		return meals;
	}

	public void setMeals(List<Meal> meals) {
		this.meals = meals;
	}


}