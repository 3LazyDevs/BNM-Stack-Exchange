import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.*;
//import java.nio.file.Path;

public class cse_view extends HttpServlet
{
	String despath;
	String codepath;
	public void check_query(String user, String shortq)
	{
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
		try
		{
			stmt = conn.createStatement();
			String query = "select despath,codepath from cseqanda WHERE username = ? AND shorthand = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, user);
			pstmt.setString(2, shortq);
			System.out.println(user + " " + shortq);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				despath = rs.getString("despath");
				codepath = rs.getString("codepath");
			}
		}
		catch(Exception e)
		{
			System.out.println("problem in executing SQL commands " + e);
		}
	}
	public void service (HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String summary = request.getParameter("summary");
		String user = request.getParameter("user");
		check_query(user, summary);
		Path fileName1 = Path.of(despath);
		Path fileName2 = Path.of(codepath);
		String des = Files.readString(fileName1);
		String code = Files.readString(fileName2);
		String loremIpsum = "Lorem ipsum dolor sit, amet consectetur adipisicing elit. A adipisci modi veritatis vero doloribus beatae maiores dolore itaque consectetur, laudantium sit corrupti illum aspernatur molestiae, veniam natus rerum expedita error!";
		PrintWriter out = response.getWriter();
		out.println("<html>" + "<head><link rel='stylesheet' href='cse_view.css'></head><body>");
		out.println("<section><textarea cols = '50' rows = '25' name = 'description' readonly>" + des + "</textarea><br>"
				    + "<textarea cols = '30' rows = '10' name = 'code' readonly>" + code + "</textarea></section>");
		out.println("<form name=\"CSEans\" method=\"post\" action=\"CseAns\">\r\n"
				+ "<textarea id='ta1' cols = 50, rows = 25, name = \"answer-des\" placeholder = \"Enter the answer\" required>\r\n" + "</textarea><br>\r\n"
				+ "<textarea id='ta2'cols = 50, rows = 25, name = \"answer-code\" placeholder = \"Enter the code if required else type NA\" required>\r\n" + "</textarea><br>\r\n"
				+ "<textarea name = 'des-path' hidden>" + despath + "</textarea>"
				+ "<textarea name = 'code-path' hidden>" + codepath + "</textarea>"
				+ "<input type=\"submit\" value=\"POST\" />\r\n"
				+ "</form>");
		out.println("</body></html>");
		
		

	}
}
