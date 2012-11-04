package com.supinfo.youfood.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.supinfo.youfood.dao.AdministratorDAO;
import com.supinfo.youfood.dao.WaiterDAO;
import com.supinfo.youfood.entity.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		User loggedUser = AdministratorDAO.getInstance().authentication(request.getParameter("login"), request.getParameter("password"));
		if(loggedUser == null) loggedUser = WaiterDAO.getInstance().authentication(request.getParameter("login"), request.getParameter("password"));
		
		if(loggedUser == null)
		{
			request.setAttribute("fail", true);
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
		else
		{
			request.getSession().setAttribute("loggedUser", loggedUser);
			request.getSession().setAttribute("first", "first");
			response.sendRedirect(getServletContext().getContextPath()+"/home");
		}
			
	}

}
