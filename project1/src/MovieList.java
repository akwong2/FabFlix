
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.annotation.WebServlet;

import java.sql.*;

/**
 * Servlet implementation class MovieList
 */
@WebServlet("/MovieList")
public class MovieList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MovieList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		PrintWriter out = response.getWriter();
		
		String loginUser = "mytestuser";
        String loginPasswd = "mypassword";
//		String loginUser = "root";
//        String loginPasswd = "2228848";
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb?autoReconnect=true&useSSL=false";
        
        response.setContentType("text/html");
        
        out.println("<HTML><HEAD><TITLE>MovieDB</TITLE></HEAD>");
        out.println("<BODY style=\"background-color:powderblue\"><H1 style=\"color:blue\">Top 20 Rated Movies</H1>");
 
        try {
	        Class.forName("com.mysql.jdbc.Driver").newInstance();

	        Connection dbcon = DriverManager.getConnection(loginUrl,loginUser, loginPasswd);
			Statement statement = dbcon.createStatement();
			String query = "Select title,year,director,rating, group_concat(distinct stars.name), group_concat(distinct genres.name) "
					+ "From movies, ratings, stars_in_movies, stars, genres, genres_in_movies "
					+ "Where ratings.movieId = movies.id and stars_in_movies.movieId = movies.id "
					+ "and stars_in_movies.starId = stars.id and genres.id = genres_in_movies.genreId and genres_in_movies.movieId = movies.id "
					+ "group by movies.title,movies.year,movies.director,ratings.rating "
					+ "order by rating desc "
					+ "limit 20";
			ResultSet rs = statement.executeQuery(query);
			
			out.println("<TABLE border>");
			out.println("<tr style=\"color:yellow\">"
						+ "<th>Movie Number</th>"
						+ "<th>Title</th>" 
						+ "<th>Year</th>"
						+ "<th>Director</th>"
						+ "<th>List of Genres</th>"
						+ "<th>List of Stars</th>"
						+ "<th>Rating</th>"
					+ "</tr>");
			int counter = 1;
			while (rs.next()) {
				String m_title = rs.getString("title");
				String m_year = rs.getString("year");
				String m_director = rs.getString("director");
				String m_lgenres = rs.getString("group_concat(distinct genres.name)");
				String m_lstars = rs.getString("group_concat(distinct stars.name)");
				String m_rating = rs.getString("rating");
	            	out.println("<tr>"
	            				+ "<td>"+counter+"</td>"
            					+ "<td>"+m_title+"</td> "
            					+ "<td>"+m_year +"</td>"
            					+ "<td>"+m_director+"</td>"
            					+ "<td>"+m_lgenres+"</td>"
            					+ "<td>"+m_lstars+"</td>"
            					+ "<td>"+m_rating+"</td>"
        					+ "</tr>");
	            	counter++;
	            
			}
			out.println("</TABLE>");
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
