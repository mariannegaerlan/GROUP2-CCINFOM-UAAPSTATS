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


public class player {

    // Player Attributes
    public enum PlayerStatus{
        Bench, Active, Injured, Suspended
    }
    private int playerID;
    private int jersey_num;
    private String schoolCode;
    private String last_name;
    private String first_name;
    private int age;
    private int total_points;
    private int total_rebounds;
    private int total_assists;
    private int total_blocks;
    private int total_steals;
    private Time minutes_played;
    private PlayerStatus playerStatus;



    //Constructor
    public player(int playerID,int jersey_num, String schoolCode, String last_name, String first_name, int age, int total_points, int total_rebounds, int total_assists, int total_blocks, int total_steals, Time minutes_played, PlayerStatus playerStatus) {
        this.playerID = playerID;
        this.jersey_num = jersey_num;
        this.schoolCode = schoolCode;
        this.first_name = first_name;
        this.last_name = last_name;
        this.age = age;
        this.total_points = total_points;
        this.total_rebounds = total_rebounds;
        this.total_assists = total_assists;
        this.total_blocks = total_blocks;
        this.total_steals = total_steals;
        this.minutes_played = minutes_played;
        this.playerStatus = playerStatus;
    }

    //getters
    public int getPlayerID() {
        return playerID;
    }
    public String getSchoolCode() {
        return schoolCode;
    }

