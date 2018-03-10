

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
 * Servlet implementation class movieStarSuggestion
 */
@WebServlet("/movieStarSuggestion")
public class movieStarSuggestion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public movieStarSuggestion() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String loginUser = "root";
		String loginPasswd = "2228848";
		
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb?autoReconnect=true&useSSL=false";
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();

	        Connection dbcon = DriverManager.getConnection(loginUrl,loginUser, loginPasswd);
			Statement statement = dbcon.createStatement();
			Statement statement2 = dbcon.createStatement();
			// setup the response json arrray
			JsonArray jsonArray = new JsonArray();
			
			// get the query string from parameter
			String searchQuery = request.getParameter("query");
			System.out.println(searchQuery);
			// return the empty json array if query is null or empty
			if (searchQuery == null || searchQuery.trim().isEmpty()) {
				response.getWriter().write(jsonArray.toString());
				return;
			}	
			
			searchQuery = searchQuery.replaceAll("[\\t\\n\\r]+","");			
			String[] prefix = searchQuery.trim().split("\\s+");

			String queryMovie = "";
			if (prefix.length>0) {
				queryMovie += "select movies.id, movies.title from movies where match (title) against ('"+prefix[0]+"*' in boolean mode)";
				for (int i = 1; i < prefix.length; ++i) {
					queryMovie += "\nand match(title) against ('"+prefix[i]+"*' in boolean mode)";
				}
			}
	
			if (queryMovie.equals("select movies.id, movies.title from movies where match (title) against ('*' in boolean mode)"))
				queryMovie = "select movies.id, movies.title from movies where match (title) against ('' in boolean mode)";
			
			// STAR
			String queryStar = "";
			if (prefix.length>0) {
				queryStar += "select stars.id, stars.name from stars where match (name) against ('"+prefix[0]+"*' in boolean mode)";
				for (int i = 1; i < prefix.length; ++i) {
					queryStar += "\nand match(name) against ('"+prefix[i]+"*' in boolean mode)";
				}
			}
	
			if (queryStar.equals("select stars.id, stars.name from stars where match (name) against ('*' in boolean mode)"))
				queryStar = "select stars.id, stars.name from stars where match (name) against ('' in boolean mode)";
			
			ResultSet rs = statement.executeQuery(queryMovie);
			ResultSet rs2 = statement2.executeQuery(queryStar);
			System.out.println(queryMovie);
			System.out.println(queryStar);
			
			//jsonArray.add(generateJsonObject(id, heroName, "marvel"));
			int movieLimit = 0;
			int starLimit = 0;
			while (rs.next() && movieLimit < 5) {
				String movieId = rs.getString("movies.id");
				String movieTitle = rs.getString("movies.title");
				jsonArray.add(generateJsonObject(movieId, movieTitle, "MOVIES"));
				++movieLimit;
			}
			while (rs2.next() && starLimit < 5) {
				String starId = rs2.getString("stars.id");
				String starName = rs2.getString("stars.name");
				jsonArray.add(generateJsonObject(starId, starName, "STARS"));
				++starLimit;
				
			}
			response.getWriter().write(jsonArray.toString());
			rs2.close();
			rs.close();
			statement2.close();
			statement.close();
			dbcon.close();
			return;
			
//			// search on marvel heros and DC heros and add the results to JSON Array
//			// this example only does a substring match
//			// TODO: in project 4, you should do full text search with MySQL to find the matches on movies and stars
//			
//			for (Integer id : marvelHerosMap.keySet()) {
//				String heroName = marvelHerosMap.get(id);
//				if (heroName.toLowerCase().contains(query.toLowerCase())) {
//					jsonArray.add(generateJsonObject(id, heroName, "marvel"));
//				}
//			}
//			
//			for (Integer id : dcHerosMap.keySet()) {
//				String heroName = dcHerosMap.get(id);
//				if (heroName.toLowerCase().contains(query.toLowerCase())) {
//					jsonArray.add(generateJsonObject(id, heroName, "dc"));
//				}
//			}
//			
//			response.getWriter().write(jsonArray.toString());
//			return;
			
		} catch (Exception e) {
			System.out.println(e);
			response.sendError(500, e.getMessage());
		}
        out.close();
	}
	
	/*
	 * Generate the JSON Object from hero and category to be like this format:
	 * {
	 *   "titleName": "Iron Man",
	 *   "data": { "category": "marvel", "id": 11 }
	 * }
	 * 
	 */
	private static JsonObject generateJsonObject(String id, String value, String categoryName) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("value", value);
		
		JsonObject additionalDataJsonObject = new JsonObject();
		additionalDataJsonObject.addProperty("category", categoryName);
		additionalDataJsonObject.addProperty("id", id);
		
		jsonObject.add("data", additionalDataJsonObject);
		return jsonObject;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
