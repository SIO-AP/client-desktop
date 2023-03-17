package controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class test {

	private String urlCnx;
	private String loginCnx;
	private String passwordCnx;

	public test() {

		// Charge le fichier de propriété contenant les informations d'accès à la BDD
		Properties properties = new Properties();
		try (InputStream fis = getClass().getClassLoader().getResourceAsStream("data/conf.properties")) {
			properties.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Charge le driver
		try {
			Class.forName(properties.getProperty("jdbc.driver.class"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Jasypt theDecrypter = new Jasypt();

		// Initialise les paramètres de connexions
		urlCnx = theDecrypter.decrypt(properties.getProperty("jdbc.url"));
		loginCnx = theDecrypter.decrypt(properties.getProperty("jdbc.login"));
		passwordCnx = theDecrypter.decrypt(properties.getProperty("jdbc.password"));

		try (Connection conn = DriverManager.getConnection(urlCnx, loginCnx, passwordCnx)) {
			// Création de l'encrypteur Jasypt
			StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
			encryptor.setPassword("4C45Lrh26YWbKz73FwtXYkc6");

			// Préparation de la requête SQL pour récupérer les réponses
			String selectQuery = "SELECT id_answer, desc_answer FROM answer";
			PreparedStatement selectStmt = conn.prepareStatement(selectQuery);

			// Exécution de la requête et traitement des résultats
			ResultSet rs = selectStmt.executeQuery();
			while (rs.next()) {
				int idQuestion = rs.getInt("id_answer");
				String reponse = rs.getString("desc_answer");

				// Encryptage de la réponse avec Jasypt
				String encryptedReponse = encryptor.encrypt(reponse);

				// Mise à jour de la base de données avec la réponse encryptée
				String updateQuery = "UPDATE answer SET desc_answer = ? WHERE id_answer = ?";
				PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
				updateStmt.setString(1, encryptedReponse);
				updateStmt.setInt(2, idQuestion);
				updateStmt.executeUpdate();
			}
			System.out.println("Réponses encryptées avec succès !");
		} catch (SQLException ex) {
			System.out.println("Erreur lors de la récupération des réponses : " + ex.getMessage());
		}
	}
}