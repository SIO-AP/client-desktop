package data;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

import controller.BCrypt;
import controller.Controller;
import model.Answer;
import model.Game;
import model.Player;
import model.Question;

public class MySQLAccess {

	private Controller monController;

	private String urlCnx;
	private String loginCnx;
	private String passwordCnx;

	public MySQLAccess(Controller unController) {
		this.monController = unController;

		// Charge le fichier de propriété contenant les informations d'accès à la BDD
		Properties properties = new Properties();
		try (InputStream fis = getClass().getClassLoader().getResourceAsStream("data/conf.properties")) {
			properties.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Charge le driver
		try {
			Class.forName(monController.getTheDecrypter().decrypt(properties.getProperty("jdbc.driver.class")));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// Initialise les paramètres de connexions
		urlCnx = monController.getTheDecrypter().decrypt(properties.getProperty("jdbc.url"));
		loginCnx = monController.getTheDecrypter().decrypt(properties.getProperty("jdbc.login"));
		passwordCnx = monController.getTheDecrypter().decrypt(properties.getProperty("jdbc.password"));
	}

	public int nombreTotalQuestion() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {

		try (Connection connection = DriverManager.getConnection(urlCnx, loginCnx, passwordCnx);
				Statement st = connection.createStatement()) {
			ResultSet resultSet = st.executeQuery("select count(*) from question");
			resultSet.next();
			int res = resultSet.getInt(1);
			return res;
		}
	}

	public ArrayList<Question> getQuestions(int nbQuestion) {

		ArrayList<Question> questions = new ArrayList<Question>();

		String query = "call get_game(?)";

		try (Connection connection = DriverManager.getConnection(urlCnx, loginCnx, passwordCnx);
				CallableStatement statement = connection.prepareCall(query);) {

			statement.setInt(1, nbQuestion);
			statement.execute();
			ResultSet resultSet = statement.getResultSet();

			while (resultSet.next()) {
				ArrayList<Answer> answers = new ArrayList<Answer>();

				int idQuestion = resultSet.getInt("id_question");
				String nomQuestion = resultSet.getString("desc_question");

				for (int i = 1; i < 5; i++) {
					String codeAnswer = Integer.toString(i);
					String descAnswer = resultSet.getString("desc_answer" + i);
					Boolean resAnswer = resultSet.getBoolean("is_correct" + i);
					answers.add(new Answer(codeAnswer, descAnswer, resAnswer));
				}

				// mélange la liste des réponses
				Collections.shuffle(answers);

				questions.add(new Question(idQuestion, nomQuestion, answers));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return questions;
	}

	public int verifLogin(String name, String password) {
		String query = "select * from player where name_player = ?";
		try (Connection connection = DriverManager.getConnection(urlCnx, loginCnx, passwordCnx);
				PreparedStatement ps = connection.prepareStatement(query)) {

			ps.setString(1, name);

			ps.execute();
			ResultSet resultSet = ps.getResultSet();
			if (resultSet.next()) {
				String password_db = resultSet.getString("password_player");

				BCrypt myHash = new BCrypt();
				if (myHash.checkpw(password, password_db)) {
					int id_db = resultSet.getInt("id_player");
					return id_db;
				}
				return 0;
			}
			return 0;
		} catch (Exception e) {
			return 0;
		}
	}

	public Game createSoloPlayerGame(Game game) {
		String query = "INSERT INTO sbcg_db.GAME VALUES (DEFAULT, ?, ?, ?, TIME(NOW()))";
		try (Connection connection = DriverManager.getConnection(urlCnx, loginCnx, passwordCnx);
				PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {

			ps.setString(1, game.getName());
			ps.setInt(2, game.getIdLeader());
			ps.setInt(3, 1);

			ps.execute();

			ResultSet rs = ps.getGeneratedKeys();
			int generatedKey = 0;
			if (rs.next()) {
				generatedKey = rs.getInt(1);
			}

			game.setIdGame(generatedKey);
			return game;

		} catch (SQLException e) {
			e.printStackTrace();
			return game;
		}
	}

	public void finishedSoloPlayerGame(Game game) {
		String queryGame = "UPDATE sbcg_db.GAME SET progress_game = 3 WHERE (id_game = ?)";

		String queryPlayer = "INSERT INTO sbcg_db.GAME_PLAYER VALUES (?, ?, ?)";

		String queryQuestion = "INSERT INTO sbcg_db.GAME_QUESTION VALUES (?, ?)";

		try (Connection connection = DriverManager.getConnection(urlCnx, loginCnx, passwordCnx);
				PreparedStatement psPlayer = connection.prepareStatement(queryPlayer);
				PreparedStatement psGame = connection.prepareStatement(queryGame);
				PreparedStatement psQuestion = connection.prepareStatement(queryQuestion);) {

			int idGame = game.getIdGame();

			psGame.setInt(1, idGame);

			psGame.execute();

			psPlayer.setInt(1, idGame);
			psPlayer.setInt(2, monController.getMonPlayer().getMyId());
			psPlayer.setInt(3, monController.getMonPlayer().getMyScore());

			psPlayer.execute();

			for (Question question : game.getGroupQuestions()) {

				psQuestion.setInt(1, idGame);
				psQuestion.setInt(2, question.getId());

				psQuestion.execute();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}