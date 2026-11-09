public class controller
{
    // method for retrieving commands from view and sending them to model

    // method for sending data back to the view

    // method for accumulating total points from match entry  and updating the player record

    // viewing with filter 
    // team record management
	public void team_record_view(){
		//Select query
		String query = "SELECT * " +
		"FROM team_record_management";

		try{
			PreparedStatement pstmt = connection.prepareStatement(query);

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
    // method for viewing with filter: top 3 schools with most championships

    // method for viewing with filter: viewing the newest schools that entered the league after 1938 (founding year)

    // player record management
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
				System.out.println(rs.getString("minutes_player"));
				System.out.println(rs.getString("playerStatus"));
			} catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
    // method for viewing with filter: viewing players with the highest number of stats for MVP leaderboard per season
    // method for viewing with filter: viewing injured players

    // match record manageemnt

    // method for viewing with filter: viewing games with a common player that scored the most points
    // method for viewing with filter: viewing games based in MOA

    // coach record management
	public void coach_record_view(){
		String query = "SELECT * " +
		"FROM coach_record_management";

		try{
			PreparedStatement pstmt = connection.prepareStatement(query);

			ResultSet rs = pstmt.executeQuery();

			while(rs.next()){
				System.out.println(rs.getString("schoolCode"));
				System.out.println(rs.getString("last_name"));
				System.out.println(rs.getString("first_name"));
				System.out.println(rs.getString("contract_start"));
				System.out.println(rs.getString("contract_end"));
				System.out.println(rs.getDouble("monthly_salary"));
				System.out.println(rs.getInt("career_championships"));
				System.out.println(rs.getString("coach_status"));
			} catch(SQLException e){
				e.printStackTrace
			}
		}
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
