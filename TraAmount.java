package com.jsp.jdbc;

import java.io.IOException;
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
@WebServlet("/TraAmount")
public class TraAmount extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		 String amount1=req.getParameter("amount");
		 int amount=Integer.parseInt(amount1);
		 
		 HttpSession session = req.getSession();
		 String mobile=(String)session.getAttribute("mobile");
		 
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
						int sub=damount-amount;
						 
						String update="update bank set amount=? where mobile=?";
						PreparedStatement ps1 = connection.prepareStatement(update);
						ps1.setInt(1, sub);
						ps1.setString(2, mobile);
						int num=ps1.executeUpdate();
						if (num!=0)
						{
							session.setAttribute("amount",amount1);
							RequestDispatcher rd = req.getRequestDispatcher("ToMobile.html");
							rd.include(req, resp);
						}
						 					
				}
				 
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		 
	}

