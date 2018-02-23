

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

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class Metadata
 */
@WebServlet("/Metadata")
public class Metadata extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Metadata() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		String loginUser = "root";
		String loginPasswd = "2228848";
		
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb?autoReconnect=true&useSSL=false";
        
        try {
	        Class.forName("com.mysql.jdbc.Driver").newInstance();

	        Connection dbcon = DriverManager.getConnection(loginUrl,loginUser, loginPasswd);
			Statement statement = dbcon.createStatement();
			Statement statement2 = dbcon.createStatement();
			String query = "show tables";

			ResultSet rs = statement.executeQuery(query);
			JsonArray jsonArray = new JsonArray();
			
			while (rs.next()) {
				String tables = rs.getString("Tables_in_moviedb");
				String description = "describe " + tables;
				ResultSet rsd = statement2.executeQuery(description);
				
				JsonArray attrArray = new JsonArray();
				while (rsd.next()) {
					String field = rsd.getString("Field");
					String type = rsd.getString("Type");
					JsonObject attrObject = new JsonObject();
					attrObject.addProperty("field", field);
					attrObject.addProperty("type", type);
					attrArray.add(attrObject);
				}
				JsonObject responseJsonObject = new JsonObject();
				responseJsonObject.addProperty("tables", tables);
				responseJsonObject.add("attributes", attrArray);

				jsonArray.add(responseJsonObject);
			}
			out.write(jsonArray.toString());
				
			rs.close();
	        statement.close();
	        dbcon.close();
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
