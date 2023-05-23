USE sbcg_db;

DROP TABLE IF EXISTS `game_player` CASCADE;
DROP TABLE IF EXISTS `game_question` CASCADE;
DROP TABLE IF EXISTS `game` CASCADE;
DROP TABLE IF EXISTS `answer` CASCADE;
DROP TABLE IF EXISTS `question` CASCADE;
DROP TABLE IF EXISTS `player` CASCADE;

CREATE TABLE `question` (
  `id_question` int NOT NULL,
  `desc_question` varchar(255) DEFAULT NULL,
  `difficulty` int DEFAULT NULL,
  PRIMARY KEY (`id_question`)
);

CREATE TABLE `answer` (
  `id_answer` int NOT NULL AUTO_INCREMENT,
  `desc_answer` varchar(255) DEFAULT NULL,
  `is_correct` tinyint(1) DEFAULT NULL,
  `id_question` int DEFAULT NULL,
  PRIMARY KEY (`id_answer`),
  KEY `id_question` (`id_question`),
  CONSTRAINT `answer_ibfk_1` FOREIGN KEY (`id_question`) REFERENCES `question` (`id_question`)
);

CREATE TABLE `player` (
  `id_player` int NOT NULL AUTO_INCREMENT,
  `name_player` varchar(30) DEFAULT NULL,
  `password_player` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_player`)
);

CREATE TABLE `game` (
  `id_game` int NOT NULL AUTO_INCREMENT,
  `name_game` varchar(20) DEFAULT NULL,
  `id_leader` int DEFAULT NULL,
  `progress_game` smallint DEFAULT NULL,
  `start_game` time DEFAULT NULL,
  PRIMARY KEY (`id_game`),
  KEY `id_leader` (`id_leader`),
  CONSTRAINT `game_ibfk_1` FOREIGN KEY (`id_leader`) REFERENCES `player` (`id_player`)
);

CREATE TABLE `game_player` (
  `id_game` int NOT NULL,
  `id_player` int NOT NULL,
  `score_player` int DEFAULT NULL,
  PRIMARY KEY (`id_game`,`id_player`),
  KEY `id_player` (`id_player`),
  CONSTRAINT `game_player_ibfk_1` FOREIGN KEY (`id_game`) REFERENCES `game` (`id_game`),
  CONSTRAINT `game_player_ibfk_2` FOREIGN KEY (`id_player`) REFERENCES `player` (`id_player`)
);

CREATE TABLE `game_question` (
  `id_game` int NOT NULL,
  `id_question` int NOT NULL,
  PRIMARY KEY (`id_game`,`id_question`),
  KEY `id_question` (`id_question`),
  CONSTRAINT `game_question_ibfk_1` FOREIGN KEY (`id_game`) REFERENCES `game` (`id_game`),
  CONSTRAINT `game_question_ibfk_2` FOREIGN KEY (`id_question`) REFERENCES `question` (`id_question`)
);


CREATE USER 'sbcg_dba'@'localhost' IDENTIFIED BY 'P@ssw0rdSIO';
GRANT ALL PRIVILEGES ON sbcg_db.* TO 'sbcg_dba'@'localhost';
GRANT ALL PRIVILEGES ON mysql.proc TO 'sbcg_dba'@'localhost';

FLUSH PRIVILEGES;
#tables list ON vth_db
show tables;

