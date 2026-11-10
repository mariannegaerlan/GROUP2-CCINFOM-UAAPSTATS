package controller;
import model.team;
import model.player;
import model.player.PlayerStatus;
import model.coach;
import model.coach.coach_status;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;


public class controller
{

    // team record management

	public List<team> viewTeams()
	{
		return team.getAllTeams();
	}

	public boolean team_insert(String schoolCode, String team_name, int year, int coach_ID, int championships)
	{
		team newTeam = new team(schoolCode, team_name, year, coach_ID, championships);
		return newTeam.team_record_insert(schoolCode, team_name, year, coach_ID, championships);
	}

	public boolean team_delete(String schoolCode)
	{
		return team.team_record_delete(schoolCode);
	}
	public boolean team_update(String schoolCode, String team_name, int year, int coach_ID, int championships)
	{
		team newTeam = new team(schoolCode, team_name, year, coach_ID, championships);
		return newTeam.team_record_update(team_name, year, coach_ID, championships);
	}

    // method for viewing with filter: top 3 schools with most championships

    // method for viewing with filter: viewing the newest schools that entered the league after 1938 (founding year)

    // player record management
	public List<player> viewPlayers()
	{
		return player.getAllPlayers();
	}

	public boolean player_insert(int playerID, int jersey_num, String schooLCode, String last_name, String first_name,
         int age, int total_points, int total_rebounds, int total_assists, int total_blocks, int total_steals, Time minutes_played, PlayerStatus playerStatus)
	{
		player newPlayer = new player( playerID,  jersey_num,  schooLCode,  last_name,  first_name,
          age,  total_points,  total_rebounds,  total_assists,  total_blocks,  total_steals,  minutes_played,  playerStatus);
		return newPlayer.player_record_insert(playerID,  jersey_num,  schooLCode,  last_name,  first_name,
          age,  total_points,  total_rebounds,  total_assists,  total_blocks,  total_steals,  minutes_played,  playerStatus);
	}

	public boolean player_delete(int playerID)
	{
		return player.player_record_delete(playerID);
	}
	public boolean player_update(int playerID, int jersey_num, String schooLCode, String last_name, String first_name,
         int age, int total_points, int total_rebounds, int total_assists, int total_blocks, int total_steals, Time minutes_played, PlayerStatus playerStatus)
	{
		player newPlayer = new player( playerID, jersey_num,  schooLCode,  last_name,  first_name,
          age,  total_points,  total_rebounds,  total_assists,  total_blocks,  total_steals,  minutes_played,  playerStatus);
		return newPlayer.player_record_update(jersey_num,  schooLCode,  last_name,  first_name,
          age,  total_points,  total_rebounds,  total_assists,  total_blocks,  total_steals,  minutes_played,  playerStatus);
	}
	
	public void accumulate_scores()
	{
		
	}
    // method for viewing with filter: viewing players with the highest number of stats for MVP leaderboard per season
    // method for viewing with filter: viewing injured players

    // match record manageemnt
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
				System.out.println(rs.getString("match_round"));
				System.out.println(rs.getInt("season"));
			}
		}
	}

    // method for viewing with filter: viewing games with a common player that scored the most points
    // method for viewing with filter: viewing games based in MOA

    // coach record management
	public List<coach> viewCoaches()
	{
		return coach.getAllCoaches();
	}

	public boolean coach_insert(int coach_ID, String schoolCode, String last_name, String first_name, LocalDate contract_start, LocalDate contract_end, double monthly_salary, int career_championships, coach_status coachStatus)
	{
		coach newCoach = new coach( coach_ID,  schoolCode,  last_name,  first_name,  contract_start,  contract_end,  monthly_salary,  career_championships, coachStatus);
		return newCoach.coach_record_insert(coach_ID,  schoolCode,  last_name,  first_name,  contract_start,  contract_end,  monthly_salary,  career_championships, coachStatus);
	}

	public boolean coach_delete(int playerID)
	{
		return coach.coach_record_delete(playerID);
	}
	public boolean coach_update(int coach_ID, String schoolCode, String last_name, String first_name, LocalDate contract_start, LocalDate contract_end, double monthly_salary, int career_championships, coach_status coachStatus)
	{
		coach newCoach = new coach( coach_ID,  schoolCode,  last_name,  first_name,  contract_start,  contract_end,  monthly_salary,  career_championships, coachStatus);
		return newCoach.coach_record_update(schoolCode,  last_name,  first_name,  contract_start,  contract_end,  monthly_salary,  career_championships, coachStatus);
	}
	
    // method for viewing with filter: viewing suspended coaches
    // method for viewing with filter: viewing coaches with high monthly salaries
    // method for viewing with filter: viewing coaches whose contracts started in the last 5 years



    // reports management
    // method for team performance report
    	public void team_performance_report(int year){
		//Select statement for school code + team name coloumns
		String query = "SELECT " +
		"t.schoolCode, t.team_name, " +
		"COUNT(DISTINCT m.match_ID AS total_matches, " + //gets the total matches

		//Computes for total wins
		"SUM(CASE " +
		"WHEN (m.home_teamcode = t.schoolCode AND m.home_score > m.away_score) " +
		"OR (m.away_teamcode = t.schoolCode AND m.away_score > m.home_score) " +
		"THEN 1 ELSE 0 END) AS wins, " +

		//computs for total loses
		"SUM(CASE " +
		"WHEN (m.home_teamcode = t.schoolCode AND m.home_score < m.away_score) " +
		"OR (m.away_teamcode = t.schoolCode AND m.away_score < m.home_score) " +
		"THEN 1 ELSE 0 END) AS loses, " +

		//Computes for win rate
		"ROUND(SUM(CASE " +
		"WHEN (m.home_teamcode = t.schoolCode AND m.home_score > m.away_score) " +
		"OR (m.away_teamcode = t.schoolCode AND m.away_score > m.home_score) " +
		"THEN 1 ELSE 0 END) * 100.0 / " +
		"NULLIN(COUNT(DISTINCT m.match_ID), 0), 2) AS win_rate " +

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

    // method for match results report

    // method for coach performance report

    


}
