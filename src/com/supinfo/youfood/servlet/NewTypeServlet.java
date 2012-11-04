package com.supinfo.youfood.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.supinfo.youfood.dao.ProductTypeDAO;
import com.supinfo.youfood.entity.Administrator;
import com.supinfo.youfood.entity.ProductType;

/**
 * Servlet implementation class NewTypeServlet
 */
@WebServlet("/productType/new")
public class NewTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewTypeServlet() {
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
		//Ajouter un type
		try
		{
			if(request.getSession().getAttribute("loggedUser") == null || !(request.getSession().getAttribute("loggedUser") instanceof Administrator))
			{
				throw new Exception("Not allowed");
			}
			if(request.getParameter("nom").toString().isEmpty()) throw new IllegalArgumentException("");
			
			ProductTypeDAO.getInstance().create(new ProductType(new String(request.getParameter("nom").getBytes(),"UTF-8")));
		}
		catch(Exception e)
		{}
	}

}
