package com.supinfo.youfood.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.supinfo.youfood.dao.AdministratorDAO;
import com.supinfo.youfood.dao.WaiterDAO;
import com.supinfo.youfood.entity.Administrator;
import com.supinfo.youfood.entity.Waiter;

/**
 * Servlet implementation class DeleteUserServlet
 */
@WebServlet("/users/delete")
public class DeleteUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteUserServlet() {
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
		//Supprimer un utilisateur
		try
		{
			if(request.getSession().getAttribute("loggedUser") == null || !(request.getSession().getAttribute("loggedUser") instanceof Administrator))
			{
				throw new Exception("Not allowed");
			}
			
			String type = request.getParameter("type").toString();
			if(type.equals("a"))
			{
				Administrator admin = AdministratorDAO.getInstance().findId(Integer.valueOf(request.getParameter("userId")));
				AdministratorDAO.getInstance().deleteAdministrator(admin);
			}
			if(type.equals("w"))
			{
				Waiter waiter = WaiterDAO.getInstance().findId(Integer.valueOf(request.getParameter("userId")));
				WaiterDAO.getInstance().deleteWaiter(waiter);
			}
		}
		catch(Exception e)
		{}
	}

}
