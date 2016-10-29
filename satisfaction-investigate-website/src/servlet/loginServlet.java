package servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



public class loginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
//		从http请求中获取前端输入的用户名和密码。和前段中的input标签内的name值相对应
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		Map<String,String> users = new HashMap<String,String>();
		users.put("a", "lizhengyao");
		users.put("b", "nietianhui");
		users.put("c", "yunaitong");
		users.put("d", "linjianfa");
		users.put("e", "chenxingli");
		users.put("f", "liuxuan");
		users.put("g", "shenzhiyong");
		users.put("h", "userh");
		users.put("i", "i");
		
		if(users.get(username) == null)
		{
			backToPage(req,resp,"该用户名不存在");
		}
		else if(!users.get(username).equals(password))
		{
			backToPage(req,resp,"密码错误");
		}
		else
		{
			HttpSession session = req.getSession();
			session.setAttribute("user", username);
			
			resp.sendRedirect("instruction.jsp");
		}	
		
		
	}

	/*
	 * request.getRequestDispatcher()是请求转发，前后页面共享一个request ; 
	 * response.sendRedirect()是重新定向，前后页面不是一个request。
	 */
	private void backToPage(HttpServletRequest req,
							HttpServletResponse resp, 
							String errInfo) throws ServletException, IOException {

		req.setAttribute("errInfo", errInfo);

		req.getRequestDispatcher("index.jsp").forward(req, resp);
	}
	
}
