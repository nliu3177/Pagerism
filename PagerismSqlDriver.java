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
	
	//Updates data in tables
	void executeInfoStuff(String query){
		Connection conn = connectingTo();
		
			try{
			PreparedStatement hopeful = conn.prepareStatement(query);
			hopeful.executeUpdate(query);
			}
			catch(Exception e)
			{
				
			}
	}
	
	void executeDeleteStuff(String query){
		Connection conn = connectingTo();
		
			try{
			PreparedStatement hopeful = conn.prepareStatement(query);
			hopeful.execute(query);
			}
			catch(Exception e)
			{
				
			}
	}
	// Gives a resultset of all the inventory
	ResultSet selectInventory()
	{
		
		String query = "select * from bookInfo;";
		return executeStuff(query);
		
	}
	
	ResultSet searchGenre(String genre)
	{
		String query = "select * from bookInfo where genre =\"" + genre +"\";";
		return executeStuff(query);
	}
	
	// Shows the users cart when given the id of the user
	ResultSet selectCart(int id)
	{
		String query = "select * from bookInfo where bookInfo.bookid in(select bookid from cart where userid =" + id +");";
		return executeStuff(query);
	}
	
	// Insert into the cart using the users id and the books id
	void addToCart(String userid, String bookid)
	{
		String query = "insert into cart values(" + bookid + ", " + userid +");";
		executeInfoStuff(query);
	}
	
	
	
	//Returns the user's id if there exists an account with the username and password
	//The user id is useful for the other queries
	int logIn(String username, String password){
		String query = "select * from users where USERNAME =\"" + username + "\"and PASSWORD =\"" + password +"\";";
		ResultSet rs = executeStuff(query);
		
		int id = -1;
		
		
		try{
			rs.beforeFirst();
			rs.next();
			id = rs.getInt(1);
		}
		catch(Exception e)
		{
			
		}
		
		return id;
	}
	
	void deleteUser(String username)
	{
		String query = "delete from users where username =\"" + username +"\";";
		executeDeleteStuff(query);
	}
	
	boolean validUser(String username)
	{
		String query = "select * from users where username =\"" + username + "\";";
		ResultSet rs = executeStuff(query);
		
		try
		{
		if(rs.next()){
			return true;
		}
		}
		catch(Exception e)
		{
			
		}
		
		return false;
	}
	
	// Creates new user
	int registerUser(String username, String password){
		String query = "insert into users(USERNAME, PASSWORD, ACCOUNT) values(" + username + "," + password + ", \"Customer\";";
		executeInfoStuff(query);
		
		query = "select id from users where username =\"" + username + "\";";
		ResultSet rs = executeStuff(query);
		
		try
		{
			rs.beforeFirst();
			rs.next();
			return rs.getInt("id");
		}
		catch(Exception e)
		{
			
		}
		
		return -1;
	}
	
	//Finds books with titles that contain the given string in its name
	ResultSet findBook(String title)
	{
		String query = "select * from bookInfo where title like '%" + title + "%';";
		return executeStuff(query);
	}
	
	int getUserId(String username)
	{
		String query = "select id from users where USERNAME =\"" + username + "\";";
		ResultSet rs = executeStuff(query);
		
		int id = -1;
		
		try{
			rs.beforeFirst();
			rs.next();
			id = rs.getInt(1);
		}
		catch(Exception e)
		{
			
		}
		
		return id;
	}
	
	int getBookId(String booktitle)
	{
		String query = "select bookid from bookInfo where TITLE =\"" + booktitle + "\";";
		ResultSet rs = executeStuff(query);
		
		int id = -1;
		
		try{
			rs.beforeFirst();
			rs.next();
			id = rs.getInt(1);
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
		String query = "insert into orderitems(ordernum, booknum) select ordernum,bookid from cart, orders where cart.userid =" + Integer.toString(userid) + " and orders.userid = " + Integer.toString(userid) + " and orders.ordernum = (select max(ordernum) from orders);";
		executeInfoStuff(query);
	}
	
	//Creates a new order for the user
	void CreateOrder(int userid)
	{
		String query = "insert into orders(userid) values " + Integer.toString(userid) + ";";
		executeInfoStuff(query);
		
	}
	
	int getCartTotal(int userid)
	{
		int total = -1;
		String query = "select price from bookInfo where bookid in (select bookid from cart where userid =" + userid + ");";
		ResultSet rs = executeStuff(query);
		try
		{
			while(rs.next())
			{
				total = rs.getInt("price");
			}
		}
		catch(Exception e)
		{
			
		}
		
		return total;
	}
	
	ResultSet displayOrderNums(int userid)
	{
		String query = "select * from orders where userid =" + Integer.toString(userid) +";";
		ResultSet rs = executeStuff(query);
		return rs;
	}

	ResultSet displayOrderContents(int ordernum)
	{
		String query = "select * from bookInfo where bookid in (select booknum from orderitems where ordernum =" + Integer.toString(ordernum)+");";
		ResultSet rs = executeStuff(query);
		return rs;
	}
	
	
	void removeFromCart(String title)
	{
		String query = "delete from cart where bookid in(select bookid from bookInfo where TITLE =" + title +";";
		executeDeleteStuff(query);
	}
	
	void deleteCart(int userid)
	{
		String query = "delete from cart where userid =" +Integer.toString(userid)+";";
		executeDeleteStuff(query);
	}
	
}
