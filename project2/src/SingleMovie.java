

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
 * Servlet implementation class SingleMovie
 */
@WebServlet("/SingleMovie")
public class SingleMovie extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SingleMovie() {
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
        
		
		try {
			String param = request.getParameter("id");
	        Class.forName("com.mysql.jdbc.Driver").newInstance();

	        Connection dbcon = DriverManager.getConnection(loginUrl,loginUser, loginPasswd);
			Statement statement = dbcon.createStatement();
			String query = "Select movies.id, title,year,director,group_concat(distinct stars.name), group_concat(distinct genres.name) \n" + 
					"From movies, stars_in_movies, stars, genres, genres_in_movies \n" + 
					"Where stars_in_movies.movieId = movies.id and stars_in_movies.starId = stars.id \n" + 
					"and genres.id = genres_in_movies.genreId and genres_in_movies.movieId = movies.id \n" + 
					"and movies.id=\""+param+"\"";

			ResultSet rs = statement.executeQuery(query);
			JsonArray jsonArray = new JsonArray();
			
			while (rs.next()) {
				String id = rs.getString("movies.id");
				String t = rs.getString("title");
				String y = rs.getString("year");
				String d = rs.getString("director");
				String sN = rs.getString("group_concat(distinct stars.name)");
				String gN = rs.getString("group_concat(distinct genres.name)");
				JsonObject responseJsonObject = new JsonObject();
				responseJsonObject.addProperty("id", id);
				responseJsonObject.addProperty("title", t);
				responseJsonObject.addProperty("year", y);
				responseJsonObject.addProperty("director", d);
				responseJsonObject.addProperty("starsName", sN);
				responseJsonObject.addProperty("genresName", gN);
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
