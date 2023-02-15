import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
//import java.io.PrintWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.io.File;


public class signup extends HttpServlet
{	
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		
		PrintWriter out = response.getWriter();
		String pass;
		String repass;
		String email;
		String user;
		Connection conn;
		String userName;
		String url;
		String password;
		Statement stmt;
		ResultSet rs;
		PreparedStatement pstmt;
		stmt = null;
		rs = null;
		pstmt = null;
		conn = null;
		try
		{
			conn = null;
			userName = "root";
			password = "ORacl3MyS@l";
			url = "jdbc:mysql://localhost:3306/test";
			Class.forName ("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url,userName,password);
			System.out.println("Database connection established");
			stmt = null;
			rs = null;
			pstmt = null;
		}
		catch(Exception e)
		{
			System.out.println("Cannot connect to database " + e);
		}
		pass = request.getParameter("password");
		repass = request.getParameter("repassword");
		email = request.getParameter("email");
		user = request.getParameter("username");
		try 
		{
			stmt = conn.createStatement();
			stmt.execute("create table if not exists user(email_id Varchar(50), username Varchar(50), password Varchar(50))");
			String query = "insert into user (email_id, username, password) values (?, ?, ?)";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, email);
			pstmt.setString(2, user);
			pstmt.setString(3, pass);
			pstmt.executeUpdate();
			query = "create table if not exists "+ user + " (shorthand Varchar(100), despath Varchar(100), codepath Varchar(100))";
			stmt.execute(query);
//			pstmt = conn.prepareStatement(query);
//			pstmt.setString(1, user);
//			pstmt.executeUpdate();
		}
		catch(Exception err2)
		{
			System.out.println("my error" + err2.getMessage());
			response.sendRedirect("SignupError.html");
		}
		if(pass.equals(repass))
		{
			String path = "C:/Users/mohit/Desktop/users/";
			path = path + user;
			File f1 = new File(path);
			try
			{
				f1.mkdir();
			}
			catch(Exception e)
			{
				System.out.println("cannot make folder" + e);
			}
			response.sendRedirect("login.html");
		}
		else
		{
			response.sendRedirect("SignupError.html");
		}
		try
		{
			conn.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
}