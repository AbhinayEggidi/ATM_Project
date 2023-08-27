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
@WebServlet("/Credit")
public class Credit extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		 String mb = req.getParameter("mobile");
		 String pin = req.getParameter("pin");
		 
		 
		 String url="jdbc:mysql://localhost:3306/teca39?user=root&password=12345";
		 String select="select * from bank where mobile=? and pin=?";
		 
		 try {
			 Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement ps = connection.prepareStatement(select);
			ps.setString(1,mb);
			ps.setString(2, pin);
			PrintWriter writer = resp.getWriter();
			ResultSet rs=ps.executeQuery();
			if (rs.next())
			{
				RequestDispatcher rd = req.getRequestDispatcher("AddAmount.html");
				rd.include(req, resp);
			}
			else
			{
				writer.println("<h1>Invalid </h1>");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}
}
