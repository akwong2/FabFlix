

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
 * Servlet implementation class Insert
 */
@WebServlet("/Insert")
public class Insert extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Insert() {
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
			Statement statement3 = dbcon.createStatement();
			String query = "select max(id) from moviedb.stars";

			ResultSet rs = statement.executeQuery(query);
			
//			System.out.println(rs.getString());
//			JsonArray jsonArray = new JsonArray();
//			
			while (rs.next()) {
				String maxID = rs.getString("max(id)");
				char first = maxID.charAt(0);
				char second = maxID.charAt(1);
				String numS = maxID.substring(2);
				int num = Integer.parseInt(numS);
				String id = "";
				if ( Integer.parseInt(numS)+1 == 10000000) {
					numS = "0000000";
					if (second == 'z') {
						if (first == 'z')
							first = 'a';
						else
							first++;
						second = 'a';
					} else {
						second++;
					}
					String firstS = String.valueOf(first);
					String secondS = String.valueOf(second);
					id += firstS + secondS + numS;
					

				} else {
					
					num = Integer.parseInt(numS) + 1;

					String firstS = String.valueOf(first);
					String secondS = String.valueOf(second);
					id += firstS + secondS + String.format("%07d", num);
					
				}
				String name = request.getParameter("name");
				String birthYear = request.getParameter("year");
				System.out.println(name);
				System.out.println(birthYear);
				String query2 = "select id from stars\n" + 
						"where name = \"" + name + "\" and birthYear = " + birthYear + ";";
				ResultSet rs2 = statement2.executeQuery(query2);
				if (rs2.next()) {
					JsonObject responseJsonObject = new JsonObject();
					responseJsonObject.addProperty("status", "found");
					responseJsonObject.addProperty("message", "Star already in database");
					out.write(responseJsonObject.toString());
				}
				else {
					String starInsert = "INSERT INTO stars VALUES('"+id+"','"+name+"',"+birthYear+")";
					statement3.executeUpdate(starInsert);
					JsonObject responseJsonObject = new JsonObject();
					responseJsonObject.addProperty("status", "success");
					responseJsonObject.addProperty("message", "Star added into database with id="
							+ id +", name="+ name+", and birthYear=" + birthYear );
					out.write(responseJsonObject.toString());
					
				}
				
				rs2.close();
			}
			rs.close();
	        statement.close();
	        statement2.close();
	        statement3.close();
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
