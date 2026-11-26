import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Reports {

    private Connection connection;

    // Database connection
    public Reports() {
        try {
            String url = "jdbc:mysql://localhost:3306/your_database";
            String user = "root";
            String password = "";

            connection = DriverManager.getConnection(url, user, password);

            System.out.println("Database connected successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // reports management
    // method for team performance report
        public void team_performance_report(int year){
        //Select statement for school code + team name coloumns
        String query = "SELECT " +
        "t.schoolCode, t.team_name, " +
        "COUNT(DISTINCT m.match_ID) AS total_matches, " + //gets the total matches

        //Computes for total wins
        "SUM(CASE " +
        "WHEN (m.home_teamcode = t.schoolCode AND m.home_score > m.away_score) " +
        "OR (m.away_teamcode = t.schoolCode AND m.away_score > m.home_score) " +
        "THEN 1 ELSE 0 END) AS wins, " +

        //computs for total loses
        "SUM(CASE " +
        "WHEN (m.home_teamcode = t.schoolCode AND m.home_score < m.away_score) " +
        "OR (m.away_teamcode = t.schoolCode AND m.away_score < m.home_score) " +
        "THEN 1 ELSE 0 END) AS losses, " +

        //Computes for win rate
        "ROUND(SUM(CASE " +
        "WHEN (m.home_teamcode = t.schoolCode AND m.home_score > m.away_score) " +
        "OR (m.away_teamcode = t.schoolCode AND m.away_score > m.home_score) " +
        "THEN 1 ELSE 0 END) * 100.0 / " +
        "NULLIF(COUNT(DISTINCT m.match_ID), 0), 2) AS win_rate " +

        //joining the tables needed
        "FROM team_record_management t " +
        "INNER JOIN match_record_management m " +
        "ON t.schoolCode = m.home_teamcode " +
        "OR t.schoolCode = m.away_teamcode " +

        //Filtering based on year
        "WHERE YEAR(m.match_date) = ? " +

        //For the aggregate functions
        "GROUP BY t.schoolCode, t.team_name";

        try{
            PreparedStatement pstmt = connection.prepareStatement(query);

            pstmt.setInt(1, year);

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                System.out.println(rs.getString("schoolCode"));
                System.out.println(rs.getString("team_name"));
                System.out.println(rs.getInt("total_matches"));
                System.out.println(rs.getInt("wins"));
                System.out.println(rs.getInt("losses"));
                System.out.println(rs.getDouble("win_rate"));
            }

            rs.close();
            pstmt.close();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }


    // method for top scorer per round report
    public void top_scorer_report(int year, int month){
        //Select statement for match date, home and away teamcodes, and the top player name with their jersey number

        String query = "SELECT " +
        "CONCAT(p.last_name, ', ', p.first_name) AS top_player, p.jersey_num, " +
        "m.match_date, m.home_teamcode, m.away_teamcode " +

        //From with inner join clause

        "FROM player_record_management p " +
        "INNER JOIN match_record_management m " +
        "ON m.top_scorer = p.playerID " +

        //Where clause

        "WHERE YEAR(m.match_date) = ? " +
        "AND MONTH(m.match_date) = ? " +

        //Ordering by dates in descending order

        "ORDER BY m.match_date DESC";

        try{
            PreparedStatement pstmt = connection.prepareStatement(query);

            pstmt.setInt(1, year);
            pstmt.setInt(2, month);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getString("top_player"));
                System.out.println(rs.getInt("jersey_num"));
                System.out.println(rs.getDate("match_date"));
                System.out.println(rs.getString("home_teamcode"));
                System.out.println(rs.getString("away_teamcode"));
            }

            rs.close();
            pstmt.close();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
    // method for match results report

    public void match_report(int year, int month){
        //Select statement for date, teams, and their scores
        String query = "SELECT " +
        "m.match_date, home.team_name AS home_team, away.team_name AS away_team, m.home_score, m.away_score, " +

        //contiuation with the winners
        "CASE " +
        "WHEN m.home_score > m.away_score " +
        "THEN home.team_name " +
        "WHEN m.away_score > m.home_score " +
        "THEN away.team_name " +
        "ELSE 'TIE' " +
        "END AS winner, " +

        //Contiunation with venue and round and total matches

        "m.venue, m.round, " +
        "COUNT(m.match_ID) OVER (PARTITION BY m.round) AS total_matches " +

        //Joining the tables needed
        "FROM match_record_management m " +
        "INNER JOIN team_record_management home " +
        "ON m.home_teamcode = home.schoolCode " +
        "INNER JOIN team_record_management away " +
        "ON m.away_teamcode = away.schoolCode " +

        //Where clause

        "WHERE YEAR(m.match_date) = ? " +
        "AND MONTH(m.match_date) = ? " +

        //Order by and grouping

        "ORDER BY m.round DESC";

        try{
            PreparedStatement pstmt = connection.prepareStatement(query);

            pstmt.setInt(1, year);
            pstmt.setInt(2, month);

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                System.out.println(rs.getDate("match_date"));
                System.out.println(rs.getString("home_team"));
                System.out.println(rs.getString("away_team"));
                System.out.println(rs.getInt("home_score"));
                System.out.println(rs.getInt("away_score"));
                System.out.println(rs.getString("winner"));
                System.out.println(rs.getString("venue"));
                System.out.println(rs.getString("round"));
                System.out.println(rs.getInt("total_matches"));
            }

            rs.close();
            pstmt.close();
        } catch(SQLException e){
            e.printStackTrace();
        }
    } 

    // method for coach performance report

    public void coach_report(int year){
        //Select statement for name of coach, their team's name, and championships
        String query = "SELECT " +
        "CONCAT(c.first_name, ' ', c.last_name) AS full_name, t.team_name, t.team_championships, " +

        //Getting the total number of matches
        "COUNT(DISTINCT m.match_ID) AS total_matches, " +

        //Computes for total wins
        "SUM(CASE " +
        "WHEN (m.home_teamcode = t.schoolCode AND m.home_score > m.away_score) " +
        "OR (m.away_teamcode = t.schoolCode AND m.away_score > m.home_score) " +
        "THEN 1 ELSE 0 END) AS wins, " +

        //computs for total loses
        "SUM(CASE " +
        "WHEN (m.home_teamcode = t.schoolCode AND m.home_score < m.away_score) " +
        "OR (m.away_teamcode = t.schoolCode AND m.away_score < m.home_score) " +
        "THEN 1 ELSE 0 END) AS losses, " +

        //Computes for win rate
        "ROUND(SUM(CASE " +
        "WHEN (m.home_teamcode = t.schoolCode AND m.home_score > m.away_score) " +
        "OR (m.away_teamcode = t.schoolCode AND m.away_score > m.home_score) " +
        "THEN 1 ELSE 0 END) * 100.0 / " +
        "NULLIF(COUNT(DISTINCT m.match_ID), 0), 2) AS win_rate " +

        //Joining the needed tables

        "FROM coach_record_management c " +
        "INNER JOIN team_record_management t " +
        "ON c.coach_ID = t.coach_ID " +
        "INNER JOIN match_record_management m " +
        "ON t.schoolCode = m.home_teamcode " +
        "OR t.schoolCode = m.away_teamcode " +

        //Where condition

        "WHERE YEAR(m.match_date) = ? " +

        //For the aggregate functions
        "GROUP BY t.schoolCode, t.team_name, c.first_name, c.last_name, t.team_championships";

        try{
            PreparedStatement pstmt = connection.prepareStatement(query);

            pstmt.setInt(1, year);

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                System.out.println(rs.getString("full_name"));
                System.out.println(rs.getInt("team_championships"));
                System.out.println(rs.getString("team_name"));
                System.out.println(rs.getInt("total_matches"));
                System.out.println(rs.getInt("wins"));
                System.out.println(rs.getInt("losses"));
                System.out.println(rs.getDouble("win_rate"));
            }

            rs.close();
            pstmt.close();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}