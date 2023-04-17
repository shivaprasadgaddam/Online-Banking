package bankProject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;


@WebServlet("/AccountDetails")
public class AccountDetails extends GenericServlet
{
	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		
		String accountNumber = Login.accountNumber;
		
		
		String url = "jdbc:mysql://localhost:3306/tejm32?user=root&password=12345";
		String select = "select * from bank where accountno=?";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection(url);
			PreparedStatement ps = connect.prepareStatement(select);
			
			ps.setString(1, accountNumber);
			
			ResultSet rs = ps.executeQuery();
			PrintWriter pw = res.getWriter();
			
			if(rs.next())
			{
				String sc = rs.getString(1);
				char[] ch = sc.toCharArray();
				pw.println("User Account Name : ");
				for(int i=0;i<ch.length;i++)
				{
					if(i<2 || i>=ch.length-2)
					{
						pw.print(ch[i]);
					}
					else
					{
						pw.print("X");
					}
				}
				pw.println("<br>");
				pw.println("User Account Name : "+rs.getString(2)+"<br>");
				pw.println("User  Email Id : "+rs.getString(3)+"<br>");
				pw.println("User Mobile Numbers : "+rs.getString(4)+"<br>");
				pw.println("User Account Balance : "+rs.getDouble(5)+"<br>");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
