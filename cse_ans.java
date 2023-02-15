import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.io.File;
import java.io.FileWriter;
import javax.servlet.http.*;
import java.io.*;
//hide textarea keyword hidden

public class cse_ans extends HttpServlet
{
	String answer;
	String des_path;
	String code_path;
	String code;
	public void add_ans(String answer, String code, String des_path, String code_path)
	{
		try
		{
			FileWriter fw = new FileWriter(des_path, true); 
			BufferedWriter bw = new BufferedWriter(fw); 
			PrintWriter pw = new PrintWriter(bw); 
			pw.println("\n\n Answer");
			pw.println(answer);
			pw.flush();
			fw.close();
			bw.close();
			pw.close();
			
			fw = new FileWriter(code_path, true); 
			bw = new BufferedWriter(fw); 
			pw = new PrintWriter(bw); 
			pw.println("\n\n Answer");
			pw.println(code);
			pw.flush();
			fw.close();
			bw.close();
			pw.close();
		}
		catch(Exception e)
		{
			System.out.println("case ans file error" + e);
		}
	}
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		answer = request.getParameter("answer-des");
		code = request.getParameter("answer-code");
		des_path = request.getParameter("des-path");
		code_path = request.getParameter("code-path");
		add_ans(answer, code, des_path, code_path);
		response.sendRedirect("http://localhost:8080/BNM_Stack_Exchange/CseHome");
	}
}