
import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Cart
 */
@WebServlet("/Cart")
public class Cart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Cart() {
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
		PagerismSqlDriver hope = new PagerismSqlDriver();
		
		pageHead(out);
		
		if(request.getParameter("state") == "removing")
		{
			hope.removeFromCart(request.getParameter("title"));
		}
		
		
		ResultSet rs = hope.selectCart(Integer.parseInt(request.getParameter("username")));
		
		out.println("Displaying Cart");
		out.println("<br>");
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
		int total = hope.getCartTotal(Integer.parseInt(request.getParameter("userid")));
		out.println(total);
		
		//Remove from cart
		out.println("<form method=\"get\" action=\"Cart\">");
		out.println("<input type=\"hidden\" name=\"state\" value = \"removing\"/><br/>");
		out.println("<input type=\"hidden\" name=\"userid\" value = \""+ request.getParameter("userid") +"\"/>");
		out.println("Book Title to Remove: <input type=\"text\" name=\"title\"/><br/>");
		out.println("<input type=\"submit\" value=\"RemoveFromCart\"/>");
		out.println("</form>");
		out.println("<br>");
		
		//Checkout
		out.println("<form method=\"POST\" action=\"Cart\">");
		out.println("<input type=\"hidden\" name=\"userid\" value = \""+ request.getParameter("userid") +"\"/>");
		out.println("<input type=\"submit\" value=\"Checkout\"/>");
		out.println("</form>");
		out.println("<br>");
		
		//Goes back to user page
		out.println("<form method=\"get\" action=\"User\">");
		out.println("<input type=\"hidden\" name=\"userid\" value = \""+ request.getParameter("userid") +"\"/>");
		out.println("<input type=\"submit\" value=\"Back to Userpage\"/>");
		out.println("</form>");
		pageEnd(out);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		PagerismSqlDriver hope = new PagerismSqlDriver();
		
		//Creates the orders
		hope.CreateOrder(Integer.parseInt(request.getParameter("userid")));
		hope.CreateOrderItems(Integer.parseInt(request.getParameter("userid")));
		hope.deleteCart(Integer.parseInt(request.getParameter("userid")));
		
		pageHead(out);
		out.println("<form method=\"get\" action=\"User\">");
		out.println("<input type=\"hidden\" name=\"userid\" value = \""+ request.getParameter("userid") +"\"/>");
		out.println("<input type=\"submit\" value=\"Back to Userpage\"/>");
		out.println("</form>");
		pageEnd(out);
	}

}
