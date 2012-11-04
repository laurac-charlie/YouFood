package com.supinfo.youfood.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import com.supinfo.youfood.entity.Administrator;
import com.supinfo.youfood.util.PersistenceManager;

public class AdministratorDAO implements DAO<Administrator>{

	private EntityManager em;
	
	private AdministratorDAO(){
		this.em = PersistenceManager.getEntityManagerFactory().createEntityManager();
	}
	
	public static AdministratorDAO getInstance(){
		return new AdministratorDAO();
	}
	
	//Create a Administrator
	public void create(Administrator admin){
		try
		{
			this.em.getTransaction().begin();
			this.em.persist(admin);
			this.em.getTransaction().commit();
		} 
		finally 
		{
			if(this.em.getTransaction().isActive()) this.em.getTransaction().rollback();
		}
	}
	
	//Authenticate an Administrator
	public Administrator authentication(String login, String password){
		
		Query query = this.em.createQuery("SELECT a FROM Administrator a WHERE a.login = :login and a.password = :password");
		query.setParameter("login", login).setParameter("password", password);
		try
		{
			return (Administrator)query.getSingleResult();
		}
		catch(NoResultException e)
		{
			return null;
		}
			
	}
	
	//Get the Administrator by id
	public Administrator findId(int id){
		Query query = this.em.createQuery("SELECT a FROM Administrator a WHERE a.id = :id");
		query.setParameter("id", id);
		try
		{
			return (Administrator) query.getSingleResult();
		}
		catch(NoResultException e)
		{
			return null;
		}
	}
	
	//Obtenir la liste des adminsitrateurs
	@SuppressWarnings("unchecked")
	public List<Administrator> getAllAdministrator(){
		Query query = this.em.createQuery("SELECT a FROM Administrator a");		
		try
		{
			return (List<Administrator>)query.getResultList();
		}
		catch(NoResultException e)
		{
			return null;
			
		}
	}
	
	public void update(Administrator admin){
		try
		{
			this.em.getTransaction().begin();
		    Administrator a = this.em.find(Administrator.class,admin.getId());
		    a.setFirstName(admin.getFirstName());
		    a.setLastName(admin.getLastName());
		    a.setLogin(admin.getLogin());
		    a.setPassword(admin.getPassword());
		    this.em.getTransaction().commit();
		}
	    finally 
		{
			if(this.em.getTransaction().isActive()) this.em.getTransaction().rollback();
		}
	}
	
	//Delete an Administrator
	public void deleteAdministrator(Administrator admin){
		Query query = this.em.createQuery("DELETE FROM Administrator a WHERE a.id = :id");
		query.setParameter("id", admin.getId());
		
		this.em.getTransaction().begin();
		query.executeUpdate();
		this.em.getTransaction().commit();
	}
	
	public void finalize(){
		this.em.close();
	}
}
