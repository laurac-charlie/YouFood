package com.supinfo.youfood.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.supinfo.youfood.dao.AdministratorDAO;
import com.supinfo.youfood.dao.RestaurantDAO;
import com.supinfo.youfood.dao.WaiterDAO;
import com.supinfo.youfood.entity.Administrator;
import com.supinfo.youfood.entity.Restaurant;
import com.supinfo.youfood.entity.Waiter;

/**
 * Servlet implementation class NewUserServlet
 */
@WebServlet("/users/new")
public class NewUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewUserServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/users/new.jsp").forward(request, response);
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
			
			String statut = null;
			boolean isWaiter = true;
			try
			{
				statut = (String)request.getParameter("statut").toString();
				if(statut.equals("admin")) isWaiter = false; 
			}
			catch(Exception e)
				{statut = null;}
			
			if(!request.getParameter("nom").toString().isEmpty() || !request.getParameter("prenom").toString().isEmpty() || !request.getParameter("login").toString().isEmpty() || !request.getParameter("password").toString().isEmpty())
			{
				if(isWaiter)
				{
					Restaurant rest = null;
					try
					{
						int i = Integer.parseInt(request.getParameter("restaurant").toString());
						rest = RestaurantDAO.getInstance().findId(i);
					}
					catch(Exception e)
						{rest = null;}
					
					WaiterDAO.getInstance().create(new Waiter((String)request.getParameter("login"),(String)request.getParameter("password"),(String)request.getParameter("prenom"),request.getParameter("nom").toString(),rest));
					response.sendRedirect(getServletContext().getContextPath() + "/users/list.jsp");
				}
				else
				{
					AdministratorDAO.getInstance().create(new Administrator((String)request.getParameter("login"),(String)request.getParameter("password"),(String)request.getParameter("prenom"),request.getParameter("nom").toString()));
					response.sendRedirect(getServletContext().getContextPath() + "/users/list.jsp");
				}
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
