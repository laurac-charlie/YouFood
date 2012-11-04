package com.supinfo.youfood.dao;

public interface DAO<T> {

	public void create(T t);
	public T findId(int id);
	public void update(T t);
	public void finalize();
}
