import java.sql.*;
public class PagerismSqlDriver {
	
	//Setup to make a connection to the sql server
	final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	final String DB_URL="jdbc:mysql://pagerism.ddns.net/Pagerism";
	
	final String USER ="golder";
	final String PASSWORD ="aurum";
	
	//Creates Connection to Mysql database, don't mess with this
	Connection connectingTo(){
	//Connect to sql
	try
	{
		Class.forName(JDBC_DRIVER);
		
		Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
		
		return conn;
	}
	catch(SQLException s)
	{
		
	}
	catch(Exception e)
	{
		
	}
	finally
	{
		
	}
	
	return null;
	}
	
	// This executes queries, dont mess with it either
	ResultSet executeStuff(String query){
		
		Connection conn = connectingTo();
		
		try{
			
			PreparedStatement hopeful = conn.prepareStatement(query);
			ResultSet rs = hopeful.executeQuery(query);
			conn.close();
			hopeful.close();
			
			return rs;
			}
			catch(Exception e)
			{
				
			}
			
			return null;
	}
	// Gives a resultset of all the inventory
	ResultSet selectInventory()
	{
		
		String query = "select * from bookInfo;";
		return executeStuff(query);
		
	}
	
	// Shows the users cart when given the id of the user
	ResultSet selectCart(int id)
	{
		String query = "select title, price from bookInfo natural join cart where userid =";
		query += id;
		
		return executeStuff(query);
	}
	
	// Insert into the cart using the users id and the books id
	void addToCart(int userid, int bookid)
	{
		String query = "insert into cart values(" + bookid + ", " + userid +");";
		executeStuff(query);
	}
	
	//Returns the user's id if there exists an account with the username and password
	//The user id is useful for the other queries
	String logIn(String username, String password){
		String query = "select * from users where USERNAME =" + username + "and PASSWORD =" + password +";";
		ResultSet rs = executeStuff(query);
		
		String id ="";
		
		try{
			id = rs.getString("id");
		}
		catch(Exception e)
		{
			
		}
		
		return id;
	}
	
	//Finds books with titles that contain the given string in its name
	ResultSet findBook(String title)
	{
		String query = "select * from bookInfo where title like '%" + title + "%';";
		return executeStuff(query);
	}
}
