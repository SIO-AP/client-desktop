#databases list on instance
show databases;
USE h5ws00fg4ypyuohr;
#insert stub data


# pierre -> toto
INSERT INTO vth_users VALUES("pierre","$2a$12$HHtp5X2bEzofYJJnr/eMX..czPbCS04dfodHcjNVFJjiyrH7xQhbK");
# paul -> tutu
INSERT INTO player (name_player, password_player) VALUES("paul","$2a$12$IG/Gbz.wqhoaFlH0VPNwrelDhobJHlUUzZbZ8B2IWD3oLZXLNDxHC");
# jacques -> titi
INSERT INTO player (name_player, password_player) VALUES("jacques","$2a$12$Jd40qa9wra0H1NOSXjr6l.DdkapDIYBOwjHxw8AfCf4gzxLsiVavu");

INSERT INTO question VALUES(0, "Qu’est ce qu’un VPN ?", 2);
INSERT INTO answer VALUES(DEFAULT, "Vaisseau pour neptune", false, 0);
INSERT INTO answer VALUES(DEFAULT, "Réseau privé virtuel", true, 0);
INSERT INTO answer VALUES(DEFAULT, "Visual Protection Network", false, 0);
INSERT INTO answer VALUES(DEFAULT, "Marque d’ordinateurs", false, 0);


INSERT INTO question VALUES(1, "Que signifie DDOS ?", 3);
INSERT INTO answer VALUES(DEFAULT, "Attaque par déni de service", true, 1);
INSERT INTO answer VALUES(DEFAULT, "Data Destructor on system", false, 1);
INSERT INTO answer VALUES(DEFAULT, "Force secrète militaire", false, 1);
INSERT INTO answer VALUES(DEFAULT, "Logiciel Antivirus", false, 1);

INSERT INTO question VALUES(2, "Quel mot de passe est le plus sécurisé ?", 1);
INSERT INTO answer VALUES(DEFAULT, "azerty", false, 2);
INSERT INTO answer VALUES(DEFAULT, "azerty1234", false, 2);
INSERT INTO answer VALUES(DEFAULT, "azerty1234!", false, 2);
INSERT INTO answer VALUES(DEFAULT, "@z3rty4321!", true, 2);

INSERT INTO question VALUES(3, "Quel est l'enjeu de la cybersécurité ?", 1);
INSERT INTO answer VALUES(DEFAULT, "Révéler les secrets", false, 3);
INSERT INTO answer VALUES(DEFAULT, "Protéger le système d’information", true, 3);
INSERT INTO answer VALUES(DEFAULT, "Augmenter les risques pesant sur le système d’information", false, 3);
INSERT INTO answer VALUES(DEFAULT, "Rendre difficile la vie des utilisateurs en ajoutant plusieurs contraintes", false, 3);

INSERT INTO question VALUES(4, "Quelle organisation gère la protection des données ?", 2);
INSERT INTO answer VALUES(DEFAULT, "Gendarmerie National", false, 4);
INSERT INTO answer VALUES(DEFAULT, "Google", false, 4);
INSERT INTO answer VALUES(DEFAULT, "CNIL", true, 4);
INSERT INTO answer VALUES(DEFAULT, "Le groupe Anonymous", false, 4);

INSERT INTO question VALUES(5, "Qu’est ce qu’un Antivirus ?", 1);
INSERT INTO answer VALUES(DEFAULT, "Logiciel pour protéger son ordinateur", true, 5);
INSERT INTO answer VALUES(DEFAULT, "Une alternative au vaccin", false, 5);
INSERT INTO answer VALUES(DEFAULT, "Logiciel pour pirater", false, 5);
INSERT INTO answer VALUES(DEFAULT, "Un ordinateur sur protégé", false, 5);

INSERT INTO question VALUES(6, "Qu’est ce qui permet de faire respecter la politique de sécurité du réseau ?", 2);
INSERT INTO answer VALUES(DEFAULT, "Une coque en silicone", false, 6);
INSERT INTO answer VALUES(DEFAULT, "Un pare feu", true, 6);
INSERT INTO answer VALUES(DEFAULT, "Google Drive", false, 6);
INSERT INTO answer VALUES(DEFAULT, "Le développeur de l’entreprise", false, 6);

INSERT INTO question VALUES(7, "Lequel est un programme malveillant indépendant qui ne nécessite aucun autre programme ?", 4);
INSERT INTO answer VALUES(DEFAULT, "Porte à piége", false, 7);
INSERT INTO answer VALUES(DEFAULT, "Cheval de troie", false, 7);
INSERT INTO answer VALUES(DEFAULT, "Ver", true, 7);
INSERT INTO answer VALUES(DEFAULT, "Virus", false, 7);

INSERT INTO question VALUES(8, "Qu'est-ce qu'un code incorporé dans un programme pour «exploser» si les conditions sont remplies ?", 1);
INSERT INTO answer VALUES(DEFAULT, "Bombe logique", true, 8);
INSERT INTO answer VALUES(DEFAULT, "Porte à piège", false, 8);
INSERT INTO answer VALUES(DEFAULT, "Virus", false, 8);
INSERT INTO answer VALUES(DEFAULT, "Cheval de troie", false, 8);

