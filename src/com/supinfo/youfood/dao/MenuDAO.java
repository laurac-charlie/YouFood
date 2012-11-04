package com.supinfo.youfood.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.supinfo.youfood.entity.Meal;
import com.supinfo.youfood.entity.Menu;
import com.supinfo.youfood.util.PersistenceManager;

public class MenuDAO implements DAO<Menu> {

	private EntityManager em;
	
	private MenuDAO(){
		this.em = PersistenceManager.getEntityManagerFactory().createEntityManager();
	}
	
	public static MenuDAO getInstance(){
		return new MenuDAO();
	}
	
	//Create a Menu
	public void create(Menu menu){
		try
		{
			this.em.getTransaction().begin();
			this.em.persist(menu);
			this.em.getTransaction().commit();
		} 
		finally 
		{
			if(this.em.getTransaction().isActive()) this.em.getTransaction().rollback();
		}
	}
	
	//Get the Menu by id
	public Menu findId(int id){
		Query query = this.em.createQuery("SELECT m FROM Menu m WHERE m.id = :id");
		query.setParameter("id", id);
		try
		{
			return (Menu) query.getSingleResult();
		}
		catch(NoResultException e)
		{
			return null;
		}
	}
	
	//Obtenir la liste des types de produits
	@SuppressWarnings("unchecked")
	public List<Menu> getAllMenu(){
		Query query = this.em.createQuery("SELECT m FROM Menu m ORDER BY m.id");		
		try
		{
			return (List<Menu>)query.getResultList();
		}
		catch(NoResultException e)
		{
			return null;
		}
	}
	
	public void update(Menu menu){
		try
		{
			this.em.getTransaction().begin();
		    Menu m = this.em.find(Menu.class,menu.getId());
		    m.setName(menu.getName());
		    //m.setMeals(menu.getMeals());
		    this.em.merge(m);
		    this.em.getTransaction().commit();
		}
	    finally 
		{
			if(this.em.getTransaction().isActive()) 
				this.em.getTransaction().rollback();
		}
	}
	
	public void addMeal(int idMenu, int idMeal){
		try
		{
			this.em.getTransaction().begin();
		    Menu m = this.em.find(Menu.class,idMenu);
		    m.getMeals().add(this.em.find(Meal.class, idMeal));
		    this.em.merge(m);
		    this.em.getTransaction().commit();
		}
	    finally 
		{
			if(this.em.getTransaction().isActive()) 
				this.em.getTransaction().rollback();
		}
	}
	
	public void removeMeal(int idMenu, int idMeal){
		try
		{
			this.em.getTransaction().begin();
		    Menu m = this.em.find(Menu.class,idMenu);
		    m.getMeals().remove(this.em.find(Meal.class, idMeal));
		    this.em.merge(m);
		    this.em.getTransaction().commit();
		}
	    finally 
		{
			if(this.em.getTransaction().isActive()) 
				this.em.getTransaction().rollback();
		}
	}
	
	//Delete a Menu
	public void deleteMenu(Menu menu){
		Query query = this.em.createQuery("DELETE FROM Menu m WHERE m.id = :id");
		query.setParameter("id", menu.getId());
		
		this.em.getTransaction().begin();
		query.executeUpdate();
		this.em.getTransaction().commit();
	}
	
	public void setCurrent(Menu menu)
	{
		Query query = this.em.createQuery("UPDATE FROM Menu m SET m.current = false");
		Query query2 = this.em.createQuery("UPDATE FROM Menu m SET m.current = true WHERE m.id = :id");
		query2.setParameter("id",menu.getId());
		
		this.em.getTransaction().begin();
		query.executeUpdate();
		query2.executeUpdate();
		this.em.getTransaction().commit();
	}
	
	public Menu getCurrent()
	{
		Query query = this.em.createQuery("SELECT m FROM Menu m WHERE m.current = true");	
		try
		{
			return (Menu)query.getSingleResult();
		}
		catch(NoResultException e)
		{
			return null;
		}
	}
	
	public void finalize(){
		this.em.close();
	}
}
