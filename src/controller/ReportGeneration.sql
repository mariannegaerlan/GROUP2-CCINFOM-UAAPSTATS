-- This is the team performance report generation
SELECT COUNT(DISTINCT m.match_ID) AS total_matches, t.schoolCode, t.team_name,
SUM(CASE WHEN (m.home_teamcode = t.schoolCode AND m.home_score > m.away_score) OR (m.away_teamcode = t.schoolCode AND m.away_score > m.home_score) THEN 1 ELSE 0 END) AS wins,

SUM(CASE WHEN (m.home_teamcode = t.schoolCode AND m.home_score < m.away_score) OR (m.away_teamcode = t.schoolCode AND m.away_score < m.home_score) THEN 1 ELSE 0 END) AS losses, 

ROUND(SUM(CASE WHEN (m.home_teamcode = t.schoolCode AND m.home_score > m.away_score) OR (m.away_teamcode = t.schoolCode AND m.away_score > m.home_score) THEN 1 ELSE 0 END) * 100.0 / NULLIF(COUNT(DISTINCT m.match_ID), 0), 2) AS win_rate

FROM team_record_management t

INNER JOIN match_record_management m
ON t.schoolCode = m.home_teamcode 
OR t.schoolCode = m.away_teamcode

WHERE YEAR(m.match_date) = 2026

GROUP BY t.schoolCode, t.team_name;

-- This is for the coach report generation
SELECT COUNT(DISTINCT m.match_ID) AS total_matches, CONCAT(c.first_name, ' ', c.last_name) AS full_name, t.team_name, 
SUM(CASE WHEN (m.home_teamcode = t.schoolCode AND m.home_score > m.away_score) OR (m.away_teamcode = t.schoolCode AND m.away_score > m.home_score) THEN 1 ELSE 0 END) AS wins,

SUM(CASE WHEN (m.home_teamcode = t.schoolCode AND m.home_score < m.away_score) OR (m.away_teamcode = t.schoolCode AND m.away_score < m.home_score) THEN 1 ELSE 0 END) AS losses, 

ROUND(SUM(CASE WHEN (m.home_teamcode = t.schoolCode AND m.home_score > m.away_score) OR (m.away_teamcode = t.schoolCode AND m.away_score > m.home_score) THEN 1 ELSE 0 END) * 100.0 / NULLIF(COUNT(DISTINCT m.match_ID), 0), 2) AS win_rate,

t.team_championships

FROM coach_record_managagement c

INNER JOIN team_record_management t
ON c.coach_ID = t.coach_ID
INNER JOIN match_record_management m
ON t.schoolCode = m.home_teamcode
OR t.schoolCode = m.away_teamcode

WHERE YEAR(m.match_date) = ?

GROUP BY t.schoolCode, t.team_name, c.first_name, c.last_name, t.team_championships;

-- This is for the top player report generation
SELECT m.date, m.home_teamcode, m.away_teamcode, CONCAT(p.last_name, ' ,', p.first_name) AS top_player, p.jersey_num

FROM player_record_management p
INNER JOIN match_record_management m
ON m.top_scorer = p.playerID

WHERE YEAR(m.match_date) = ? 
AND MONTH(m.match_date) = ?

ORDER BY m.match_date DESC;

-- for the match results report
SELECT m.match_date, home.team_name AS home_team, away.team_name AS away_team, m.home_score, m.away_score,

CASE
WHEN m.home_score > m.away_score
THEN home.team_name
WHEN m.away_score > m.home_score
THEN away_team_name
ELSE 'TIE'
END AS winner,

m.venue,
m.round,
COUNT(m.match_ID) OVER (PARTITION BY m.round) AS total_matches

FROM match_record_management m
INNER JOIN team_record_management home
ON m.home_teamcode = home.schoolCode
INNER JOIN team_record_management away
ON m.away_teamcode = away.schoolCode

WHERE YEAR(m.match_date) = ?
AND MONTH(m.match_date) = ?

ORDER BY m.round DESC;


