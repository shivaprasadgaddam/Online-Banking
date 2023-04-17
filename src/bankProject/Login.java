package bankProject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.GenericServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet("/Login")
public class Login extends GenericServlet
{
	public static String accountNumber;
	
	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		
		 String email = req.getParameter("ac");
		 String password = req.getParameter("mb");
		 
		 	String url = "jdbc:mysql://localhost:3306/tejm32?user=root&password=12345";
			String select = "select * from bank where email=? and password=?";
		 try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection connect = DriverManager.getConnection(url);
				PreparedStatement ps = connect.prepareStatement(select);
				
				ps.setString(1, email);
				ps.setString(2, password);
				
				ResultSet rs = ps.executeQuery();
				PrintWriter pw = res.getWriter();
				if(rs.next())
				{
				accountNumber = rs.getString(1);
				
					if(accountNumber!=null)
					{
						RequestDispatcher rd = req.getRequestDispatcher("bankInterface.html");
						rd.forward(req, res);
					}
				}
				else
				{
					RequestDispatcher rd = req.getRequestDispatcher("login.html");
					rd.include(req, res);
					pw.write("<p style='color:red'>Invalid UserName Or Password</p>");
				}
		 }
		 catch (Exception e) {
			// TODO: handle exception
		}
		 
	}

}