    public String getFirstName() {
        return first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public int getJerseyNum() {
        return jersey_num;
    }

    public int getAge() {
        return age;
    }

    public int getTotalPoints() {
        return total_points;
    }

    public int getTotalRebounds() {
        return total_rebounds;
    }

    public int getTotalAssists() {
        return total_assists;
    }

    public int getTotalBlocks() {
        return total_blocks;
    }

    public int getTotalSteals() {
        return total_steals;
    }

    public Time getMinutesPlayed() {
        return minutes_played;
    }

    public PlayerStatus getPlayerStatus() {
        return playerStatus;
    }

    // setters
    public void setPlayerID(int playerID) {
        if (playerID > 0)
        {
            this.playerID = playerID;
        }
        else
        {
            throw new IllegalArgumentException ("Data can't be negative or zero");
        }
    }
    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }
    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
        
    }

    public void setJerseyNum(int jersey_num) {
        if (jersey_num >= 0)
        {
            this.jersey_num = jersey_num;
        }
        else
        {
            throw new IllegalArgumentException ("Data can't be negative");
        }
    }

    public void setAge(int age) {
        if (age > 0)
        {
            this.age = age;
        }
        else
        {
            throw new IllegalArgumentException ("Data can't be negative or zero");
        }
    }

    public void setTotalPoints(int total_points) {
        if (total_points >= 0)
        {
            this.total_points = total_points;
        }
        else
        {
            throw new IllegalArgumentException ("Data can't be negative");
        }    
    }

    public void setTotalRebounds( int total_rebounds) {
        if (total_rebounds >= 0)
        {
            this.total_rebounds = total_rebounds;
        }
        else
        {
            throw new IllegalArgumentException ("Data can't be negative");
        }    
    }

    public void setTotalAssists(int total_assists) {
        if (total_assists >= 0)
        {
            this.total_assists = total_assists;
        }
        else
        {
            throw new IllegalArgumentException ("Data can't be negative");
        }    
    }

    public void setTotalBlocks(int total_blocks) {
        if (total_blocks >= 0)
        {
            this.total_blocks = total_blocks;
        }
        else
        {
            throw new IllegalArgumentException ("Data can't be negative ");
        }    
    }

    public void setTotalSteals(int total_steals) {
        if (total_steals >= 0)
        {
            this.total_steals = total_steals;
        }
        else
        {
            throw new IllegalArgumentException ("Data can't be negative");
        }    
    }

    public void setMinutesPlayed(Time minutes_played) {
        this.minutes_played = minutes_played;
    }

    public void setPlayerStatus(PlayerStatus playerStatus) {
        this.playerStatus = playerStatus;
    }
    // display method for testing


    public void displayPlayerInfo()
    {
        System.out.println("Player ID:" + this.playerID + "|School Code: " + this.schoolCode + "| Last Name: " + this.last_name + "| First Name: " + this.first_name + "| Jersey Number: " + this.jersey_num + "| Age: " + this.age + "| Total Points: " + this.total_points + "| Total Rebounds: " + this.total_rebounds + "| Total Assists: " + this.total_assists + "| Total Blocks: " + this.total_blocks + "| Total Steals: " + this.total_steals + "| Minutes Played: " + this.minutes_played + "| Player Status: " + this.playerStatus);
    }
    // database connection

    private static final String DB_URL = System.getenv().getOrDefault("DB_URL","jdbc:mysql://localhost:3306/uaapstats");
    private static final String USER = System.getenv().getOrDefault("DB_USER","root");
    private static final String PASSWORD = System.getenv().getOrDefault("DB_PASSWORD","");

    private static Connection conn = null;

    //database methods

   private static boolean connectDB()
   {
    try
    {
        conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        System.out.println("UAAP DB Connected Successfully.");
        return true;
    }
    catch (SQLException e)
    {
        System.out.println(e.getMessage());
        return false;
    }
   }

   public static List<player> getAllPlayers()
   {
    List<player> players = new ArrayList<>();

    try
    {
        if (conn == null || conn.isClosed())
        {
            if (!connectDB())
            {
                return players;
            }
        }
    }
    catch (SQLException e)
    {
        System.out.println(e.getMessage());
        return players;
    }

    StringBuilder query = new StringBuilder("SELECT * FROM player_record_management ");

    try (Statement stmt = conn.createStatement(); 
        ResultSet rs = stmt.executeQuery(query.toString()))
    {
        while(rs.next())
        {
            String statusStr = rs.getString("player_status");
            player.PlayerStatus status = player.PlayerStatus.valueOf(statusStr);

            player p = new player(
                rs.getInt("playerID"),
                rs.getInt("jersey_num"),
                rs.getString("schoolCode"),
                rs.getString("last_name"),
                rs.getString("first_name"),
                rs.getInt("age"),
                rs.getInt("total_points"),
                rs.getInt("total_rebounds"),
                rs.getInt("total_assists"),
                rs.getInt("total_blocks"),
                rs.getInt("total_steals"),
                rs.getTime("minutes_played"),
                status
            );
            players.add(p);
        }
    }
    catch (SQLException e)
    {
        System.out.println(e.getMessage());
    }

    
    return players;

   }
    // CRUD operations

	public void player_record_view(){
		String query = "SELECT * " +
		"FROM player_record_management";

		try{
			PreparedStatement pstmt = connection.prepareStatement(query);

			ResultSet rs = pstmt.executeQuery();

			while(rs.next()){
				System.out.println(rs.getInt("playerID"));
				System.out.println(rs.getInt("jersey_num"));
				System.out.println(rs.getString("schoolCode"));
				System.out.println(rs.getString("last_name"));
				System.out.println(rs.getString("first_name"));
				System.out.println(rs.getInt("age"));
				System.out.println(rs.getInt("total_points"));
				System.out.println(rs.getInt("tota_rebounds"));
				System.out.println(rs.getInt("total_assists"));
				System.out.println(rs.getInt("total_blocks"));
				System.out.println(rs.getInt("total_steals"));
				System.out.println(rs.getString("minutes_played"));
				System.out.println(rs.getString("player_status"));
			} catch(SQLException e){
				e.printStackTrace();
			}
		}
	}

	public boolean player_record_insert(int playerID, int jersey_num, String schooLCode, String last_name, String first_name,
         int age, int total_points, int total_rebounds, int total_assists, int total_blocks, int total_steals, Time minutes_played, PlayerStatus playerStatus)
	{

		String query = "INSERT INTO player_record_management( playerID,  jersey_num,  schooLCode,  last_name,  first_name,  age,  total_points,  total_rebounds,  total_assists,  total_blocks,  total_steals,  minutes_played,  player_status) VALUES (?,?,?,?,?, ?,?,?,?,?, ?,?,?)";

		try
		{
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, playerID);
			pstmt.setInt(2, jersey_num);
			pstmt.setString(3, schooLCode);
			pstmt.setString(4, last_name);
			pstmt.setString(5, first_name);
			pstmt.setInt(6, age);
			pstmt.setInt(7, total_points);
			pstmt.setInt(8, total_rebounds);
			pstmt.setInt(9, total_assists);
			pstmt.setInt(10, total_blocks);
			pstmt.setInt(11, total_steals);
			pstmt.setTime(12, minutes_played);
			pstmt.setString(13, playerStatus.name());
            pstmt.setInt(14, this.playerID);
            return pstmt.executeUpdate() > 0;


		} catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
	}
	public static boolean player_record_delete(int playerID)
	{

		String query = "DELETE FROM player_record_management WHERE playerID = ?";

		try
		{
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, playerID);
            return pstmt.executeUpdate() > 0;


		} catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
	}
	public boolean player_record_update(int jersey_num, String schooLCode, String last_name, String first_name,
         int age, int total_points, int total_rebounds, int total_assists, int total_blocks, int total_steals, Time minutes_played, PlayerStatus playerStatus)
	{
		String query = "UPDATE player_record_management SET jersey_num = ?, schoolCode = ?, last_name = ? , first_name = ?, age = ?, total_points = ?, total_rebounds = ? ,total_assists = ? ,total_blocks = ? ,total_steals = ?, minutes_played = ?, player_status = ? WHERE playerID = ?";

		try
		{
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, jersey_num);
			pstmt.setString(2, schooLCode);
			pstmt.setString(3, last_name);
			pstmt.setString(4, first_name);
			pstmt.setInt(5, age);
			pstmt.setInt(6, total_points);
			pstmt.setInt(7, total_rebounds);
			pstmt.setInt(8, total_assists);
			pstmt.setInt(9, total_blocks);
			pstmt.setInt(10, total_steals);
			pstmt.setTime(11, minutes_played);
			pstmt.setString(12, playerStatus.name());
            return pstmt.executeUpdate() > 0;



		} catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
	}

