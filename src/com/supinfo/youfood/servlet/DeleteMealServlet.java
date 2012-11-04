package com.supinfo.youfood.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.supinfo.youfood.dao.MealDAO;
import com.supinfo.youfood.entity.Administrator;
import com.supinfo.youfood.entity.Meal;

/**
 * Servlet implementation class DeleteMealServlet
 */
@WebServlet("/meals/delete")
public class DeleteMealServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteMealServlet() {
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
		//Delete a meal
		try
		{
			if(request.getSession().getAttribute("loggedUser") == null || !(request.getSession().getAttribute("loggedUser") instanceof Administrator))
			{
				throw new Exception("Not allowed");
			}
			Meal meal = MealDAO.getInstance().findId(Integer.valueOf(request.getParameter("mealId")));
			MealDAO.getInstance().deleteMeal(meal);
		}
		catch(Exception e)
		{}
	}

}
