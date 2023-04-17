package bankProject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.GenericServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet("/MoneyTransfer")
public class MoneyTransfer extends GenericServlet
{
	@Override
	public void service(ServletRequest req, ServletResponse resp) throws ServletException, IOException 
	{
		String accountNumber = Login.accountNumber;
		String name = req.getParameter("name");
		String accountNum = req.getParameter("an");
		String amount = req.getParameter("amount");
		int ac = Integer.parseInt(amount);
		String pin = req.getParameter("pin");
		
		String url ="jdbc:mysql://localhost:3306/tejm32?user=root&password=12345";
		
		String pselect ="select * from bank where pin = ?";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection(url);
			PreparedStatement ps = connect.prepareStatement(pselect);
			ps.setString(1, pin);
			ResultSet rs = ps.executeQuery();
			PrintWriter pw = resp.getWriter();
			if(rs.last())
			{
				String cbalance = rs.getString(5);
				int cb = Integer.parseInt(cbalance);
				int fb = cb-ac;
				String toselect ="update bank set Balance = "+fb+" where pin = "+pin;
				Statement s = connect.createStatement();
				int num = s.executeUpdate(toselect);
				if (num!=0)
				{
					String fselect ="select * from bank where AccountNo = "+accountNum;
					Statement s1 = connect.createStatement();
					ResultSet rs1 = s1.executeQuery(fselect);
					if(rs.last())
					{
						String b = rs.getString(5);
						int ba = Integer.parseInt(cbalance);
						int t = cb+ba;
						String fupdate ="update bank set Balance = "+t+" where AccountNo = "+accountNum;
						Statement s2 = connect.createStatement();
						int num1 = s2.executeUpdate(fupdate);
						if(num1!=0)
						{
							pw.println("From Account : "+accountNumber);
							pw.println("To Account : "+accountNum);
							pw.println("Amount Transfered : "+amount);
						}
					}
					
				}
			}
			else
			{
				RequestDispatcher rd = req.getRequestDispatcher("moneytransfer.html");
				rd.include(req, resp);
				pw.write("<P style='color:red'>Inavlid PIN</p>");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
