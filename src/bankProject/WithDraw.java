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


@WebServlet("/WithDraw")
public class WithDraw extends GenericServlet
{
	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		
		String accountNumber = Login.accountNumber;
		String pin = req.getParameter("pin");
		 String withDraw = req.getParameter("wd");
		
		String url = "jdbc:mysql://localhost:3306/tejm32?user=root&password=12345";
		String select = "select * from bank where pin=?";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection(url);
			PreparedStatement ps = connect.prepareStatement(select);
			
			ps.setString(1, pin);
			
			ResultSet rs = ps.executeQuery();
			PrintWriter pw = res.getWriter();
			if(rs.next())
			{
			pw.println("Your Account Name : "+rs.getString(2)+"<br>");
			double balance = rs.getDouble(5);
			double wd = Double.parseDouble(withDraw);
			double currentBalance = balance-wd;
			
			
			String insert = "update bank set Balance="+currentBalance+" where AccountNo="+accountNumber;

			int num = ps.executeUpdate(insert);
			if(num!=0)
			{
				pw.println("Your Available Balance : "+balance+"<br>");
				pw.println("Your WithDraw Amount : "+wd+"<br>");
				
				pw.println("Your CurrentBalance : "+currentBalance+"<br>");
			}
			}
			else
			{
				RequestDispatcher rd = req.getRequestDispatcher("withDraw.html");
				rd.include(req, res);
				pw.write("<P style='color:red'>Inavlid PIN</p>");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
