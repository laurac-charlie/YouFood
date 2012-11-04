package com.supinfo.youfood.servlet;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.supinfo.youfood.dao.MealDAO;
import com.supinfo.youfood.entity.Administrator;
import com.supinfo.youfood.entity.Meal;

/**
 * Servlet implementation class NewMealServlet
 */
@WebServlet("/meals/new")
public class NewMealServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewMealServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/meals/new.jsp").forward(request, response);
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
			
			Administrator admin = (Administrator)request.getSession().getAttribute("loggedUser");
			Date date = new Date((new java.util.Date()).getTime());
			float price = 0;
			boolean dispo = false;
			
			String select[] = request.getParameterValues("disponible"); 
			if (select != null && select.length != 0) 
			{
				for (String s : select) 
					if(s.equals("disponible")) dispo = true;
			}
			
			try
				{price = new Float((String)request.getParameter("prix"));}
			catch(NumberFormatException e)
				{price = 0;}
			
			if(!request.getParameter("nom").toString().isEmpty() || !request.getParameter("prix").toString().isEmpty() || !request.getParameter("description").toString().isEmpty())
			{
				MealDAO.getInstance().create(new Meal((String)request.getParameter("nom"),(String)request.getParameter("description"),price,dispo,admin,date,date));
				response.sendRedirect(getServletContext().getContextPath() + "/meals/list.jsp");
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
