
import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class User
 */
@WebServlet("/User")
public class User extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public User() {
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
		out.println("<title>User</title>");
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
		PrintWriter out = response.getWriter();
		
		pageHead(out);
		
		out.println("\t\t\t");
		//Form for going to the Shop servlet
		out.println("<form method=\"get\" action=\"Shop\">");
		out.println("<input type=\"hidden\" name=\"userid\" value = \""+ request.getParameter("userid") +"\"/>");
		out.println("<input type=\"submit\" value=\"Return to Store\"/>");
		out.println("</form>");
		out.println("<br>");
		
		//Checkout
		out.println("<form method=\"get\" action=\"Cart\">");
		out.println("<input type=\"hidden\" name=\"userid\" value = \""+ request.getParameter("userid") +"\"/>");
		out.println("<input type=\"submit\" value=\"Display Cart\"/>");
		out.println("</form>");
		out.println("<br>");
				
		//Checkout
		out.println("<form method=\"POST\" action=\"User\">");
		out.println("<input type=\"hidden\" name=\"userid\" value = \""+ request.getParameter("userid") +"\"/>");
		out.println("<input type=\"submit\" value=\"Display Orders\"/>");
		out.println("</form>");
		out.println("<br>");
				
	pageEnd(out);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		PagerismSqlDriver hope = new PagerismSqlDriver();
		ResultSet rs = null;
		
		pageHead(out);
		
		String state = request.getParameter("state");
		
		if(state == null)
		{
			state = "skip";
		}
		
		if(state.compareTo("display") == 0)
		{
			rs = hope.displayOrderContents(Integer.parseInt(request.getParameter("ordernum")));
		}
		
		else
		{
			rs = hope.displayOrderNums(Integer.parseInt(request.getParameter("userid")));
		}
		
		out.println("Orders");
		out.println("<br>");
		try{
			
			if(state.compareTo("display") == 0)
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
			
			else
			{
				while(rs.next())
				{
					out.println("ordernum: ");
					out.println(rs.getString("ordernum"));
					out.println(rs.getString("<br>"));
					
				}
			}
			
			}
			catch(Exception e)
			{
				out.println("Something went wrong " + e);
			}
		
		//Displays contents of an order
		out.println("<form method=\"POST\" action=\"User\">");
		out.println("Type  ordernum to display contents: <input type=\"text\" name=\"ordernum\"/><br/>");
		out.println("<input type=\"hidden\" name=\"state\" value = \"display\"/><br/>");
		out.println("<input type=\"hidden\" name=\"userid\" value = \""+ request.getParameter("userid") +"\"/>");
		out.println("<input type=\"submit\" value=\"Display\"/>");
		out.println("</form>");
		out.println("<br>");
		
		//Goes back to the userpage
		out.println("<form method=\"get\" action=\"User\">");
		out.println("<input type=\"hidden\" name=\"userid\" value = \""+ request.getParameter("userid") +"\"/>");
		out.println("<input type=\"submit\" value=\"Userpage\"/>");
		out.println("</form>");
		out.println("<br>");
		pageEnd(out);
	}

}
