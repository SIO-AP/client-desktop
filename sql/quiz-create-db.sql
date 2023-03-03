DROP TABLE if exists game_question cascade;
DROP TABLE if exists game_player cascade;
DROP TABLE if exists game cascade;
DROP TABLE if exists answer cascade;
DROP TABLE if exists question cascade;
DROP TABLE if exists player cascade;

CREATE TABLE player (
    id_player INTEGER NOT NULL AUTO_INCREMENT,
    name_player VARCHAR(30),
    password_player VARCHAR(255),
    PRIMARY KEY(id_player)
);

CREATE TABLE game (
    id_game INTEGER NOT NULL AUTO_INCREMENT,
    name_game VARCHAR(20),
    PRIMARY KEY(id_game)
   );

CREATE TABLE question(
    id_question INTEGER,
    desc_question VARCHAR(255),
    difficulty INTEGER,
    PRIMARY KEY (id_question)
);

CREATE TABLE answer (
    desc_answer VARCHAR(255),
    is_correct BOOLEAN,
    id_question INTEGER,
    FOREIGN KEY (id_question) REFERENCES question(id_question)
);

CREATE TABLE game_question (
    id_game INTEGER,
    id_question INTEGER,
    PRIMARY KEY (id_game, id_question),
    FOREIGN KEY (id_game) REFERENCES game(id_game),
    FOREIGN KEY (id_question) REFERENCES question(id_question)
);

CREATE TABLE game_player (
    id_game INTEGER,
    id_player INTEGER,
    score_player  INTEGER,
    PRIMARY KEY (id_game, id_player),
    FOREIGN KEY (id_game) REFERENCES game(id_game),
    FOREIGN KEY (id_player) REFERENCES player(id_player)
);