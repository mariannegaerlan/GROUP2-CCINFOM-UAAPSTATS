package model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class team {

	//Team attributes

	private String schoolCode;
	private String team_name;
	private int year;
	private int coach_ID;
	private int team_championships;

	//Constructor

	public team(String schoolCode, String team_name, int year, int coach_ID, int team_championships){
		this.schoolCode = schoolCode;
		this.team_name = team_name;
		this.year = year;
		this.coach_ID = coach_ID;
		this.team_championships = team_championships;
	}

	//Getters

	public String getSchoolCode(){
		return this.schoolCode;
	}

	public String getTeamName(){
		return this.team_name;
	}

	public int getYear(){
		return this.year;
	}

	public int getCoachID(){
		return this.coach_ID;
	}

	public int getTeamChampionships(){
		return this.team_championships;
	}

	//setters

	public void setSchoolCode(String schoolCode){
		this.schoolCode = schoolCode;
	}

	public void setTeamName(String team_name){
		this.team_name = team_name;
	}

	public void setYear(int year) throws IllegalAccessException{
		if(year > 0){
			this.year = year;
		}

		else{
			throw new IllegalAccessException("Data can't be negative or zero");
		}
	}

	public void setCoachID(int coach_ID) throws IllegalAccessException{
		if(coach_ID > 0){
			this.coach_ID = coach_ID;
		}

		else{
			throw new IllegalAccessException("Data can't be negative or zero");
		}
	}

	public void setTeamChampionships(int team_championships) throws IllegalAccessException{
		if(year >= 0){
			this.team_championships = team_championships;
		}

		else{
			throw new IllegalAccessException("Data can't be negative");
		}
	}

	public void displayTeamInfo(){
		System.out.println("School Code: " + this.schoolCode + "\nTeam Name: " + this.team_name + "\nYear joined: " + this.year + "\nCoach ID: " + this.coach_ID
		+ "\nNumber of championships: " + this.team_championships);
	}

	//DB connection

	private static final String DB_URL = System.getenv().getOrDefault("DB_URL","jdbc:mysql://localhost:3306/uaapstats");
	private static final String USER = System.getenv().getOrDefault("DB_USER","root");
    private static final String PASSWORD = System.getenv().getOrDefault("DB_PASSWORD","");

    private static Connection conn = null;

    //database methods

    private static boolean connectDB(){
    	try {
    		conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
    		System.out.println("UAAP DB Connected Successfully.");
    		return true;
    	}

    	catch (SQLException e){
    		System.out.println(e.getMessage());
    		return false;
    	}
    }

    // testing for connection
    public static List<team> getAllTeams(){
    	List<team> teams = new ArrayList<>();

    	try{
    		if(conn == null || conn.isClosed()){
    			if(!connectDB()){
    				return teams;
    			}
    		}
    	}
    	catch (SQLException e){
    		System.out.println(e.getMessage());
    		return teams;
    	}

    	StringBuilder query = new StringBuilder("SELECT * FROM team_record_management ");

    	try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query.toString())){
    		while(rs.next()){
    			team t = new team(
    				rs.getString("schoolCode"),
    				rs.getString("team_name"),
    				rs.getInt("year"),
    				rs.getInt("coach_ID"),
    				rs.getInt("team_championships"));
    			teams.add(t);
    		}
    	}
    	catch (SQLException e){
    		System.out.println(e.getMessage());
    	}

    	return teams;
    }


    // CRUD operations

	public void team_record_view(){
		//Select query
		String query = "SELECT * " +
		"FROM team_record_management";

		try{
			PreparedStatement pstmt = conn.prepareStatement(query);

			ResultSet rs = pstmt.executeQuery();

			while(rs.next()){
				System.out.println(rs.getString("schoolCode"));
				System.out.println(rs.getString("team_name"));
				System.out.println(rs.getInt("year"));
				System.out.println(rs.getInt("coach_ID"));
				System.out.println(rs.getInt("team_championships"));
			}

			rs.close();
			pstmt.close();
		} catch(SQLException e){
			e.printStackTrace();
		}
	}

	public boolean team_record_insert(String schoolCode, String team_name, int year, int coach_ID, int team_championships)
	{

		String query = "INSERT INTO team_record_management(schoolCode, team_name, year, coach_ID, team_championships) VALUES (?,?,?,?,?)";

		try
		{
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, schoolCode);
			pstmt.setString(2, team_name);
			pstmt.setInt(3, year);
			pstmt.setInt(4, coach_ID);
			pstmt.setInt(5, team_championships);
            System.err.println("Data inserted:");
            System.out.println("SchoolCode: " + schoolCode + "| team_name: " + team_name + "| year: " + year + "| coach_ID: " + coach_ID + "| team_championships: " + team_championships);

            return pstmt.executeUpdate() > 0;


		} catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
	}
	public static boolean team_record_delete(String schoolCode)
	{

		String query = "DELETE FROM team_record_management WHERE schoolCode = ?";

		try
		{
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, schoolCode);
            System.out.println("Team deleted successfully: " + schoolCode);
            return pstmt.executeUpdate() > 0;


		} catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
	}
	public boolean team_record_update(String team_name, int year, int coach_ID, int team_championships)
	{
		String query = "UPDATE team_record_management SET team_name = ?, year = ?, coach_ID = ? ,team_championships = ? WHERE schoolCode = ?";

		try
		{
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, team_name);
			pstmt.setInt(2, year);
			pstmt.setInt(3, coach_ID);
			pstmt.setInt(4, team_championships);
            pstmt.setString(5, this.schoolCode);
            System.err.println("Data updated:");
            System.out.println( "team_name: " + team_name + "| year: " + year + "| coach_ID: " + coach_ID + "| team_championships: " + team_championships);

            return pstmt.executeUpdate() > 0;


		} catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
	}

	public static List<team> viewTop3Championships()
	{
    	List<team> teams = new ArrayList<>();

    	try{
    		if(conn == null || conn.isClosed()){
    			if(!connectDB()){
    				return teams;
    			}
    		}
    	}
    	catch (SQLException e){
    		System.out.println(e.getMessage());
    		return teams;
    	}

    	StringBuilder query = new StringBuilder("SELECT * FROM team_record_management ORDER BY team_championships DESC LIMIT 3");

    	try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query.toString())){
    		while(rs.next()){
    			team t = new team(
    				rs.getString("schoolCode"),
    				rs.getString("team_name"),
    				rs.getInt("year"),
    				rs.getInt("coach_ID"),
    				rs.getInt("team_championships"));
    			teams.add(t);
    		}
    	}
    	catch (SQLException e){
    		System.out.println(e.getMessage());
    	}

    	return teams;

	}
	public static List<team> viewNonFounders()
	{
    	List<team> teams = new ArrayList<>();

    	try{
    		if(conn == null || conn.isClosed()){
    			if(!connectDB()){
    				return teams;
    			}
    		}
    	}
    	catch (SQLException e){
    		System.out.println(e.getMessage());
    		return teams;
    	}

    	StringBuilder query = new StringBuilder("SELECT * FROM team_record_management WHERE year > 1938");

    	try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query.toString())){
    		while(rs.next()){
    			team t = new team(
    				rs.getString("schoolCode"),
    				rs.getString("team_name"),
    				rs.getInt("year"),
    				rs.getInt("coach_ID"),
    				rs.getInt("team_championships"));
    			teams.add(t);
    		}
    	}
    	catch (SQLException e){
    		System.out.println(e.getMessage());
    	}

    	return teams;

	}


    //main

    public static void main(String[] args){
    	List<team> teams = getAllTeams();

    	for(team t : teams){
    		t.displayTeamInfo();
    	} 
    }
}