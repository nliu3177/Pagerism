
import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Search
 */
@WebServlet("/Search")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Search() {
        super();
        // TODO Auto-generated constructor stub
    }
    void pageHead(PrintWriter out)
    {
    	out.println("<html>");
		out.println("<head></head>");
		out.println("<body>");
		out.println("<meta charset=\"ISO-8859-1\">");
		out.println("<title>Class Entry</title>");
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		PagerismSqlDriver hope = new PagerismSqlDriver();
		
		ResultSet rs = hope.findBook(request.getParameter("search"));
		pageHead(out);
		out.println("Search completed");
		out.println("<br>");
		
		try
		{
		while(rs.next())
		{
			out.println(rs.getString("title"));
			out.println(" ");
			out.println("$"+rs.getString("price"));
			out.println(" ");
			out.println(rs.getString("A_First_Name"));
			out.println(" ");
			out.println(rs.getString("A_Last_Name"));
			out.println("<br>");
		}
		}
		catch(Exception e)
		{
			out.println("Something went wrong " + e);
		}
		
		out.println("<br>");
		out.println("<form method=\"post\" action=\"Shop\">");
		out.println("Username: <input type=\"text\" name=\"username\"/><br/>");
		out.println("Book Title: <input type=\"text\" name=\"booktitle\"/><br/>");
		out.println("<input type=\"submit\" value=\"Add To Cart\"/>");
		out.println("</form>");
		out.println("<form method=\"get\" action=\"Shop\">");
		out.println("<input type=\"submit\" value=\"Return To Shopping\"/>");
		out.println("</form>");
		pageEnd(out);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