// view ith filter


   public static List<player> viewTopPlayers()
   {
    List<player> players = new ArrayList<>();

    try
    {
        if (conn == null || conn.isClosed())
        {
            if (!connectDB())
            {
                return players;
            }
        }
    }
    catch (SQLException e)
    {
        System.out.println(e.getMessage());
        return players;
    }

    StringBuilder query = new StringBuilder("SELECT * FROM player_record_management ORDER BY total_points DESC, total_rebounds DESC, total_assists DESC, total_blocks DESC ");

    try (Statement stmt = conn.createStatement(); 
        ResultSet rs = stmt.executeQuery(query.toString()))
    {
        while(rs.next())
        {
            String statusStr = rs.getString("player_status");
            player.PlayerStatus status = player.PlayerStatus.valueOf(statusStr);

            player p = new player(
                rs.getInt("playerID"),
                rs.getInt("jersey_num"),
                rs.getString("schoolCode"),
                rs.getString("last_name"),
                rs.getString("first_name"),
                rs.getInt("age"),
                rs.getInt("total_points"),
                rs.getInt("total_rebounds"),
                rs.getInt("total_assists"),
                rs.getInt("total_blocks"),
                rs.getInt("total_steals"),
                rs.getTime("minutes_played"),
                status
            );
            players.add(p);
        }
    }
    catch (SQLException e)
    {
        System.out.println(e.getMessage());
    }

    
    return players;
    }

   public static List<player> viewInjuredPlayers()
   {
    List<player> players = new ArrayList<>();

    try
    {
        if (conn == null || conn.isClosed())
        {
            if (!connectDB())
            {
                return players;
            }
        }
    }
    catch (SQLException e)
    {
        System.out.println(e.getMessage());
        return players;
    }

    StringBuilder query = new StringBuilder("SELECT * FROM player_record_management WHERE player_status = 'Injured' ");

    try (Statement stmt = conn.createStatement(); 
        ResultSet rs = stmt.executeQuery(query.toString()))
    {
        while(rs.next())
        {
            String statusStr = rs.getString("player_status");
            player.PlayerStatus status = player.PlayerStatus.valueOf(statusStr);

            player p = new player(
                rs.getInt("playerID"),
                rs.getInt("jersey_num"),
                rs.getString("schoolCode"),
                rs.getString("last_name"),
                rs.getString("first_name"),
                rs.getInt("age"),
                rs.getInt("total_points"),
                rs.getInt("total_rebounds"),
                rs.getInt("total_assists"),
                rs.getInt("total_blocks"),
                rs.getInt("total_steals"),
                rs.getTime("minutes_played"),
                status
            );
            players.add(p);
        }
    }
    catch (SQLException e)
    {
        System.out.println(e.getMessage());
    }

    
    return players;
    }

// main
   public static void main(String[] args)
   {
    List<player> players = getAllPlayers();

    for (player p: players)
    {
        p.displayPlayerInfo();
    }
   }


}

