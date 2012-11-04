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
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
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
@XmlType(propOrder = {"name", "description", 
        "price", "specificities", "available","creationDate","lastModification","types"}) 
public class Meal implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name="idMeal")
	@Id @GeneratedValue
	private int id;

	private String description;

	private float price;

	private String specificities;

	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name="Meal_Type",	joinColumns = @JoinColumn(name = "idMeal"), inverseJoinColumns = @JoinColumn(name = "idType"))
	private List<ProductType> types = new ArrayList<ProductType>();

	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name="Meal_Menu", joinColumns = @JoinColumn(name = "idMeal"), inverseJoinColumns = @JoinColumn(name = "idMenu"))
	private List<Menu> menus = new ArrayList<Menu>();;
	
	@Column(nullable= false,unique=true)
	private String name;

	private boolean available;

	@OneToOne
	@JoinColumn(name="idAdmin")
	private Administrator createdBy;

	private Date creationDate;

	private Date lastModification;
	
	public Meal() {}
	
	public Meal(String name , String description, float price, boolean available) {
		this.description = description;
		this.price = price;
		this.name = name;
		this.available = available;
	}
	
	public Meal(String name, String description, float price, boolean available, Administrator createdBy,Date creationDate, Date lastModification) {
		this.description = description;
		this.price = price;
		this.name = name;
		this.available = available;
		this.createdBy = createdBy;
		this.creationDate = creationDate;
		this.lastModification = lastModification;
	}

	@XmlAttribute
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@XmlElement
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@XmlElement
	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	@XmlElement
	public String getSpecificities() {
		return specificities;
	}

	public void setSpecificities(String specificities) {
		this.specificities = specificities;
	}

	@XmlElementWrapper(name="types")
	@XmlElement(name="type")
	public List<ProductType> getTypes() {
		return types;
	}

	public void setTypes(List<ProductType> types) {
		this.types = types;
	}

	@XmlElement
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement
	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
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

	@XmlElement
	@XmlJavaTypeAdapter(DateAdapter.class)
	public Date getLastModification() {
		return lastModification;
	}

	public void setLastModification(Date lastModification) {
		this.lastModification = lastModification;
	}

	@XmlTransient
	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	
}