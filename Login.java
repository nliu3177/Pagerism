

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
    

	// prints the header of an html page
    void pageHead(PrintWriter out)
    {
    	out.println("<html>");
		out.println("<head></head>");
		out.println("<body>");
		out.println("<meta charset=\"ISO-8859-1\">");
		out.println("<title>Login</title>");
		out.println("</head>");
		out.println("<body>");
    }
    
  //prints the footer of an html page
    void pageEnd(PrintWriter out)
    {
    	out.println("</form>");
		out.println("</body>");
		out.println("</html>");
    }
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = -2;
		PrintWriter out = response.getWriter();
		PagerismSqlDriver hope = new PagerismSqlDriver();
		pageHead(out);
		
		String state = request.getParameter("log");
		if(state == null)
		{
			state = "skip";
		}
		
		if(state.compareTo("forgot") == 0)
		{
			
			out.println("<form method=\"get\" action=\"Login\">");
			if(hope.validUser(request.getParameter("username")))
			{
				out.println("<form\"post\" action=\"Login\">");
				out.println("New Password: <input type=\"text\" name=\"password\"/><br/>");
				out.println("<input type=\"hidden\" name=\"log\" value = \"create\"/><br/>");
				out.println("<input type =\"hidden\" name=\"username\" value = \"" + request.getParameter("username") + "\"/><br/>");
				out.println("<input type=\"submit\" value=\"Submit\"/>");
				out.println("</form>");
			}
			else
			{
				out.println("Invalid username");
			}
			
		}
		
		else if(state.compareTo("create") == 0)
		{
			id = hope.registerUser(request.getParameter("username"), request.getParameter("password"));
			out.println("<form method=\"get\" action=\"Shop\">");
			out.println("<input type=\"hidden\" name=\"userid\" value = \""+ id +"\"/>");
			out.println("<input type=\"submit\" value=\"Creation Successful\"/>");
			out.println("</form>");
		}
		
		else
		{
			id = hope.logIn(request.getParameter("username"), request.getParameter("password"));
			out.println("<form method=\"get\" action=\"Shop\">");
			out.println("<input type=\"hidden\" name=\"userid\" value = \""+ id +"\"/>");
			out.println("<input type=\"submit\" value=\"Login Successful!\"/>");
			out.println("</form>");
			out.println("<br>");
		}
		
		
		pageEnd(out);
		
	}

}
