package com.supinfo.youfood.ressource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.supinfo.youfood.dao.ProductTypeDAO;
import com.supinfo.youfood.entity.ProductType;

@Path("/type")
public class ProductTypeRessources {

	@GET
	public List<ProductType> getTypes()
	{
		return ProductTypeDAO.getInstance().getAllProductType();
	}
}
