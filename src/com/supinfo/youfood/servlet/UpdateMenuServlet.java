package com.supinfo.youfood.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.supinfo.youfood.dao.MealDAO;
import com.supinfo.youfood.dao.MenuDAO;
import com.supinfo.youfood.entity.Administrator;
import com.supinfo.youfood.entity.Meal;
import com.supinfo.youfood.entity.Menu;

/**
 * Servlet implementation class UpdateMenuServlet
 */
@WebServlet("/menu/update")
public class UpdateMenuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateMenuServlet() {
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
			
			if(request.getParameter("type") == null || request.getParameter("menuId") == null)
			{
				throw new Exception("aucun type ou id ");
			}
			
			int idMenu = Integer.parseInt(request.getParameter("menuId").toString());
			Menu menu = MenuDAO.getInstance().findId(idMenu);
			
			if(request.getParameter("type").toString().equals("nom"))
			{
				menu.setName(new String(request.getParameter("nom").getBytes(),"UTF-8"));
				MenuDAO.getInstance().update(menu);
				
				response.setContentType("text/html");
		       	PrintWriter out = response.getWriter();
				out.println("<div>Le nom du menu a été mis à jour.</div>");
		       	out.close();
			}
			
			if(request.getParameter("type").toString().equals("add"))
			{
				int idMeal = Integer.parseInt(request.getParameter("mealId").toString());
				Meal meal = MealDAO.getInstance().findId(idMeal);
				
				if(mealExists(menu.getMeals(),idMeal))
				{
					response.setContentType("text/html");
			       	PrintWriter out = response.getWriter();
					out.println("<div>Le plat " + meal.getName() + " existe déjà dans le menu.</div>");
			       	out.close();
				}
				else
				{
					MenuDAO.getInstance().addMeal(idMenu, idMeal);
					response.setContentType("text/html");
			       	PrintWriter out = response.getWriter();
			       	//out.println("<html><body>");
					out.println("<div >Le plat " + meal.getName() + " a été ajouté au menu.</div>");
					/*out.println("<tr >");
					out.println("<td>" + meal.getName() + "</td>");
					out.println("<td>" + meal.getPrice() + "</td>");
					out.println("<td>" + meal.isAvailable() + "</td>");
					out.println("<td>" + meal.getLastModification() + "</td>");
					out.println("</tr>");
					out.println("</body></html>");*/
			       	out.close();
				}
			}
			
			if(request.getParameter("type").toString().equals("delete"))
			{
				int idMeal = Integer.parseInt(request.getParameter("mealId").toString());
				Meal meal = MealDAO.getInstance().findId(idMeal);
				
				MenuDAO.getInstance().removeMeal(idMenu, idMeal);
				
				response.setContentType("text/html");
		       	PrintWriter out = response.getWriter();
		       	out.println("<div >Le plat " + meal.getName() + " a été supprimé du menu.</div>");
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
	
	private boolean mealExists(List<Meal> meals, int mealId)
	{
		boolean exist = false;
		for(Meal m : meals)
		{
			if(m.getId() == mealId)
				exist = true;
		}
		return exist;
	}

}
