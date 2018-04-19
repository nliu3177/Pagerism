

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

/**
 * Servlet implementation class Shop
 */
@WebServlet("/Shop")
public class Shop extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Shop() {
        super();
        // TODO Auto-generated constructor stub
    }

    
    // prints the header of an html page
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
		
		pageHead(out);
		out.println(request.getParameter("userid"));
		PagerismSqlDriver hope = new PagerismSqlDriver();
		
		ResultSet rs;
		
			rs = hope.selectInventory();
			
		try{
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
		out.println("<form method=\"get\" action=\"Search\">");
		out.println("Searchword: <input type=\"text\" name=\"search\"/><br/>");
		out.println("<input type=\"submit\" value=\"Search\"/>");
		out.println("</form>");
		out.println("<br>");
		out.println("<form method=\"get\" action=\"Cart\">");
		out.println("Username: <input type=\"text\" name=\"username\"/><br/>");
		out.println("<input type=\"submit\" value=\"Display Cart\"/>");
		out.println("</form>");
		pageEnd(out);
		
		
	
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PagerismSqlDriver hope = new PagerismSqlDriver();
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		pageHead(out);
		int userid = hope.getUserId(request.getParameter("username"));
		int bookid = hope.getBookId(request.getParameter("booktitle"));
		hope.addToCart(userid, bookid);
		out.println("<form method=\"get\" action=\"Shop\">");
		out.println("<input type=\"submit\" value=\"Return To Shopping\"/>");
		out.println("</form>");
		pageEnd(out);
		
		
	}

}
