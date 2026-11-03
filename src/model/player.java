package model;
import java.sql.Connection;
import java.sql.DriverManager;
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
    private static final String PASSWORD = System.getenv().getOrDefault("DB_PASSWORD","tomemyxmen1997");

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

