package com.supinfo.youfood.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.supinfo.youfood.adapter.DateAdapter;

@Entity
@XmlRootElement
@XmlType(propOrder = {"name", "creationDate","meals"})
public class Menu implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name="idMenu")
	@Id @GeneratedValue
	private int id;
	
	@Column(nullable= false,unique=true)
	private String name;

	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name="Meal_Menu", joinColumns = @JoinColumn(name = "idMenu"), inverseJoinColumns = @JoinColumn(name = "idMeal"))
	private List<Meal> meals = new ArrayList<Meal>();
	
	@OneToOne
	@JoinColumn(name="idAdmin")
	private Administrator createdBy;

	private Date creationDate ;
	
	private boolean current = false;
	
	public Menu() {}
	
	public Menu(String name) {
		super();
		this.name = name;
	}
	
	public Menu(String name, List<Meal> meals, Administrator createdBy,	Date creationDate) {
		super();
		this.name = name;
		this.meals = meals;
		this.createdBy = createdBy;
		this.creationDate = creationDate;
	}
	
	public Menu(String name, Administrator createdBy, Date creationDate) {
		super();
		this.name = name;
		this.createdBy = createdBy;
		this.creationDate = creationDate;
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

	@XmlElementWrapper(name="meals")
	@XmlElement(name="meal")
	public List<Meal> getMeals() {
		return meals;
	}

	public void setMeals(List<Meal> meals) {
		this.meals = meals;
	}

	@XmlTransient
	public Administrator getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Administrator createdBy) {
		this.createdBy = createdBy;
	}

	@XmlElement
	@XmlJavaTypeAdapter(DateAdapter.class)
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@XmlTransient
	public boolean isCurrent() {
		return current;
	}

	public void setCurrent(boolean current) {
		this.current = current;
	}
	
}