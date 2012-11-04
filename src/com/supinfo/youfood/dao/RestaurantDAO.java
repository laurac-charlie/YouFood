package com.supinfo.youfood.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.supinfo.youfood.entity.Restaurant;
import com.supinfo.youfood.util.PersistenceManager;

public class RestaurantDAO implements DAO<Restaurant>{

	private EntityManager em;
	
	private RestaurantDAO(){
		this.em = PersistenceManager.getEntityManagerFactory().createEntityManager();
	}
	
	public static RestaurantDAO getInstance(){
		return new RestaurantDAO();
	}
	
	//Create a Restaurant
	public void create(Restaurant restaurant){
		try
		{
			this.em.getTransaction().begin();
			this.em.persist(restaurant);
			this.em.getTransaction().commit();
		} 
		finally 
		{
			if(this.em.getTransaction().isActive()) this.em.getTransaction().rollback();
		}
	}
	
	//Obtenir la liste des restaurants
	@SuppressWarnings("unchecked")
	public List<Restaurant> getAllRestaurant(){
		Query query = this.em.createQuery("SELECT r FROM Restaurant r");		
		try
		{
			return (List<Restaurant>)query.getResultList();
		}
		catch(NoResultException e)
		{
			return null;
		}
	}
	
	//Get the Restaurant by id
	public Restaurant findId(int id){
		Query query = this.em.createQuery("SELECT r FROM Restaurant r LEFT JOIN fetch r.photo p where r.id = :id");
		query.setParameter("id", id);
		try
		{
			return (Restaurant) query.getSingleResult();
		}
		catch(NoResultException e)
		{
			return null;
		}
	}
	
	public void update(Restaurant res){
		try
		{
			this.em.getTransaction().begin();
		    Restaurant r = this.em.find(Restaurant.class,res.getId());
		    r.setName(res.getName());
		    r.setDescription(res.getDescription());
		    r.setAdress(res.getAdress());
		    r.setGerant(res.getGerant());
		    r.setNbOfSeat(res.getNbOfSeat());
		    this.em.getTransaction().commit();
		}
	    finally 
		{
			if(this.em.getTransaction().isActive()) this.em.getTransaction().rollback();
		}
	}
	
	//Delete a Restaurant
	public void deleteRestaurant(Restaurant res){
		Query query2 = null;
		Query query = this.em.createQuery("DELETE FROM Restaurant r WHERE r.id = :id");
		query.setParameter("id", res.getId());
		if(res.getPhoto() != null)
		{
			query2 = this.em.createQuery("DELETE FROM Image i WHERE i.id = :id");
			query2.setParameter("id", res.getPhoto().getId());
		}
		
		this.em.getTransaction().begin();
		query.executeUpdate();
		if(res.getPhoto() != null) query2.executeUpdate();
		this.em.getTransaction().commit();
	}
	
	public void finalize(){
		this.em.close();
	}
}
