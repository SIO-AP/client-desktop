DROP TABLE if exists game_question cascade;
DROP TABLE if exists party cascade;
DROP TABLE if exists answer cascade;
DROP TABLE if exists question cascade;
DROP TABLE if exists player cascade;

CREATE TABLE player (
    id_player INTEGER NOT NULL AUTO_INCREMENT,
    name_player VARCHAR(20),
    score_player INTEGER,
    PRIMARY KEY(id_player)
);

CREATE TABLE party (
    id_party INTEGER NOT NULL AUTO_INCREMENT,
    name_party VARCHAR(20),
    id_player INTEGER,
    PRIMARY KEY(id_party),
    foreign key (id_player) references player(id_player)
   );

CREATE TABLE question(
    id_question        INTEGER,
    desc_question    VARCHAR(255),
    difficulte INTEGER,
    PRIMARY KEY (id_question)
);

CREATE TABLE game_question (
    id_party            INTEGER,
    id_question        INTEGER,
    PRIMARY KEY (id_party, id_question),
    FOREIGN KEY (id_party) REFERENCES party(id_party),
    FOREIGN KEY (id_question) REFERENCES question(id_question)
);
CREATE TABLE answer (
    desc_answer        VARCHAR(255),
    is_correct        BOOLEAN,
    id_question        INTEGER,
    FOREIGN KEY (id_question) REFERENCES question(id_question)
);