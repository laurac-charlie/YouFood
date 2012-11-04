package com.supinfo.youfood.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.supinfo.youfood.dao.RestaurantDAO;
import com.supinfo.youfood.entity.Administrator;
import com.supinfo.youfood.entity.Restaurant;

/**
 * Servlet implementation class NewInterventionServlet
 */
@WebServlet("/restaurant/new")
public class NewRestaurantServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewRestaurantServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/restaurant/new.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try
		{
			if(request.getSession().getAttribute("loggedUser") == null || !(request.getSession().getAttribute("loggedUser") instanceof Administrator))
			{
				throw new Exception("Not allowed");
			}
			
			String description = null;
			int seat = 0;
			
			//Verify if a description was sent
			if(request.getParameter("description").toString().isEmpty())
				description = "Pas de description";
			else
				description = request.getParameter("description").toString();
			try
				{seat = Integer.parseInt((String)request.getParameter("nb_seat"));}
			catch(NumberFormatException e)
				{seat = 0;}
			
			if(!request.getParameter("nom").toString().isEmpty() || !request.getParameter("adress").toString().isEmpty() || !request.getParameter("gerant").toString().isEmpty())
			{
				RestaurantDAO.getInstance().create(new Restaurant((String)request.getParameter("nom"),(String)request.getParameter("adress"),(String)request.getParameter("gerant"),description,seat));
				response.sendRedirect(getServletContext().getContextPath() + "/restaurant/list.jsp");
			}
			else
			{
				request.setAttribute("fail", "fail");
				this.doGet(request, response);
			}
		}
		catch(Exception e)
		{
			request.setAttribute("fail", "fail");
			this.doGet(request, response);
		}
		
	}

}