INSERT INTO question VALUES(9, "Quel forme de virus est conçue pour éviter la détection par des logiciels antivirus ?", 2);
INSERT INTO answer VALUES(DEFAULT, "Virus de macro", false, 9);
INSERT INTO answer VALUES(DEFAULT, "Virus parasite", false, 9);
INSERT INTO answer VALUES(DEFAULT, "Virus polymorphe", false, 9);
INSERT INTO answer VALUES(DEFAULT, "Virus furtif", true, 9);

INSERT INTO question VALUES(10, "Quel protocole est utilisé pour sécuriser les e-mails ?", 3);
INSERT INTO answer VALUES(DEFAULT, "POP", false, 10);
INSERT INTO answer VALUES(DEFAULT, "PGP", true, 10);
INSERT INTO answer VALUES(DEFAULT, "SNMP", false, 10);
INSERT INTO answer VALUES(DEFAULT, "HTTP", false, 10);

INSERT INTO question VALUES(11, "Quel est le nombre de sous-clés générées dans l’algorithme IDEA ?", 4);
INSERT INTO answer VALUES(DEFAULT, "54", false, 11);
INSERT INTO answer VALUES(DEFAULT, "48", false, 11);
INSERT INTO answer VALUES(DEFAULT, "52", true, 11);
INSERT INTO answer VALUES(DEFAULT, "50", false, 11);

INSERT INTO question VALUES(12, "Lequel est un exemple d’algorithme de clé publique ?", 4);
INSERT INTO answer VALUES(DEFAULT, "RSA", true, 12);
INSERT INTO answer VALUES(DEFAULT, "DES", false, 12);
INSERT INTO answer VALUES(DEFAULT, "IREA", false, 12);
INSERT INTO answer VALUES(DEFAULT, "RC5", false, 12);

INSERT INTO question VALUES(13, "Qu’est ce qui transforme le message en format illisible par les pirates ?", 3);
INSERT INTO answer VALUES(DEFAULT, "Décryptage", false, 13);
INSERT INTO answer VALUES(DEFAULT, "Cryptage", true, 13);
INSERT INTO answer VALUES(DEFAULT, "Transformation", false, 13);
INSERT INTO answer VALUES(DEFAULT, "Suppression", false, 13);

INSERT INTO question VALUES(14, "Quel est le numéro de port pour HTTPS (HTTP Secure) ?", 4);
INSERT INTO answer VALUES(DEFAULT, "443", true, 14);
INSERT INTO answer VALUES(DEFAULT, "404", false, 14);
INSERT INTO answer VALUES(DEFAULT, "43", false, 14);
INSERT INTO answer VALUES(DEFAULT, "445", false, 14);

INSERT INTO question VALUES(15, "Quel est la méthode utilisée pour valider l’identité de l’expéditeur d’un message ?", 2);
INSERT INTO answer VALUES(DEFAULT, "Selfie", false, 15);
INSERT INTO answer VALUES(DEFAULT, "Décryptage", false, 15);
INSERT INTO answer VALUES(DEFAULT, "Certificat numérique", true, 15);
INSERT INTO answer VALUES(DEFAULT, "Formule de politesse à la fin", false, 15);

INSERT INTO question VALUES(16, "Qu’est ce qu’un hacker ?", 1);
INSERT INTO answer VALUES(DEFAULT, "Un pirate informatique", true, 16);
INSERT INTO answer VALUES(DEFAULT, "Un biscuit aperitif", false, 16);
INSERT INTO answer VALUES(DEFAULT, "Un virus surpuissant", false, 16);
INSERT INTO answer VALUES(DEFAULT, "Une clé usb pour écouter des conversations", false, 16);

INSERT INTO question VALUES(17, "Qu’est ce qu’un pentest ?", 3);
INSERT INTO answer VALUES(DEFAULT, "Un stylo connecté", false, 17);
INSERT INTO answer VALUES(DEFAULT, "Un test de débit réseau", true, 17);
INSERT INTO answer VALUES(DEFAULT, "Evaluation de la sécurité d’un système d’information", false, 17);
INSERT INTO answer VALUES(DEFAULT, "Un virus pour pénétrer la base de donnée", false, 17);

INSERT INTO question VALUES(18, "Qu’est ce que le RGDP ?", 3);
INSERT INTO answer VALUES(DEFAULT, "Règlement général sur la protection des données", true, 18);
INSERT INTO answer VALUES(DEFAULT, "Règle globale pour le piratage des données", false, 18);
INSERT INTO answer VALUES(DEFAULT, "Un format de fichier crypté", false, 18);
INSERT INTO answer VALUES(DEFAULT, "Regular guaranteed data protection", false, 18);

INSERT INTO question VALUES(19, "Quelle est la perte moyenne de CA suite à une attaque ?", 4);
INSERT INTO answer VALUES(DEFAULT, "13%", false, 19);
INSERT INTO answer VALUES(DEFAULT, "43%", false, 19);
INSERT INTO answer VALUES(DEFAULT, "80%", false, 19);
INSERT INTO answer VALUES(DEFAULT, "27%", true, 19);


#list question and answer
SELECT * FROM question INNER JOIN answer ON question.id_question = answer.id_question;