

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

/**
 * Servlet implementation class addMovie
 */
@WebServlet("/addMovie")
public class addMovie extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public addMovie() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		PrintWriter out = response.getWriter();
		
		String loginUser = "root";
		String loginPasswd = "2228848";
		
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb?autoReconnect=true&useSSL=false";
        
        //response.setContentType("text/html");
 
        try {
	        Class.forName("com.mysql.jdbc.Driver").newInstance();

	        Connection dbcon = DriverManager.getConnection(loginUrl,loginUser, loginPasswd);
			Statement statement = dbcon.createStatement();
			Statement statement2 = dbcon.createStatement();
			Statement statement3 = dbcon.createStatement();
			Statement statement4 = dbcon.createStatement();
			String title = request.getParameter("title");
			String year = request.getParameter("year");
			String director = request.getParameter("director");
			String starName = request.getParameter("starName");
			String starYear = request.getParameter("starYear");
			if (starYear.length() == 0) {
				starYear = "NULL";
			}
			String genre = request.getParameter("genre");
			
			String query = "select id from movies where title ='"+title+"' and\n"
					+ "year = "+year+" and director = '"+director+"'";
			String query2 = "select id from stars where name ='"+starName+"'";

			ResultSet rs = statement.executeQuery(query); // if movie exists
			ResultSet rs2 = statement2.executeQuery(query2); // if star exists
			
			String movieid ="";
			String starid ="";
			if (rs.next()) {
				movieid = rs.getString("id");
			} else {
				String querymovieid = "select max(id) from moviedb.movies";

				ResultSet rsmovieid = statement3.executeQuery(querymovieid);
	
				while (rsmovieid.next()) {
					String maxID = rsmovieid.getString("max(id)");
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
					movieid = id;
				}
				rsmovieid.close();
			}
			
			if (rs2.next()) {
				starid = rs2.getString("id");
			} else {
				//generate star id
				String querystarid = "select max(id) from moviedb.stars";

				ResultSet rsstarid = statement4.executeQuery(querystarid);
	
				while (rsstarid.next()) {
					String maxID = rsstarid.getString("max(id)");
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
					starid = id;	
				}
				rsstarid.close();
			}
			
			String querycall = "call add_movie('"+movieid+"','"+title+"',\n"
					+year+",'"+director+"','"+starid+"',\n" + 
					"'"+starName+"', "+starYear+", '"+genre+"', @a, @b, @c)";
			
			Statement statement5 = dbcon.createStatement();
			ResultSet rscall = statement5.executeQuery(querycall);
			
			
			Statement statement6 = dbcon.createStatement();
			Statement statement7 = dbcon.createStatement();
			Statement statement8 = dbcon.createStatement();
			String a = "select @a";
			String b = "select @b";
			String c = "select @c";
			ResultSet rsA = statement6.executeQuery(a);
			ResultSet rsB = statement7.executeQuery(b);
			ResultSet rsC = statement8.executeQuery(c);
					
			rsA.next();
			rsB.next();
			rsC.next();
			JsonObject responseJsonObject = new JsonObject();

			if (Integer.parseInt(rsA.getString("@a")) == 1) {
//				JsonObject responseJsonObject = new JsonObject();
				responseJsonObject.addProperty("a", "found");
				responseJsonObject.addProperty("messageA", "Movie already in database");
//				out.write(responseJsonObject.toString());
			} else {
//				JsonObject responseJsonObject = new JsonObject();
				responseJsonObject.addProperty("a", "success");
				responseJsonObject.addProperty("messageA", "Movie added!");
//				out.write(responseJsonObject.toString());
			}
			if (Integer.parseInt(rsB.getString("@b")) == 1) {
//				JsonObject responseJsonObject = new JsonObject();
				responseJsonObject.addProperty("b", "found");
				responseJsonObject.addProperty("messageB", "Star already in database");
//				out.write(responseJsonObject.toString());
			} else {
//				JsonObject responseJsonObject = new JsonObject();
				responseJsonObject.addProperty("b", "success");
				responseJsonObject.addProperty("messageB", "Star added!");
//				out.write(responseJsonObject.toString());
			}
			if (Integer.parseInt(rsC.getString("@c")) == 1) {
//				JsonObject responseJsonObject = new JsonObject();
				responseJsonObject.addProperty("c", "found");
				responseJsonObject.addProperty("messageC", "Genre already in database");
//				out.write(responseJsonObject.toString());
			} else {
//				JsonObject responseJsonObject = new JsonObject();
				responseJsonObject.addProperty("c", "success");
				responseJsonObject.addProperty("messageC", "Genre added!");
//				out.write(responseJsonObject.toString());
			}
			out.write(responseJsonObject.toString());
			rs.close();
			rs2.close();
			rscall.close();
			rsA.close();
			rsB.close();
			rsC.close();
			
	        statement.close();
	        statement2.close();
	        statement3.close();
	        statement4.close();
	        statement5.close();
	        statement6.close();
	        statement7.close();
	        statement8.close();
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
