package com.jsp.jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/Amount")
public class Amount extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		 String temp=req.getParameter("amount");
		 int amount=Integer.parseInt(temp);
		 PrintWriter writer = resp.getWriter();
		 HttpSession session = req.getSession();
		 String mb=(String)session.getAttribute("mobile");
		 if (amount>0)
		 {
			String url="jdbc:mysql://localhost:3306/teca39?user=root&password=12345";
			String select="select * from bank where mobile=?";
			
			try {
				Connection connection = DriverManager.getConnection(url);
				PreparedStatement ps = connection.prepareStatement(select);
				ps.setString(1, mb);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) 
				{
					int damount = rs.getInt(4);
					if (amount<=damount)
					{
						Class.forName("com.mysql.jdbc.Driver");
						int sub=damount-amount;
						String update="update bank set amount=? where mobile=?";
						PreparedStatement ps1 = connection.prepareStatement(update);
						ps1.setInt(1, sub);
						ps1.setString(2, mb);
						int num=ps1.executeUpdate();
						if (num!=0)
						{
							writer.println("<h1>withdraw succesful</h1>");
						}
						else
						{
							RequestDispatcher rd = req.getRequestDispatcher("Amount.html");
							rd.include(req, resp);
							writer.println("404 error");
						}
					}
					else
					{
						RequestDispatcher rd = req.getRequestDispatcher("Amount.html");
						rd.include(req, resp);
						writer.println("<h1>Insuuficient balance</h1>");
					}
				}
				else
				{
					RequestDispatcher rd = req.getRequestDispatcher("Amount.html");
					rd.include(req, resp);
					writer.println("<h1>Invalid Details</h1>");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
