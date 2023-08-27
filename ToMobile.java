package com.jsp.jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/ToMobile")
public class ToMobile extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		 String mobile=req.getParameter("mb"); 
		 HttpSession session = req.getSession();
		 String amo=(String)session.getAttribute("amount");
		 int amount=Integer.parseInt(amo);
		 String url="jdbc:mysql://localhost:3306/teca39?user=root&password=12345";
		 String select="select * from bank where mobile=?";
			
			try {
				Connection connection = DriverManager.getConnection(url);
				PreparedStatement ps = connection.prepareStatement(select);
				ps.setString(1, mobile);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) 
				{
					int damount = rs.getInt(4);
					 
						Class.forName("com.mysql.jdbc.Driver");
						int sub=damount+amount;
						String update="update bank set amount=? where mobile=?";
						PreparedStatement ps1 = connection.prepareStatement(update);
						ps1.setInt(1, sub);
						ps1.setString(2, mobile);
						int num=ps1.executeUpdate();
						PrintWriter writer = resp.getWriter();
						if (num!=0)
						{
							writer.println("<h1>transfer succesfully</h1>");
							 
						}	
				}
				else
				{
					RequestDispatcher rd = req.getRequestDispatcher("Amount.html");
					rd.include(req, resp);
					 
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	}
}
