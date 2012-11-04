package com.supinfo.youfood.servlet;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.supinfo.youfood.dao.MenuDAO;
import com.supinfo.youfood.entity.Administrator;
import com.supinfo.youfood.entity.Menu;

/**
 * Servlet implementation class NewMenuServlet
 */
@WebServlet("/menu/new")
public class NewMenuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewMenuServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect(getServletContext().getContextPath()+"/home");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Ajouter un menu
		try
		{
			if(request.getSession().getAttribute("loggedUser") == null || !(request.getSession().getAttribute("loggedUser") instanceof Administrator))
			{
				throw new Exception("Not allowed");
			}
			if(request.getParameter("nom").toString().isEmpty()) throw new IllegalArgumentException("");
			
			Date date = new Date((new java.util.Date()).getTime());
			
			MenuDAO.getInstance().create(new Menu(new String(request.getParameter("nom").getBytes(),"UTF-8"),(Administrator)request.getSession().getAttribute("loggedUser"),date));
		}
		catch(Exception e)
		{}
	}

}
