public class controller
{
    // method for retrieving commands from view and sending them to model

    // method for sending data back to the view

    // method for accumulating total points from match entry  and updating the player record

    // viewing with filter 
    // team record management
    // method for viewing with filter: top 3 schools with most championships

    // method for viewing with filter: viewing the newest schools that entered the league after 1938 (founding year)

    // player record management
    // method for viewing with filter: viewing players with the highest number of stats for MVP leaderboard per season
    // method for viewing with filter: viewing injured players

    // match record manageemnt

    // method for viewing with filter: viewing games with a common player that scored the most points
    // method for viewing with filter: viewing games based in MOA

    // coach record management
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
		"FROM team t " +
		"INNER JOIN `match` m " +
		"ON t.schoolCode = m.home_teamcode " +
		"OR t.schoolCode = m.away_teamcode " +

		//Filtering based on year
		"WHERE YEAR(m.date) = ? " +

		//For the aggregate functions
		"GROUP BY t.schoolCode, t.team_name";

		try{
			PreparedStatement pstmt = connection.prepareStatement(query);

			pstmt.setInt(1, year);

			ResultSet rs = pstmt.executeQuery();

			while(rs.next()){
				System.out.println(rs.getString("schoolCode"));
				System.out.println(rs.getString("team_name"));
				System.out.println(rs.getString("total_matches"));
				System.out.println(rs.getString("wins"));
				System.out.println(rs.getString("losses"));
				System.out.println(rs.getString("win_rate"));
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
