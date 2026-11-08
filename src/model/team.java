package model;
import java.sql.Connection;
import java.sql.DriverManager;
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

	public void setYear(int year){
		if(year > 0){
			this.year = year;
		}

		else{
			throw new IllegalAccessException("Data can't be negative or zero");
		}
	}

	public void setCoachID(int coach_ID){
		if(year > 0){
			this.coach_ID = coach_ID;
		}

		else{
			throw new IllegalAccessException("Data can't be negative or zero");
		}
	}

	public void setTeamChampionships(int team_championships){
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

	private static final String DB_URL = System.getnev().getOrDefault("DB_URL","jdbc:mysql://localhost:3306/uaapstats");
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
    		return players;
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

    //main

    public static void main(String[] args){
    	List<team> teams = getAllTeams();

    	for(team t : teams){
    		p.displayTeamInfo();
    	} 
    }
}
