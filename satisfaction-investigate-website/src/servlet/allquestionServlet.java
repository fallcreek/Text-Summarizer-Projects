package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import JDBC.searchQuestion;
import entity.question;

public class allquestionServlet extends HttpServlet {

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
			List<question> qlist = searchQuestion.searchAllQuestion(username);
			session.setAttribute("qlist", qlist);
			resp.sendRedirect("questionlist.jsp");
		}
	}

	
}
