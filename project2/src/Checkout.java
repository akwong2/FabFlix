

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.HashMap;

/**
 * Servlet implementation class Checkout
 */
@WebServlet("/Checkout")
public class Checkout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Checkout() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
		
		PrintWriter out = response.getWriter();
		String loginUser = "mytestuser";
		String loginPasswd = "mypassword";
		
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb?autoReconnect=true&useSSL=false";
        
		String fn = request.getParameter("fn");
		String ln = request.getParameter("ln");
		String cc = request.getParameter("cc");
		String ed = request.getParameter("ed");
		
		try {
	        
			Class.forName("com.mysql.jdbc.Driver").newInstance();
	        Connection dbcon = DriverManager.getConnection(loginUrl,loginUser, loginPasswd);
			Statement statement = dbcon.createStatement();
			String newEd = ed.replaceAll("-", "/");
			
			String query = "select creditcards.firstName, customers.id\n" + 
					"from creditcards, customers\n" + 
					"where creditcards.id = \""+cc+"\" and creditcards.firstName = \""+fn+"\" and\n" + 
					"creditcards.lastName = \""+ln+"\" and creditcards.expiration = \""+newEd+"\" and \n" + 
					"creditcards.firstName = customers.firstName and creditcards.lastName = customers.lastName";
			ResultSet rs = statement.executeQuery(query);

			if (rs.next()) {
				String cID = rs.getString("customers.id");
				JsonObject responseJsonObject = new JsonObject();
				responseJsonObject.addProperty("status", "success");
				responseJsonObject.addProperty("message", "success");
				responseJsonObject.addProperty("cID", cID);
				response.getWriter().write(responseJsonObject.toString());
				
				rs.close();
		        statement.close();
		        dbcon.close();
			}
			else {
				
				JsonObject responseJsonObject = new JsonObject();
				responseJsonObject.addProperty("status", "fail");
				
				responseJsonObject.addProperty("message", "Invalid Information");
				
				response.getWriter().write(responseJsonObject.toString());
				
				rs.close();
		        statement.close();
		        dbcon.close();
				
			}
        }
        catch (SQLException ex) {
            while (ex != null) {
                System.out.println("SQL Exception:  " + ex.getMessage());
                ex = ex.getNextException();
            } // end while
        } // end catch SQLException

        catch (java.lang.Exception ex) {
            out.println("<HTML>" + "<HEAD><TITLE>" + "MovieDB: Error" + "</TITLE></HEAD>\n<BODY>"
                    + "<P>SQL error in doGet: " + ex.getMessage() + "</P></BODY></HTML>");
            return;
        }
        out.close();
		
	}

}

