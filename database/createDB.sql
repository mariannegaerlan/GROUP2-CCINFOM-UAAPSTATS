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

CREATE TABLE coach_record_management(
    coach_ID INT PRIMARY KEY, 
    last_name VARCHAR(35), 
    first_name VARCHAR(35), 
    contract_start DATE,
    contract_end DATE,
    monthly_salary INT, 
    career_championships INT,
    coach_status Enum('Active', 'Suspended', 'Retired')
);

INSERT INTO coach_record_management(coach_ID, last_name, first_name, contract_start, contract_end,
monthly_salary, career_championships, coach_status) VALUES

(1, 'Robinson', 'Topex', '2020-03-10', '9999-12-31', 100000, 4, 'Active' ),
(2, 'Baldwin', 'Thomas', '2020-03-10', '9999-12-31', 100000, 4, 'Suspended' );

