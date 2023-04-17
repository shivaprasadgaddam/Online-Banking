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


@WebServlet("/CheckBalance")
public class CheckBalance extends GenericServlet
{
	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		
		String accountNumber = Login.accountNumber;
		String pin = req.getParameter("pin");
		
		String url = "jdbc:mysql://localhost:3306/tejm32?user=root&password=12345";
		String select = "select * from bank where pin=?";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection(url);
			PreparedStatement ps = connect.prepareStatement(select);
			
			ps.setString(1,pin);
			
			ResultSet rs = ps.executeQuery();
			PrintWriter pw = res.getWriter();
			if(rs.next())
			{
				
				pw.println("Your Account Name : "+rs.getString(2)+"<br>");
				pw.println("Your Available Balance : "+rs.getDouble(5)+"<br>");
			}
			else
			{
				RequestDispatcher rd = req.getRequestDispatcher("checkBalance.html");
				rd.include(req, res);
				pw.write("<p style='color:red'>Invalid Pin</p>");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
