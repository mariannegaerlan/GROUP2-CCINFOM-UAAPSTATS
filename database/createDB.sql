CREATE DATABASE uaapstats;

CREATE TABLE team_record_management(
    schoolCode VARCHAR(4) PRIMARY KEY,
    team_name VARCHAR(35),
    year INT(6),
    coach_ID INT,
    team_championships INT
);

INSERT INTO team_record_management(schoolCode, team_name, year, coach_ID, team_championships)
VALUES
    ('ADU', 'SOARING FALCONS', 1954, 1011, 1),
    ('ADMU', 'Blue Eagles', 1974, 1012, 12),
    ('DLSU', 'Green Archers', 1986, 1013, 11),
    ('FEU', 'Tamaraws', 1938, 1014, 20),
    ('NU', 'Bulldogs', 1938, 1015, 2),
    ('UE', 'Red Warriors', 1954, 1016, 18),
    ('UPD', 'Fighting Maroons', 1938, 1017, 4),
    ('UST', 'Growling Tigers', 1938, 1018, 18);
