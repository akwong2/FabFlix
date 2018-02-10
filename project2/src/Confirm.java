

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Servlet implementation class Confirm
 */
@WebServlet("/Confirm")
public class Confirm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Confirm() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		String loginUser = "mytestuser";
		String loginPasswd = "mypassword";
		
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb?autoReconnect=true&useSSL=false";
        
		String title = request.getParameter("title");
		String quan = request.getParameter("quan");
		int cID = Integer.parseInt(request.getParameter("id"));
		System.out.println(title);
		System.out.println(quan);
		System.out.println(cID);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDate localdate = LocalDate.now();
		System.out.println(dtf.format(localdate));
		
		
		
		try {
	        
			Class.forName("com.mysql.jdbc.Driver").newInstance();
	        Connection dbcon = DriverManager.getConnection(loginUrl,loginUser, loginPasswd);
			Statement statement = dbcon.createStatement();
			String query = "select movies.id from movies where movies.title='"+title+"'";
			ResultSet rs = statement.executeQuery(query);
			rs.next();
			String mID = rs.getString("movies.id");
				
			String salesQuery = "INSERT INTO sales VALUES(DEFAULT,"+cID+",'"+mID+"', '"+dtf.format(localdate)+"')";
			try {
				statement.executeUpdate(salesQuery);
				String IDquery = "select sales.id\n" + 
						"from sales\n" + 
						"where customerId = "+cID+" and movieId = '"+mID+"' and saleDate = '"+dtf.format(localdate)+"'";
				ResultSet searchq = statement.executeQuery(IDquery);
				if (searchq.next()) {
					out.write("Transaction Number: " + searchq.getString("sales.id")+"\n");
				}
				out.write("Thank you for purchasing from FabFlix");
				rs.close();
		        statement.close();
		        dbcon.close();
			}
			catch(SQLException ex){
				while (ex != null) {
	                System.out.println("SQL Exception:  " + ex.getMessage());
	                ex = ex.getNextException();
	            } // end while
				
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
