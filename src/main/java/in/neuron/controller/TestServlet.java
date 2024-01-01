package in.neuron.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
		urlPatterns = { "/TestServlet" }, 
		initParams = { 
				@WebInitParam(name = "url", value = "jdbc:mysql:///manish"), 
				@WebInitParam(name = "userName", value = "root"), 
				@WebInitParam(name = "psw", value = "Manish@3123")
		},loadOnStartup = 10)
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;
	PreparedStatement pstm = null;
	
	static {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				System.out.println("Driver Successfully loaded....");
			}catch(ClassNotFoundException e) {
				e.printStackTrace();
			}
	}
	
	public void init() throws ServletException{
		String url = getInitParameter("url");
		String userName = getInitParameter("userName");
		String pw = getInitParameter("psw");
		try (Connection connection = DriverManager.getConnection(url)) {
			System.out.println("Connection Created successfully....");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getParameter("username");
		String userage = request.getParameter("userage");
		String useraddress = request.getParameter("useraddress");
		String sqlQuery = "insert into student (name,age,address) Values(?,?,?)";
		 
		try {
			if(connection!=null)
				pstm = connection.prepareStatement(sqlQuery);
			if(pstm!=null) {
				pstm.setString(1,username);
				pstm.setInt(2, Integer.parseInt(userage));
				pstm.setString(3,useraddress);
				
			}
			
			if(pstm!=null) {
				int rowAffected = pstm.executeUpdate();
				PrintWriter out = null;
				out = response.getWriter();
				if(rowAffected == 1) {
					out.println("<h1 style='color:green; text-align: center;'>REGISTRATION SUCCESSFULL</h1>");
				}
				else {
					out.println("<h1 style='color:green; text-align: center;'>REGISTRATION FAILED TRY AGAIN WITH THE FOLLOWING LINK</h1>");
					out.println("<a heaf='./reg.html'/>|REGISTRATION|</a>");
				}
				
				out.close();
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
//		public destroy()
		
		
		
	}

}
