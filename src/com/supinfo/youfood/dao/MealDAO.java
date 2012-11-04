package com.supinfo.youfood.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.supinfo.youfood.entity.Meal;
import com.supinfo.youfood.entity.ProductType;
import com.supinfo.youfood.util.PersistenceManager;

public class MealDAO implements DAO<Meal>{

	private EntityManager em;
	
	private MealDAO(){
		this.em = PersistenceManager.getEntityManagerFactory().createEntityManager();
	}
	
	public static MealDAO getInstance(){
		return new MealDAO();
	}
	
	//Create a Meal
	public void create(Meal meal){
		try
		{
			this.em.getTransaction().begin();
			this.em.persist(meal);
			this.em.getTransaction().commit();
		} 
		finally 
		{
			if(this.em.getTransaction().isActive()) this.em.getTransaction().rollback();
		}
	}
	
	//Get the Meal by id
	public Meal findId(int id){
		Query query = this.em.createQuery("SELECT m FROM Meal m WHERE m.id = :id");
		query.setParameter("id", id);
		try
		{
			return (Meal) query.getSingleResult();
		}
		catch(NoResultException e)
		{
			return null;
		}
	}
	
	//Obtenir la liste des types de produits
	@SuppressWarnings("unchecked")
	public List<Meal> getAllMeal(){
		Query query = this.em.createQuery("SELECT m FROM Meal m ORDER BY m.id");		
		try
		{
			return (List<Meal>)query.getResultList();
		}
		catch(NoResultException e)
		{
			return null;
		}
	}
	
	public void update(Meal meal){
		try
		{
			java.sql.Date date = new java.sql.Date((new java.util.Date()).getTime());
			this.em.getTransaction().begin();
		    Meal m = this.em.find(Meal.class,meal.getId());
		    m.setName(meal.getName());
		    m.setDescription(meal.getDescription());
		    m.setAvailable(meal.isAvailable());
		    m.setLastModification(date);
		    m.setPrice(meal.getPrice());
		    this.em.getTransaction().commit();
		}
		finally 
		{
			if(this.em.getTransaction().isActive()) this.em.getTransaction().rollback();
		}
	}

	public void addType(int idMeal, int idType){
		try
		{
			this.em.getTransaction().begin();
		    Meal m = this.em.find(Meal.class,idMeal);
		    m.getTypes().add(this.em.find(ProductType.class, idType));
		    this.em.merge(m);
		    this.em.getTransaction().commit();
		}
	    finally 
		{
			if(this.em.getTransaction().isActive()) 
				this.em.getTransaction().rollback();
		}
	}
	
	public void removeType(int idMeal, int idType){
		try
		{
			this.em.getTransaction().begin();
		    Meal m = this.em.find(Meal.class,idMeal);
		    m.getTypes().remove(this.em.find(ProductType.class, idType));
		    this.em.merge(m);
		    this.em.getTransaction().commit();
		}
	    finally 
		{
			if(this.em.getTransaction().isActive()) 
				this.em.getTransaction().rollback();
		}
	}
	
	//Delete a Meal
	public void deleteMeal(Meal meal){
		Query query = this.em.createQuery("DELETE FROM Meal m WHERE m.id = :id");
		query.setParameter("id", meal.getId());
		
		try
		{
			this.em.getTransaction().begin();
			Meal m = this.em.find(Meal.class, meal.getId());
			m.getMenus().clear();
			this.em.merge(m);
			this.em.getTransaction().commit();
			
			this.em.getTransaction().begin();
			query.executeUpdate();
			this.em.getTransaction().commit();
		}
	    finally 
		{
			if(this.em.getTransaction().isActive()) this.em.getTransaction().rollback();
		}
	}
	
	public void finalize(){
		this.em.close();
	}
}
