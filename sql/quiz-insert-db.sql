#databases list on instance
show databases;
USE quiz_db;
#insert stub data

INSERT INTO question VALUES(0, "Capital city of Costa Rica ?");
INSERT INTO answer VALUES("San Juan", false, 0);
INSERT INTO answer VALUES("Asuncion", false, 0);
INSERT INTO answer VALUES("San Jose", true, 0);
INSERT INTO answer VALUES("Santiago", false, 0);

INSERT INTO question VALUES(1, "Capital city of Philippines ?");
INSERT INTO answer VALUES("Seoul", false, 1);
INSERT INTO answer VALUES("Manila", true, 1);
INSERT INTO answer VALUES("Hanoi", false, 1);
INSERT INTO answer VALUES("Taipei", false, 1);

INSERT INTO question VALUES(2, "Capital city of Maldives ?");
INSERT INTO answer VALUES("Male", true, 2);
INSERT INTO answer VALUES("Maseru", false, 2);
INSERT INTO answer VALUES("Port Louis", false, 2);
INSERT INTO answer VALUES("Chisinau", false, 2);

INSERT INTO question VALUES(3, "Capital city of Romania ?");
INSERT INTO answer VALUES("Muscat", false, 3);
INSERT INTO answer VALUES("Munich", false, 3);
INSERT INTO answer VALUES("Bucharest", true, 3);
INSERT INTO answer VALUES("Belgrade", false, 3);

INSERT INTO question VALUES(4, "Capital city of Switzerland ?");
INSERT INTO answer VALUES("Brussels", false, 4);
INSERT INTO answer VALUES("Bern", true, 4);
INSERT INTO answer VALUES("San Marino", false, 4);
INSERT INTO answer VALUES("Tallin", true, 4);

INSERT INTO question VALUES(5, "What has to be broken before you can use it ?");
INSERT INTO answer VALUES("A sponge", false, 5);
INSERT INTO answer VALUES("A stone", false, 5);
INSERT INTO answer VALUES("A candle", false, 5);
INSERT INTO answer VALUES("An egg", true, 5);

INSERT INTO question VALUES(6, "What was the first country to approve women's suffrage ?");
INSERT INTO answer VALUES("Germany", false, 6);
INSERT INTO answer VALUES("Uk", false, 6);
INSERT INTO answer VALUES("New Zealand", true, 6);
INSERT INTO answer VALUES("Italy", false, 6);

INSERT INTO question VALUES(7, "In what year did man reach the moon ?");
INSERT INTO answer VALUES("1959", false, 7);
INSERT INTO answer VALUES("1969", true, 7);
INSERT INTO answer VALUES("1967", false, 7);
INSERT INTO answer VALUES("1981", false, 7);

INSERT INTO question VALUES(8, "What was Marco Polo's home city ?");
INSERT INTO answer VALUES("Venice", true, 8);
INSERT INTO answer VALUES("Milan", false, 8);
INSERT INTO answer VALUES("Marseille", false, 8);
INSERT INTO answer VALUES("Rome", false, 8);

INSERT INTO question VALUES(9, "In what year did the Rwandan genocide take place ?");
INSERT INTO answer VALUES("2000", false, 9);
INSERT INTO answer VALUES("1985", false, 9);
INSERT INTO answer VALUES("1994", true, 9);
INSERT INTO answer VALUES("1952", false, 9);

INSERT INTO question VALUES(10, "In what year was the Soviet Union dissolved ?");
INSERT INTO answer VALUES("1991", true, 10);
INSERT INTO answer VALUES("2015", false, 10);
INSERT INTO answer VALUES("1960", false, 10);
INSERT INTO answer VALUES("1500 BC ;)", false, 10);

INSERT INTO question VALUES(11, "In what year was the Soviet Union dissolved ?");
INSERT INTO answer VALUES("1991", true, 11);
INSERT INTO answer VALUES("2015", false, 11);
INSERT INTO answer VALUES("1960", false, 11);
INSERT INTO answer VALUES("1500 BC ;)", false, 11);

INSERT INTO question VALUES(12, "To which country does the island of Crete belong ?");
INSERT INTO answer VALUES("Canary Islands", false, 12);
INSERT INTO answer VALUES("Malta", false, 12);
INSERT INTO answer VALUES("Greece", true, 12);
INSERT INTO answer VALUES("Cyprus", false, 12);

INSERT INTO question VALUES(13, "In which country in Europe is Magyar spoken ?");
INSERT INTO answer VALUES("Poland", false, 13);
INSERT INTO answer VALUES("Hungary", true, 13);
INSERT INTO answer VALUES("Peru", false, 13);
INSERT INTO answer VALUES("Luxembourg", false, 13);

