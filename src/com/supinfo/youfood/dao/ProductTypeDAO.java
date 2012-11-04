package com.supinfo.youfood.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.supinfo.youfood.entity.ProductType;
import com.supinfo.youfood.util.PersistenceManager;

public class ProductTypeDAO implements DAO<ProductType>{

	private EntityManager em;
	
	private ProductTypeDAO(){
		this.em = PersistenceManager.getEntityManagerFactory().createEntityManager();
	}
	
	public static ProductTypeDAO getInstance(){
		return new ProductTypeDAO();
	}
	
	//Create a ProductType
	public void create(ProductType type){
		try
		{
			this.em.getTransaction().begin();
			this.em.persist(type);
			this.em.getTransaction().commit();
		} 
		finally 
		{
			if(this.em.getTransaction().isActive()) this.em.getTransaction().rollback();
		}
	}
	
	//Get the ProductType by id
	public ProductType findId(int id){
		Query query = this.em.createQuery("SELECT p FROM ProductType p WHERE p.id = :id");
		query.setParameter("id", id);
		try
		{
			return (ProductType) query.getSingleResult();
		}
		catch(NoResultException e)
		{
			return null;
		}
	}
	
	//Obtenir la liste des types de produits
	@SuppressWarnings("unchecked")
	public List<ProductType> getAllProductType(){
		Query query = this.em.createQuery("SELECT p FROM ProductType p ORDER BY p.id");		
		try
		{
			return (List<ProductType>)query.getResultList();
		}
		catch(NoResultException e)
		{
			return null;
		}
	}
	
	public void update(ProductType type){
		try
		{
			this.em.getTransaction().begin();
		    ProductType p = this.em.find(ProductType.class,type.getId());
		    p.setName(type.getName());
		    this.em.getTransaction().commit();
		}
	    finally 
		{
			if(this.em.getTransaction().isActive()) this.em.getTransaction().rollback();
		}
	}
	
	//Delete an ProductType
	public void deleteProductType(ProductType type){
		Query query = this.em.createQuery("DELETE FROM ProductType p WHERE p.id = :id");
		query.setParameter("id", type.getId());
		
		try
		{
			this.em.getTransaction().begin();
			ProductType t = this.em.find(ProductType.class, type.getId());
			t.getMeals().clear();
			this.em.merge(t);
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
