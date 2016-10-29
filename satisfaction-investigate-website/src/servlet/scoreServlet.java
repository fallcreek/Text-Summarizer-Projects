package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import JDBC.searchQuestion;
import entity.question;

public class scoreServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		String username = (String) session.getAttribute("user");

		if(username == null)
		{
			resp.sendRedirect("index.jsp");
		}
		else
		{
			question q = searchQuestion.search(username);
			session.setAttribute("question", q);
			resp.sendRedirect("question.jsp");
		}
		
	}

	
	
	
}
