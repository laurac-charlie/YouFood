package com.supinfo.youfood.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.supinfo.youfood.dao.RestaurantDAO;
import com.supinfo.youfood.entity.Administrator;
import com.supinfo.youfood.entity.Restaurant;

/**
 * Servlet implementation class UpdateRestaurantServlet
 */
@WebServlet("/restaurant/update")
public class UpdateRestaurantServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateRestaurantServlet() {
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
			
			if(request.getParameter("resId").toString() == null)
				throw new Exception("aucun id ");
			
			int idRes = Integer.parseInt(request.getParameter("resId").toString());
			Restaurant res = RestaurantDAO.getInstance().findId(idRes);
			
			if(request.getParameter("nom") == null || request.getParameter("adress") == null || request.getParameter("description") == null || request.getParameter("gerant") == null)
				throw new Exception("données manquantes");
			
			res.setName(new String(request.getParameter("nom").getBytes(),"UTF-8"));
			res.setAdress(new String(request.getParameter("adress").getBytes(),"UTF-8"));
			res.setGerant(new String(request.getParameter("gerant").getBytes(),"UTF-8"));
			res.setDescription(new String(request.getParameter("description").getBytes(),"UTF-8"));
			res.setNbOfSeat(Integer.parseInt(request.getParameter("nbOfSeat").toString()));
			
			RestaurantDAO.getInstance().update(res);
			
			response.setContentType("text/html");
	       	PrintWriter out = response.getWriter();
			out.println("<div>Le resaurant a été mis à jour.</div>");
	       	out.close();
			
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
