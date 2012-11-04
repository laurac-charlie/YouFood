package com.supinfo.youfood.servlet;

import java.io.IOException;
import java.io.PrintWriter;

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
 * Servlet implementation class UpdateUserServlet
 */
@WebServlet("/users/update")
public class UpdateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateUserServlet() {
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
		try
		{
			if(request.getSession().getAttribute("loggedUser") == null || !(request.getSession().getAttribute("loggedUser") instanceof Administrator))
				throw new Exception("Not allowed");
			
			if(request.getParameter("adminId") == null && request.getParameter("waiterId") == null)
				throw new Exception("aucun id ");
			
			if(!(request.getParameter("adminId") == null))
			{
				int idAdmin = Integer.parseInt(request.getParameter("adminId").toString());
				Administrator admin = AdministratorDAO.getInstance().findId(idAdmin);
				
				if(request.getParameter("lastName") == null || request.getParameter("firstName") == null || request.getParameter("login") == null || request.getParameter("password") == null)
					throw new Exception("données manquantes");
				
				admin.setLastName(new String(request.getParameter("lastName").getBytes(),"UTF-8"));
				admin.setFirstName(new String(request.getParameter("firstName").getBytes(),"UTF-8"));
				admin.setLogin(new String(request.getParameter("login").getBytes(),"UTF-8"));
				admin.setPassword(new String(request.getParameter("password").getBytes(),"UTF-8"));
				
				AdministratorDAO.getInstance().update(admin);
				
				response.setContentType("text/html");
		       	PrintWriter out = response.getWriter();
				out.println("<div>L'administrateur a été mis à jour.</div>");
		       	out.close();
			}
			
			if(!(request.getParameter("waiterId") == null))
			{
				int idWaiter = Integer.parseInt(request.getParameter("waiterId").toString());
				Waiter waiter = WaiterDAO.getInstance().findId(idWaiter);
				
				if(request.getParameter("lastName").toString().isEmpty() || request.getParameter("firstName").toString().isEmpty()|| request.getParameter("login").toString().isEmpty() || request.getParameter("password").toString().isEmpty() || request.getParameter("resId").toString().isEmpty())
					throw new Exception("données manquantes");
				
				int idRes = Integer.parseInt(request.getParameter("resId").toString());;
				Restaurant res = RestaurantDAO.getInstance().findId(idRes);
				
				waiter.setLastName(request.getParameter("lastName").toString());
				waiter.setFirstName(request.getParameter("firstName").toString());
				waiter.setLogin(request.getParameter("login").toString());
				waiter.setPassword(request.getParameter("password").toString());
				waiter.setTheRestaurant(res);
				
				WaiterDAO.getInstance().update(waiter);
				
				response.setContentType("text/html");
		       	PrintWriter out = response.getWriter();
				out.println("<div>Le serveur a été mis à jour.</div>");
		       	out.close();
			}
		}
		catch(Exception e)
		{
			//Sending html content to the page
			response.setContentType("text/html");
	       	PrintWriter out = response.getWriter();
			out.println("<div>  La mise à jour a échoué.</div>");
	       	out.close();
		}
	}

}
