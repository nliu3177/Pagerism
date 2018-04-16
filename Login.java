

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
		PrintWriter out = response.getWriter();
	
		PagerismSqlDriver psql = new PagerismSqlDriver();
		
		out.println("<!DOCTYPE html>");
        out.println("<html><head>");
        out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
        out.println("<title>Pagerism Login</title>");
        out.println("</head><body>");
        
        if(request.getParameter("login") != null){
        	out.println("Login<br>");
        	out.println("User ID: " + psql.logIn(request.getParameter("username"), request.getParameter("password")));
        }
        else if(request.getParameter("register") != null){
        	out.println("Register<br>");
        	psql.registerUser(request.getParameter("username"), request.getParameter("password"));
        	out.println("User: " + request.getParameter("username") + " Registered!");
        }
        
        out.println("</body>"
        		+ 	"</html>");
		out.close();
		
		//RequestDispatcher rd = request.getRequestDispatcher("store");
		//rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
