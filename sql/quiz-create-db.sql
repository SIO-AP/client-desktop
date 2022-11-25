#databases list on instance
show databases;
#quiz_db database creation
CREATE DATABASE quiz_db;
USE quiz_db;
CREATE TABLE quiz_game (
    id_game            INTEGER NOT NULL AUTO_INCREMENT,
    player_name        VARCHAR(255),
    player_score    INTEGER,
    PRIMARY KEY (id_game)
);
CREATE TABLE question(
    id_question        INTEGER,
    desc_question    VARCHAR(255),
    PRIMARY KEY (id_question)
);
CREATE TABLE game_question (
    id_game            INTEGER,
    id_question        INTEGER,
    PRIMARY KEY (id_game, id_question),
    FOREIGN KEY (id_game) REFERENCES quiz_game(id_game),
    FOREIGN KEY (id_question) REFERENCES question(id_question)
);
CREATE TABLE answer (
    desc_answer        VARCHAR(255),
    is_correct        BOOLEAN,
    id_question        INTEGER,
    FOREIGN KEY (id_question) REFERENCES question(id_question)
);
#quiz_dba owner creation
CREATE USER 'quiz_dba'@'localhost' IDENTIFIED BY 'P@ssw0rd';
GRANT ALL PRIVILEGES ON quiz_db.* TO quiz_dba@localhost;
FLUSH PRIVILEGES;
#tables list on quiz_db
show tables;