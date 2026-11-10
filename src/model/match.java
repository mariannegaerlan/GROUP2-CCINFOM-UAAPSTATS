package model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class match{

	//Match attributes
	private int match_ID;
	private LocalDate match_date;
	private String home_teamcode;
	private String away_teamcode;
	private int away_score;
	private int home_score;
	private int top_scorer;
	private String venue;
	private enum Round{
		Round_1, Round_2, Semi_Finals, Battle_for_Third, Championship
	}
	private Round round;
	private int season;

	//Constructor

	public match(int match_ID, LocalDate match_date, String home_teamcode, String away_teamcode, int away_score, int home_score, int top_scorer, String venue, Round round, int season){
		this.match_ID = match_ID;
		this.match_date = match_date;
		this.home_teamcode = home_teamcode;
		this.away_teamcode = away_teamcode;
		this.away_score = away_score;
		this.home_score = home_score;
		this.top_scorer = top_scorer;
		this.venue = venue;
		this.round = round;
		this.season = season;
	}

	//getters

	public int getMatchID(){
		return this.match_ID;
	}

	public LocalDate getMatchDate(){
		return this.match_date;
	}

	public String getHomeTeamCode(){
		return this.home_teamcode;
	}

	public String getAwayTeamCode(){
		return this.away_teamcode;
	}

	public int getAwayScore(){
		return this.away_score;
	}

	public int getHomeScore(){
		return this.home_score;
	}

	public int getTopScorer(){
		return this.top_scorer;
	}

	public String getVenue(){
		return this.venue;
	}

	public Round getRound(){
		return this.round;
	}

	public int getSeason(){
		return this.season;
	}

	//Setters

	public void setMatchID(int match_ID){
		this.match_ID = match_ID;
	}

	public void setDate(LocalDate match_date){
		this.match_date = match_date;
	}

	public void setHomeTeamCode(String home_teamcode){
		this.home_teamcode = home_teamcode;
	}

	public void setAwayTeamCode(String away_teamcode){
		this.away_teamcode = away_teamcode;
	}

	public void setAwayScore(int away_score){
		this.away_score = away_score;
	}

	public void setHomeScore(int home_score){
		this.home_score = home_score;
	}

	public void setTopScorer(int top_scorer){
		this.top_scorer = top_scorer;
	}

	public void setVenue(String venue){
		this.venue = venue;
	}

	public void setRound(Round round){
		this.round = round;
	}

	public void setSeason(int season){
		this.season = season;
	}

	//Display method for testing

	public void displayMatchInfo(){
		System.out.println("Match Id" + this.match_ID + " | Match Date: " + this.match_date + " | Home Teamcode: " + this.home_teamcode + 
		" | Away Teamcode: " + this.away_teamcode + " | Home Score: " + this.home_score + 
		" | Away Score: " + this.away_score + " | Top Scorer: " + this.top_scorer + " | Venue: " + this.venue + 
		" | Round: " + this.round + " | Season: " + this.season);
	}

	//Database Connection

	private static final String DB_URL = System.getenv().getOrDefault("DB_URL","jdbc:mysql://localhost:3306/uaapstats");
    private static final String USER = System.getenv().getOrDefault("DB_USER","root");
    private static final String PASSWORD = System.getenv().getOrDefault("DB_PASSWORD",""); 

    private static Connection conn = null;

    //database methods

    private static boolean connectDB(){
    	try{
    		conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
    		System.out.println("UAAP DB Connected Successfully.");
    		return true;
    	} catch (SQLException e){
    		System.out.println(e.getMessage());
    		return false;
    	}
    }

    public static List<match> getAllMatches(){
    	List<match> matches = new ArrayList<>();

    	try{
    		if(conn == null || conn.isClosed()){
    			if(!connectDB()){
    				return matches;
    			}
    		}
    	} catch(SQLException e){
    		System.out.println(e.getMessage());
    		return matches;
    	}

    	StringBuilder query = new StringBuilder("SELECT * FROM match_record_management ");

    	try(Statement stmt = conn.createStatement();
    	ResultSet rs = stmt.executeQuery(query.toString())){
    		while(rs.next()){
    			String statusStr = rs.getString("round");
    			match.Round round = match.round.ValueOf(statusStr);
    			LocalDate match_date = rs.getObject("match_date",LocalDate.class);

    			match m = new match(
    				rs.getInt("match_ID"),
    				match_date
    				rs.getString("home_teamcode"),
    				rs.getString("away_teamcode"),
    				rs.getInt("away_score"),
    				rs.getInt("home_score"),
    				rs.getInt("top_scorer"),
    				rs.getString("venue"),
    				round,
    				rs.getInt("season")
    			);
    			matches.add(m);
    		}
    	}catch(SQLException e){
    		System.out.println(e.getMessage());
    	}

    	return coaches;
    }

    public void match_record_view(){
		String query = "SELECT * " +
		"FROM match_record_management";

		try{
			PreparedStatement pstmt = connection.prepareStatement(query);

			ResultSet rs = pstmt.executeQuery();

			while(rs.next()){
				System.out.println(rs.getInt("match_ID"));
				System.out.println(rs.getDate("match_date"));
				System.out.println(rs.getString("home_teamcode"));
				System.out.println(rs.getString("away_teamcode"));
				System.out.println(rs.getInt("away_score"));
				System.out.println(rs.getInt("home_score"));
				System.out.println(rs.getInt("top_scorer"));
				System.out.println(rs.getString("venue"));
				System.out.println(rs.getString("round"));
				System.out.println(rs.getInt("season"));
			}
		}
	}

	public void match_record_insert(int match_ID, Date match_date, String home_teamcode, String away_teamcode, int away_score, int home_score, int top_scorer, String venue, Round round, int season){
		String query = "INSERT INTO match_record_management( match_ID, match_date, home_teamcode, away_teamcode, away_score, home_score, top_scorer, venue, match_round, season) VALUES (?,?,?,?,?,?,?,?,?,?)";

		try{
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, match_ID);
			pstmt.setDate(2, match_date);
			pstmt.setString(3, home_teamcode);
			pstmt.setString(4, away_teamcode);
			pstmt.setInt(5, away_score);
			pstmt.setInt(6, home_score);
			pstmt.setInt(7, top_scorer);
			pstmt.setString(8, venue);
			pstmt.setString(9, round);
			pstmt.setInt(10, season);

			return pstmt.executeUpdate() > 0;
		} catch(SQLException e){
			e.printStackTrace();
		}
	}

	public static boolean match_record_delete(int match_ID){

		String query = "DELETE FROM match_record_management WHERE match_ID = ?";

		try{
			PreparedStatement ostmt = conn.prepareStatement(query);
			pstmt.setInt(1, match_ID);
			return pstmt.executeUpdate() > 0;
		} catch(SQLException e){
			e.printStackTrace();
			return false;
		}
	}

	public boolean match_record_update(int match_ID, Date match_date, String home_teamcode, String away_teamcode, int away_score, int home_score, int top_scorer, String venue, Round round, int season){
		String query = "UPDATE match_record_management SET match_ID = ?, match_date = ?m home_teamcode = ?, away_teamcode = ?, away_score = ?, home_teamcode = ?, top_scorer = ?, venue = ?, round = ?, season = ?";

		try{
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, match_ID);
			pstmt.setDate(2, match_date);
			pstmt.setString(3, home_teamcode);
			pstmt.setString(4, away_teamcode);
			pstmt.setInt(5, away_score);
			pstmt.setInt(6, home_score);
			pstmt.setInt(7, top_scorer);
			pstmt.setString(8, venue);
			pstmt.setString(9, round);
			pstmt.setInt(10, season);

			return pstmt.executeUpdate() > 0;
		} catch(SQLException e){
			e.printStackTrace();
			return false;
		}
	}

	//main

	public static void main(String[] args){
		connectDB();
		List<match> matches = getAllMatches;

		for(match m: matches){
			m.displayMatchInfo();
		}
	}
}
