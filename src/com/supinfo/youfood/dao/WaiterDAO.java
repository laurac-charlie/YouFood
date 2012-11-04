package com.supinfo.youfood.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.supinfo.youfood.entity.Restaurant;
import com.supinfo.youfood.entity.Waiter;
import com.supinfo.youfood.util.PersistenceManager;


public class WaiterDAO implements DAO<Waiter>{

	private EntityManager em;
	
	private WaiterDAO(){
		this.em = PersistenceManager.getEntityManagerFactory().createEntityManager();
	}
	
	public static WaiterDAO getInstance(){
		return new WaiterDAO();
	}
	
	//Create a Waiter
	public void create(Waiter waiter){
		try
		{
			this.em.getTransaction().begin();
			this.em.persist(waiter);
			this.em.getTransaction().commit();
		} 
		finally 
		{
			if(this.em.getTransaction().isActive()) this.em.getTransaction().rollback();
		}
	}
	
	//Authenticate an Waiter
	public Waiter authentication(String login, String password){
		
		Query query = this.em.createQuery("SELECT w FROM Waiter w WHERE w.login = :login and w.password = :password");
		query.setParameter("login", login).setParameter("password", password);
		try
		{
			return (Waiter)query.getSingleResult();
		}
		catch(NoResultException e)
		{
			return null;
		}
			
	}
	
	//Get the Waiter by id
	public Waiter findId(int id){
		Query query = this.em.createQuery("SELECT w FROM Waiter w WHERE w.id = :id");
		query.setParameter("id", id);
		try
		{
			return (Waiter) query.getSingleResult();
		}
		catch(NoResultException e)
		{
			return null;
		}
	}
	
	//Obtenir la liste des serveurs
	@SuppressWarnings("unchecked")
	public List<Waiter> getAllWaiter(){
		Query query = this.em.createQuery("SELECT w FROM Waiter w LEFT JOIN fetch w.theRestaurant r");		
		try
		{
			List<Waiter> w = (List<Waiter>)query.getResultList();
			return w;
		}
		catch(NoResultException e)
		{
			return null;
		}
	}
	
	public void update(Waiter waiter){
		try
		{
			this.em.getTransaction().begin();
		    Waiter w = this.em.find(Waiter.class,waiter.getId());
		    w.setFirstName(waiter.getFirstName());
		    w.setLastName(waiter.getLastName());
		    w.setLogin(waiter.getLogin());
		    w.setPassword(waiter.getPassword());
		    w.setTheRestaurant(this.em.find(Restaurant.class, waiter.getTheRestaurant().getId()));
		    this.em.getTransaction().commit();
		}
	    finally 
		{
			if(this.em.getTransaction().isActive()) this.em.getTransaction().rollback();
		}
	}
	
	//Delete an Waiter
	public void deleteWaiter(Waiter waiter){
		Query query = this.em.createQuery("DELETE FROM Waiter w WHERE w.id = :id");
		query.setParameter("id", waiter.getId());
		
		this.em.getTransaction().begin();
		query.executeUpdate();
		this.em.getTransaction().commit();
	}
	
	public void finalize(){
		this.em.close();
	}
}
