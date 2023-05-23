USE `sbcg_db`;
DROP procedure IF EXISTS `get_game`;

DELIMITER $$
USE `sbcg_db`$$
CREATE PROCEDURE `get_game`(IN numQuestions INT)
BEGIN
-- Crée une vue temporaire qui contient les questions et leurs réponses
CREATE TEMPORARY TABLE tempQuestionsWithAnswers
SELECT q.*,
a1.id_answer AS id_answer1, a1.desc_answer AS desc_answer1, a1.is_correct AS is_correct1,
a2.id_answer AS id_answer2, a2.desc_answer AS desc_answer2, a2.is_correct AS is_correct2,
a3.id_answer AS id_answer3, a3.desc_answer AS desc_answer3, a3.is_correct AS is_correct3,
a4.id_answer AS id_answer4, a4.desc_answer AS desc_answer4, a4.is_correct AS is_correct4
FROM question q
-- Jointure gauche pour récupérer les réponses de la question
LEFT JOIN answer a1 ON q.id_question = a1.id_question
-- Jointure gauche pour récupérer la deuxième réponse de la question
LEFT JOIN answer a2 ON q.id_question = a2.id_question and a2.id_answer != a1.id_answer
-- Jointure gauche pour récupérer la troisième réponse de la question
LEFT JOIN answer a3 ON q.id_question = a3.id_question and a3.id_answer != a1.id_answer and a3.id_answer != a2.id_answer
-- Jointure gauche pour récupérer la quatrième réponse de la question
LEFT JOIN answer a4 ON q.id_question = a4.id_question and a4.id_answer != a1.id_answer and a4.id_answer != a2.id_answer and a4.id_answer != a3.id_answer
-- Jointure interne avec une table dérivée pour sélectionner les IDs de questions aléatoires
JOIN (
  SELECT id_question FROM question ORDER BY RAND() LIMIT numQuestions
) AS randomQuestions ON q.id_question = randomQuestions.id_question
GROUP BY q.id_question;

-- Sélectionne les données de la vue temporaire
SELECT * FROM tempQuestionsWithAnswers ORDER BY RAND();

-- Supprime la vue temporaire
DROP TEMPORARY TABLE tempQuestionsWithAnswers;
END$$

DELIMITER ;


USE `sbcg_db`;
DROP procedure IF EXISTS `get_Player_Stats`;

DELIMITER $$
USE `sbcg_db`$$
CREATE PROCEDURE `get_Player_Stats`(IN playerID INT)
BEGIN   
SELECT COUNT(*) AS numGamesPlayed, AVG(score_player) AS avgScorePerGame
FROM player p
inner join game_player gp on gp.id_player = p.id_player
WHERE p.id_player = playerID;
END$$

DELIMITER ;

