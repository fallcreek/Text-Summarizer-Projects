package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import JDBC.searchQuestion;

public class completeServlet extends HttpServlet {


	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		String username = (String) session.getAttribute("user");
		
		if(username == null)
		{
			resp.sendRedirect("index.jsp");
		}
		else
		{
			int id = Integer.valueOf(req.getParameter("id"));
			String q1 = req.getParameter("q1");
			String q2 = req.getParameter("q2");
			String q3 = req.getParameter("q3");
			
			if(searchQuestion.complete(id, username, q1, q2, q3))
				resp.sendRedirect("scoreServlet");
		}
		
		
		
		
	}

	
}
