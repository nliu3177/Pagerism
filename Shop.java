

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
		out.println("<title>Shop</title>");
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
			
		//Form for going to the user servlet
		out.println("<form method=\"get\" action=\"User\">");
		out.println("<input type=\"hidden\" name=\"userid\" value = \""+ request.getParameter("userid") +"\"/>");
		out.println("<input type=\"submit\" value=\"Userpage\"/>");
		out.println("</form>");
		out.println("<br>");
		
		
		PagerismSqlDriver hope = new PagerismSqlDriver();
		ResultSet rs = null;
		String state = request.getParameter("state");

		if(state == null)
		{
			state = "skip";
		}
		
		if(state.compareTo("genre") == 0)
		{
			rs = hope.searchGenre(request.getParameter("genreInfo"));
		}
		else if(state.compareTo("search") == 0)
		{
			rs = hope.findBook(request.getParameter("searchFor"));
		}
		
		else if(state.compareTo("add") == 0)
		{
			
			hope.addToCart(request.getParameter("userid"), request.getParameter("bookid"));
			out.println("<form method=\"get\" action=\"Shop\">");
			out.println("<input type=\"hidden\" name=\"userid\" value = \""+ request.getParameter("userid") +"\"/>");
			out.println("<input type=\"submit\" value=\"Back to shopping\"/>");
			out.println("</form>");
			out.println("<br>");
		}
		
		else
		{
			rs = hope.selectInventory();
		}
		
		try{
		while(rs.next())
		{
			out.println("Bookid: ");
			out.println(rs.getString("bookid"));
			out.println(rs.getString("title"));
			out.println(" ");
			out.println("$"+rs.getString("price"));
			out.println(" ");
			out.println(rs.getString("A_First_Name"));
			out.println(" ");
			out.println(rs.getString("A_Last_Name"));
			out.println(" ");
			out.println(rs.getString("genre"));
			out.println("<br>");
		}
		}
		catch(Exception e)
		{
			out.println("Something went wrong " + e);
		}
		out.println("<br>");
		
		
		
		//Form for adding to the cart
		out.println("<form method=\"get\" action=\"Shop\">");
		out.println("<input type=\"hidden\" name=\"state\" value = \"add\"/><br/>");
		out.println("<input type=\"hidden\" name=\"userid\" value = \""+ request.getParameter("userid") +"\"/>");
		out.println("Book Id: <input type=\"text\" name=\"bookid\"/><br/>");
		out.println("<input type=\"submit\" value=\"Add To Cart\"/>");
		out.println("</form>");
		out.println("<br>");
		
		//Form for searching by genre
		out.println("<form method=\"get\" action=\"Shop\">");
		out.println("<input type=\"hidden\" name=\"state\" value = \"genre\"/><br/>");
		out.println("Search by Genre<br>");
		out.println("<input type=\"radio\" name=\"genreInfo\" value = \"Nonfiction\"/> Nonfiction ");
		out.println("<input type=\"radio\" name=\"genreInfo\" value = \"Adventure\"/> Adventure ");
		out.println("<input type=\"radio\" name=\"genreInfo\" value = \"Fantasy\"/> Fantasy ");
		out.println("<input type=\"radio\" name=\"genreInfo\" value = \"Mystery\"/> Mystery ");
		out.println("<input type=\"radio\" name=\"genreInfo\" value = \"Science Fiction\"/> Science Fiction ");
		out.println("<input type=\"hidden\" name=\"userid\" value = \""+ request.getParameter("userid") +"\"/>");
		out.println("<br><input type=\"submit\" value=\"Display by Genre\"/>");
		out.println("<br>");
		out.println("</form>");
		
		
		//Form for searching by keyword
		out.println("<form method=\"get\" action=\"Shop\">");
		out.println("Searchword: <input type=\"text\" name=\"searchFor\"/><br/>");
		out.println("<input type=\"hidden\" name=\"state\" value = \"search\"/><br/>");
		out.println("<input type=\"hidden\" name=\"userid\" value = \""+ request.getParameter("userid") +"\"/>");
		out.println("<input type=\"submit\" value=\"Search\"/>");
		out.println("</form>");
		out.println("<br>");
		pageEnd(out);
		
		
	
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
	}

}
