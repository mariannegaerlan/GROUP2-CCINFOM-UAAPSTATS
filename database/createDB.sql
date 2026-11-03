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

(1011, 'Racela', 'Nash', '2021-12-01', '9999-12-31', 50000, 0, 'Active' ),-- AU
(1012, 'Alas', 'Louie', '2026-07-10', '9999-12-31', 70000, 0, 'Active' ),-- ADMU
(1013, 'Robinson', 'Topex', '2023-01-19', '9999-12-31', 100000, 2, 'Active' ),-- DLSU
(1014, 'Chambers', 'Sean', '2024-03-18', '9999-12-31', 60000, 0, 'Active' ),-- FEU
(1015, 'Napa', 'Jeff', '2020-10-09', '9999-12-31', 80000, 0, 'Active' ),-- NU
(1016, 'Hubalde', 'Paulo', '2026-09-29', '9999-12-31', 40000, 0, 'Active' ),-- UE
(1017, 'Monteverde', 'Goldwin', '2021-08-13', '9999-12-31', 70000, 2, 'Active' ),-- UPD
(1018, 'Jarencio', 'Pido', '2006-02-03', '9999-12-31', 90000, 1, 'Active' ),-- UST

(0000, 'Baldwin', 'Thomas', '2020-03-10', '9999-12-31', 90000, 2, 'Suspended' ), -- ADMU
(0001, 'Ayo', 'Aldin', '2020-03-10', '9999-12-31', 70000, 1, 'Suspended' ), -- UST
(0002, 'Black', 'Norman', '2008-05-14', '2012-12-31', 150000, 5, 'Retired' ), -- ADMU
(0003, 'Pumaren', 'Franz', '1998-02-21', '2001-12-31', 100000, 3, 'Retired' ); -- DLSU
    
