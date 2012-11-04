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
import com.supinfo.youfood.dao.ProductTypeDAO;
import com.supinfo.youfood.entity.Administrator;
import com.supinfo.youfood.entity.Meal;
import com.supinfo.youfood.entity.ProductType;

/**
 * Servlet implementation class UpdateMealServlet
 */
@WebServlet("/meals/update")
public class UpdateMealServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateMealServlet() {
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
			
			if(request.getParameter("type") == null || request.getParameter("mealId") == null)
			{
				throw new Exception("aucun type ou id ");
			}
			
			int idMeal = Integer.parseInt(request.getParameter("mealId").toString());
			Meal meal = MealDAO.getInstance().findId(idMeal);
			
			if(request.getParameter("type").toString().equals("all"))
			{
				if(request.getParameter("nom") == null|| request.getParameter("prix") == null || request.getParameter("description") == null)
				{
					throw new Exception("données manquantes");
				}
				
				meal.setName(new String(request.getParameter("nom").getBytes(),"UTF-8"));
				meal.setDescription(new String(request.getParameter("description").getBytes(),"UTF-8"));
				meal.setPrice(new Float((String)request.getParameter("prix")));
				meal.setAvailable(request.getParameter("disponible").toString().equals("true"));
				
				MealDAO.getInstance().update(meal);
				
				response.setContentType("text/html");
		       	PrintWriter out = response.getWriter();
				out.println("<div>Le plat a été mis à jour.</div>");
		       	out.close();
			}
			
			if(request.getParameter("type").toString().equals("add"))
			{
				int idType = Integer.parseInt(request.getParameter("typeId").toString());
				ProductType type = ProductTypeDAO.getInstance().findId(idType);
				
				if(typeExists(meal.getTypes(),idType))
				{
					response.setContentType("text/html");
			       	PrintWriter out = response.getWriter();
					out.println("<div>Le plat est déjà du type : " + type.getName() + "</div>");
			       	out.close();
				}
				else
				{
					MealDAO.getInstance().addType(idMeal, idType);
					response.setContentType("text/html");
			       	PrintWriter out = response.getWriter();
			       	//out.println("<html><body>");
					out.println("<div >Le plat est maintenant du type : " + type.getName() + "</div>");
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
				int idType = Integer.parseInt(request.getParameter("typeId").toString());
				ProductType type = ProductTypeDAO.getInstance().findId(idType);
				
				MealDAO.getInstance().removeType(idMeal, idType);
				
				response.setContentType("text/html");
		       	PrintWriter out = response.getWriter();
		       	out.println("<div >Le plat n'est plus du type : " + type.getName() + "</div>");
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
	
	private boolean typeExists(List<ProductType> types, int typeId)
	{
		boolean exist = false;
		for(ProductType t : types)
		{
			if(t.getId() == typeId)
				exist = true;
		}
		return exist;
	}

}
