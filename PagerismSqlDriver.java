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
		String query = "select * from cart where userid =" + Integer.toString(id) +";";
		return executeStuff(query);
	}
	
	// Insert into the cart using the users id and the books id
	void addToCart(int userid, int bookid)
	{
		String query = "insert into cart bookid, userid values(" + bookid + ", " + userid +");";
		executeStuff(query);
	}
	
	//Returns the user's id if there exists an account with the username and password
	//The user id is useful for the other queries
	int logIn(String username, String password){
		String query = "select * from users where USERNAME =" + username + "and PASSWORD =" + password +";";
		ResultSet rs = executeStuff(query);
		
		int id = -1;
		
		try{
			id = rs.getInt(0);
		}
		catch(Exception e)
		{
			
		}
		
		return id;
	}
	
	// Creates new user
	void registerUser(String username, String password){
		String query = "insert into users(USERNAME, PASSWORD, ACCOUNT) values(" + username + "," + password + ", 'Customer';";
		executeStuff(query);
	}
	
	//Finds books with titles that contain the given string in its name
	ResultSet findBook(String title)
	{
		String query = "select * from bookInfo where title like '%" + title + "%';";
		return executeStuff(query);
	}
	
	int getUserId(String username)
	{
		String query = "select id from users where USERNAME =" + username;
		ResultSet rs = executeStuff(query);
		
		int id = -1;
		
		try{
			id = rs.getInt("id");
		}
		catch(Exception e)
		{
			
		}
		
		return id;
	}
	
	int getBookId(String booktitle)
	{
		String query = "select bookid from bookInfo where TITLE =" + booktitle;
		ResultSet rs = executeStuff(query);
		
		int id = -1;
		
		try{
			id = rs.getInt("bookid");
		}
		catch(Exception e)
		{
			
		}
		
		return id;
	}

	// Inserts all the books in the cart into the newest order of the given userid
	// Make sure to use CreateOrder first
	void CreateOrderItems(int userid)
	{
		String query = "insert into orderitems(ordernum, booknum) select ordernum,bookid from cart, orders where cart.userid =" + userid + " and orders.userid = " + userid + " and orders.ordernum = (select max(ordernum) from orders);";
		executeStuff(query);
	}
	
	//Creates a new order for the user
	void CreateOrder(int userid)
	{
		String query = "insert into orders(userid) values " + userid + ";";
		executeStuff(query);
		
	}
	
	ResultSet displayOrderNums(int userid)
	{
		String query = "select * from orders where userid =" + userid +";";
		ResultSet rs = executeStuff(query);
		return rs;
	}

	ResultSet displayOrderContents(int ordernum)
	{
		String query = "select * from bookInfo where bookid in (select booknum from orderitems where ordernum =" +ordernum+");";
		ResultSet rs = executeStuff(query);
		return rs;
	}
	
	void deleteCart(int userid)
	{
		String query = "delete from cart where userid =" +userid+";";
		executeStuff(query);
	}
}
