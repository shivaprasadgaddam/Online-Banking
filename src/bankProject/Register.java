package bankProject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.GenericServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet("/Register")
public class Register extends GenericServlet
{
	@Override
	public void service(ServletRequest req, ServletResponse resp) throws ServletException, IOException {
		
		String name = req.getParameter("name");
		String AccountNo=req.getParameter("ac");
		String MobileNo = req.getParameter("mb");
		String Email = req.getParameter("email");
		String password = req.getParameter("pass");
		String pin = req.getParameter("pin");
		
		String url="jdbc:mysql://localhost:3306/tejm32?user=root&password=12345";
		
		String insert = "insert into bank (AccountNo, Name, email, mobileno, password, pin) values(?,?,?,?,?,?) ";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection(url);
			PreparedStatement ps = connect.prepareStatement(insert);
			ps.setString(1, AccountNo);
			ps.setString(2, name);
			ps.setString(3, Email);
			ps.setString(4,MobileNo);
			ps.setString(5, password);
			ps.setString(6, pin);
			PrintWriter pw =resp.getWriter();
			int num = ps.executeUpdate();
			if(num!=0)
			{
				pw.print("Successfully Registered.....");
			}
			else
			{
				pw.print("Enter Proper Details");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		
	}
}
