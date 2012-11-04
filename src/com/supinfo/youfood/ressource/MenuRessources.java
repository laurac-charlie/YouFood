package com.supinfo.youfood.ressource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.supinfo.youfood.dao.MenuDAO;
import com.supinfo.youfood.entity.Menu;

@Path("/menu")
public class MenuRessources {

	@GET
	public Menu getCurrentMenu() {
		return MenuDAO.getInstance().getCurrent();
	}
}