INSERT INTO question VALUES(14, "What is the most visited country in the world ?");
INSERT INTO answer VALUES("Brazil", false, 14);
INSERT INTO answer VALUES("Japan", false, 14);
INSERT INTO answer VALUES("China", false, 14);
INSERT INTO answer VALUES("France", true, 14);

INSERT INTO question VALUES(15, "Between which countries is the Bering Strait ?");
INSERT INTO answer VALUES("USA and Russia", true, 15);
INSERT INTO answer VALUES("Morocco and Spain", false, 15);
INSERT INTO answer VALUES("Japan and China", false, 15);
INSERT INTO answer VALUES("UK and France", false, 15);

INSERT INTO question VALUES(16, "Who painted the 'Guernica' ?");
INSERT INTO answer VALUES("Wolfgang Amadeus Mozart", false, 16);
INSERT INTO answer VALUES("Van Gogh", false, 16);
INSERT INTO answer VALUES("Pablo Picasso", true, 16);
INSERT INTO answer VALUES("Leonardo Da Vinci", false, 16);

INSERT INTO question VALUES(17, "What is the name of Don Quixote de la Mancha's horse ?");
INSERT INTO answer VALUES("Jolly Jumper", false, 17);
INSERT INTO answer VALUES("Rocinante", true, 17);
INSERT INTO answer VALUES("Margarita", false, 17);
INSERT INTO answer VALUES("John Doe", false, 17);

INSERT INTO question VALUES(18, "What gas in the atmosphere protects us from ultraviolet radiation ?");
INSERT INTO answer VALUES("Butane gas", false, 18);
INSERT INTO answer VALUES("Oxygen", false, 18);
INSERT INTO answer VALUES("Hydrogen", false, 18);
INSERT INTO answer VALUES("Ozone", true, 18);

INSERT INTO question VALUES(19, "From which plant is tequila made ?");
INSERT INTO answer VALUES("Sugar Cane", false, 19);
INSERT INTO answer VALUES("Agave", true, 19);
INSERT INTO answer VALUES("Rosemary", false, 19);
INSERT INTO answer VALUES("Palm tree", false, 19);

INSERT INTO question VALUES(20, "What fictional street did Sherlock Holmes live on ?");
INSERT INTO answer VALUES("Baker Street", true, 20);
INSERT INTO answer VALUES("Carnaby Street", false, 20);
INSERT INTO answer VALUES("Barking Road", false, 20);
INSERT INTO answer VALUES("Piccadilly Street", false, 20);

INSERT INTO question VALUES(21, "Which of the actors who played James Bond fought Dr. No ?");
INSERT INTO answer VALUES("Roger Moore", false, 21);
INSERT INTO answer VALUES("Pierce Brosnan", false, 21);
INSERT INTO answer VALUES("Sean Connery", true, 21);
INSERT INTO answer VALUES("Daniel Craig", false, 21);

INSERT INTO question VALUES(22, "What is the name of the leader of the Autobots in the 'Transformers' movie saga ?");
INSERT INTO answer VALUES("Optimus Prime", true, 22);
INSERT INTO answer VALUES("Amazon Prime", false, 22);
INSERT INTO answer VALUES("Ironhide", false, 22);
INSERT INTO answer VALUES("Megatron", false, 22);

INSERT INTO question VALUES(23, "Where was Ping-Pong invented?");
INSERT INTO answer VALUES("Madagascar", false, 23);
INSERT INTO answer VALUES("USA", false, 23);
INSERT INTO answer VALUES("China", false, 23);
INSERT INTO answer VALUES("Uk", true, 23);

INSERT INTO question VALUES(24, "What is the NBA team with the most titles ?");
INSERT INTO answer VALUES("Miami Heat", false, 24);
INSERT INTO answer VALUES("Chicago Bulls", false, 24);
INSERT INTO answer VALUES("Boston Celtics", true, 24);
INSERT INTO answer VALUES("Los Angeles Lakers", false, 24);

INSERT INTO question VALUES(25, "Who is the all-time top scorer in the football World Cup?");
INSERT INTO answer VALUES("John Terry", false, 25);
INSERT INTO answer VALUES("Miroslav Klose", true, 25);
INSERT INTO answer VALUES("Diego Armando Maradona", false, 25);
INSERT INTO answer VALUES("Ronaldinho GÃ¡ucho", false, 25);

#list question and answer
SELECT * FROM question INNER JOIN answer ON question.id_question = answer.id_question;