package com.supinfo.youfood.dao;

/**
 * 
 * @author Charlie
 * @version 1.0
 *
 * @param <T>
 */
public interface DAO<T> {

	public void create(T t);
	public T findId(int id);
	public void update(T t);
	public void finalize();
}
