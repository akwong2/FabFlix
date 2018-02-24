
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.sql.ResultSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class importMovies {

	//No generics
//	List myEmpls;
	Document dom;


	public importMovies(){
//		myEmpls = new ArrayList();
	}

	public void runExample() {
		
		//parse the xml file and get the dom object
		parseXmlFile();
		
		//get each employee element and create a Employee object
		parseDocument();
		
	}
	
	
	private void parseXmlFile(){
		//get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
		try {
			
			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			//parse using builder to get DOM representation of the XML file
			dom = db.parse("mains243.xml");
			

		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}

	
	private void parseDocument(){
		//get the root elememt
		Element docEle = dom.getDocumentElement();
		
		//get a nodelist of <employee> elements
		NodeList nl = docEle.getElementsByTagName("directorfilms");
		
//		String loginUser = "root";
//		String loginPasswd = "2228848";
		
		String loginUser = "mytestuser";
		String loginPasswd = "mypassword";
		
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb?autoReconnect=true&useSSL=false";
		
        try {
	        Class.forName("com.mysql.jdbc.Driver").newInstance();

	        Connection dbcon = DriverManager.getConnection(loginUrl,loginUser, loginPasswd);
	        String sql = "insert into moviedb.movies (id, title,year,director) values (?,?,?,?)";
	        String sqlGenres = "insert into moviedb.genres (id, name) values (?,?)";
	        String sqlGenres_in_Movies = "insert into moviedb.genres_in_movies (genreId, movieId) values (?,?)";
	        
	        PreparedStatement ps = dbcon.prepareStatement(sql);
	        //PreparedStatement psGenres = dbcon.prepareStatement(sqlGenres);
	        PreparedStatement psGenres_in_Movies = dbcon.prepareStatement(sqlGenres_in_Movies);
	        // directorfilms
	        
	        Statement statement = dbcon.createStatement();
	        Statement statementGenres = dbcon.createStatement();
			String querymovieid = "select max(id) from moviedb.movies";
			String querygenresid = "select max(id) from moviedb.genres";

			ResultSet rsmovieid = statement.executeQuery(querymovieid);
			rsmovieid.next();
			String maxID = rsmovieid.getString("max(id)");
			if (maxID == null) {
				maxID = "aa0000000";
			}
			ResultSet rsgenresid = statementGenres.executeQuery(querygenresid);
			rsgenresid.next();
			String maxIDGenresStr = rsgenresid.getString("max(id)");
			int maxIDGenres;
			
			if (maxIDGenresStr == null) {
				maxIDGenres = 0;
			} else {
				maxIDGenres = Integer.parseInt(maxIDGenresStr);
			}
	        
			HashMap <Integer, String> gmap = new HashMap <Integer, String>();
			
			HashMap <Integer, ArrayList<String>> ginmmap = new HashMap <Integer, ArrayList<String>>();
			
			for (int i=0;i<nl.getLength();++i) {
				
				Element e = (Element)nl.item(i);
				Node dir = e.getChildNodes().item(0);
				Node films = e.getChildNodes().item(1);
				
				String dirName="NULL";	
		
				// director
				for (int j=0;j<dir.getChildNodes().getLength();++j) {
					if (dir.getChildNodes().item(j).getNodeName().equals("dirname")) {
						dirName = dir.getChildNodes().item(j).getTextContent();
					}else if (dir.getChildNodes().item(j).getNodeName().equals("dirn")) {
						dirName = dir.getChildNodes().item(j).getTextContent();
					}
				}
				NodeList filmList = films.getChildNodes();
				// films
				for (int k=0;k<filmList.getLength();++k) {
					// film
					String id = "";
					char first = maxID.charAt(0);
					char second = maxID.charAt(1);
					String numS = maxID.substring(2);
					int num = Integer.parseInt(numS);
					
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
					
					Node film = filmList.item(k);
				
					NodeList filmInfo = film.getChildNodes();
					
					if (film.getNodeName().equals("film")) {
						String title = "NULL";
						String year = "NULL";
						ArrayList<String> cats = new ArrayList<String>();
						
						for (int l=0; l<filmInfo.getLength();++l) {
							if (filmInfo.item(l).getNodeName().equals("t")) {
								title = filmInfo.item(l).getTextContent();
							}
							if (filmInfo.item(l).getNodeName().equals("year")) {
								if (!filmInfo.item(l).hasChildNodes()){
									year = filmInfo.item(l).getTextContent();
								} else {
									year = filmInfo.item(l).getFirstChild().getTextContent();
								}
							}
							if (filmInfo.item(l).getNodeName().equals("cats")) {
								for (int m=0; m<filmInfo.item(l).getChildNodes().getLength();++m) {
									if (filmInfo.item(l).getChildNodes().item(m).getNodeName().equals("cat"))
										cats.add(filmInfo.item(l).getChildNodes().item(m).getTextContent());
								}
							}
						}
						if (year.equals("19yy")) {
							year = "1900";
						}
						else if(year.equals("199x")) {
							year = "1990";
						}
						else if(year.equals("196x")) {
							year = "1960";
						}
						else if(year.equals("198")) {
							year = "1980";
						}
						else if(year.equals("196")) {
							year = "1960";
						}
						year = year.substring(0, 4);
				
						
						
						
						for (String cat : cats) {
							PreparedStatement psGenres = dbcon.prepareStatement(sqlGenres);
							Statement statementGenreExist = dbcon.createStatement();
							String queryGenreExist = "select id from genres where name = '"+cat+"';";
							ResultSet rsGenreExist = statementGenreExist.executeQuery(queryGenreExist);
							if (!rsGenreExist.next()) {
								++maxIDGenres;
								gmap.put(maxIDGenres, cat);
								psGenres.setInt(1, maxIDGenres);
								psGenres.setString(2, cat);
//								psGenres.addBatch();
//								psGenres.executeBatch();
							}
							
							psGenres.close();
							statementGenreExist.close();
							rsGenreExist.close();
						}
						
						//psGenres.executeBatch();
//						psGenres_in_Movies.executeBatch();
						
						ps.setString(1, id);
						ps.setString(2, title);
						ps.setInt(3, Integer.parseInt(year));
						ps.setString(4, dirName);
						ps.addBatch();
						maxID = id;
					}
					rsmovieid.close();
					rsgenresid.close();
				}
			}
			//ps.executeBatch();
			
//			PreparedStatement psGenres = dbcon.prepareStatement(sqlGenres);
//			
//			for (Integer key : gmap.keySet()) {
//				String val = gmap.get(key);
//				psGenres.setInt(1, key);
//				psGenres.setString(2, val);
//				psGenres.addBatch();
//				System.out.println(psGenres);
//			}
			//System.out.println(psGenres);
			
			//psGenres_in_Movies.executeBatch();
			
			
	
			//statementGenres.close();
			statement.close();
			psGenres_in_Movies.close();
			ps.close();
			dbcon.close();
        }
        catch (SQLException ex) {
            while (ex != null) {
                System.out.println("SQL Exception:  " + ex.getMessage());
                ex = ex.getNextException();
            } // end while
        } // end catch SQLException

        catch (java.lang.Exception ex) {
            System.out.println("Java Exception: " +ex.getMessage());
            return;
        }
		
	}
	
	public static void main(String[] args){
		//create an instance
		importMovies dpe = new importMovies();
		
		//call run example
		dpe.runExample();
	}

}
