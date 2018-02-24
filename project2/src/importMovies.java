
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
	Document dom2;
	Document dom3;


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
		DocumentBuilderFactory dbf2 = DocumentBuilderFactory.newInstance();
		DocumentBuilderFactory dbf3 = DocumentBuilderFactory.newInstance();
		
		
		try {
			
			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			DocumentBuilder db2 = dbf2.newDocumentBuilder();
			
			//parse using builder to get DOM representation of the XML file
			dom = db.parse("mains243.xml");
			dom2 = db2.parse("actors63.xml");
			

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
		Element docEle2 = dom2.getDocumentElement();
		
		//get a nodelist of <employee> elements
		NodeList nl = docEle.getElementsByTagName("directorfilms");
		
		NodeList nl2 = docEle2.getElementsByTagName("actor");		
		
//		String loginUser = "root";
//		String loginPasswd = "2228848";
		
		String loginUser = "mytestuser";
		String loginPasswd = "mypassword";
		
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb?autoReconnect=true&useSSL=false";
		
        try {
	        Class.forName("com.mysql.jdbc.Driver").newInstance();

	        Connection dbcon = DriverManager.getConnection(loginUrl,loginUser, loginPasswd);
	        
	        Statement statementStarId = dbcon.createStatement();
	        String querystarid = "select max(id) from moviedb.stars";
	        ResultSet rsstarid = statementStarId.executeQuery(querystarid);
	        rsstarid.next();
	        String maxIDstar = rsstarid.getString("max(id)");
	        if (maxIDstar == null) {
	        		maxIDstar = "aa0000000";
	        }
	        
	        String sqlstar = "insert into moviedb.stars (id, name, birthYear) values (?,?,?)";
	        PreparedStatement psstar = dbcon.prepareStatement(sqlstar);
	        
	        for (int a = 0; a < nl2.getLength();++a) {
	        		NodeList actorInfo = nl2.item(a).getChildNodes();
	        		
	        		String sid = "";
	        		String stagename = "NULL";
	        		String dob = "NULL";
	        		
	        		
	        		for (int b = 0; b<actorInfo.getLength(); ++b) {
	        			if (actorInfo.item(b).getNodeName().equals("stagename")) {
	        				stagename = actorInfo.item(b).getTextContent();
	        				stagename = stagename.replaceAll("\"", "'");
	        			}
	        			if (actorInfo.item(b).getNodeName().equals("dob")) {
	        				if (!actorInfo.item(b).getTextContent().isEmpty()) {
	        					
	        					
	        					if (actorInfo.item(b).getTextContent().matches("\\d+")) {
	        						dob = actorInfo.item(b).getTextContent();
	        					}else {
	        						dob = "NULL";
	        					}	
	        				}
	        			}
	        			
	        			Statement statementStarExist = dbcon.createStatement();
	        	        String querystarExist = "select id from moviedb.stars where name = \""+stagename+"\";";
	        	        ResultSet rsstarExist = statementStarExist.executeQuery(querystarExist);
	        	        if (!rsstarExist.next()) {
	    					char first = maxIDstar.charAt(0);
	    					char second = maxIDstar.charAt(1);
	    					String numS = maxIDstar.substring(2);
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
	    						sid = firstS + secondS + numS;
	    					} else {
	    						num = Integer.parseInt(numS) + 1;
	    						String firstS = String.valueOf(first);
	    						String secondS = String.valueOf(second);
	    						sid = firstS + secondS + String.format("%07d", num);
	    					}
	        	        }
	        	        statementStarExist.close();
	        	        rsstarExist.close();
	        		}
	        		maxIDstar = sid;

	        		psstar.setString(1, sid);
				psstar.setString(2, stagename);
				if (dob.equals("NULL")) {
					psstar.setNull(3, 0);
				}else {
					psstar.setInt(3, Integer.parseInt(dob));
				}
				psstar.addBatch();

	        		
	        		
	        		System.out.println(psstar);
//	        		System.out.println(sid);
//	        		System.out.println(stagename);
//	        		System.out.println(dob);
	        }
	        psstar.executeBatch();
			psstar.close();
			statementStarId.close();
			rsstarid.close();
    	        
	        String sql = "insert into moviedb.movies (id, title,year,director) values (?,?,?,?)";
	        String sqlGenres = "insert into moviedb.genres (id, name) values (?,?)";
	        String sqlGenres_in_Movies = "insert into moviedb.genres_in_movies (genreId, movieId) values (?,?)";
	        
	        PreparedStatement ps = dbcon.prepareStatement(sql);
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
								if (cats.isEmpty()) {
									cats.add("None");
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
								psGenres.setInt(1, maxIDGenres);
								cat = cat.replaceFirst("^\\s*", "");
								psGenres.setString(2, cat);
								psGenres.addBatch();
								//psGenres.executeBatch();
								psGenres_in_Movies.setInt(1, maxIDGenres);
								psGenres_in_Movies.setString(2, id);
								psGenres_in_Movies.addBatch();
							} else {
								psGenres_in_Movies.setInt(1, rsGenreExist.getInt("id"));
								psGenres_in_Movies.setString(2, id);
								psGenres_in_Movies.addBatch();
							}
							psGenres.close();
							statementGenreExist.close();
							rsGenreExist.close();
						}
						
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
			//psGenres_in_Movies.executeBatch();
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
        		System.out.println(ex.getStackTrace()[0].getLineNumber());
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
