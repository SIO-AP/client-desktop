package data;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import controller.BCrypt;
import controller.Controller;
import controller.Jasypt;
import model.Answer;
import model.Question;

public class MySQLAccess {
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

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
			Class.forName(properties.getProperty("jdbc.driver.class"));
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

	private ArrayList<Answer> getAnswersFromQuestion(Connection connection, int id)
			throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {

		ArrayList<Answer> answers = new ArrayList<Answer>();

		String strSqlQuestion = "select * from answer where id_question = " + id + " order by rand()";

		try (Statement st = connection.createStatement()) {
			ResultSet resultSet = st.executeQuery(strSqlQuestion);
			for (int i = 0; i < 4; i++) {
				resultSet.next();
				String indexA = Integer.toString(i + 1);
				String descA = resultSet.getString(2);
				Boolean resA = resultSet.getBoolean(3);
				answers.add(new Answer(indexA, descA, resA));
			}
			return answers;
		}

	}

	public ArrayList<Question> getQuestions(ArrayList<Integer> listeIdQuestion)
			throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {

		ArrayList<Question> questions = new ArrayList<Question>();

		try (Connection connection = DriverManager.getConnection(urlCnx, loginCnx, passwordCnx);
				Statement st = connection.createStatement()) {

			for (int i : listeIdQuestion) {
				ResultSet resultSet = st.executeQuery("select * from question where id_question = " + i);
				resultSet.next();

				int idQuestion = resultSet.getInt(1);
				String nomQuestion = resultSet.getString(2);

				questions.add(new Question(idQuestion, nomQuestion, getAnswersFromQuestion(connection, idQuestion)));
			}
		}
		return questions;
	}

	public int verifLogin(String name, String password) {
		try (Connection connection = DriverManager.getConnection(urlCnx, loginCnx, passwordCnx);
				Statement st = connection.createStatement()) {

			ResultSet resultSet = st.executeQuery("select * from player where name_player = '" + name + "'");
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

	// You need to close the resultSet
	private void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {

		}
	}

}