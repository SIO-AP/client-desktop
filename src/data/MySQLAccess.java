package data;

import java.io.FileInputStream;
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
import java.util.Random;

import controller.Controller;
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
	private Connection conn;

	public MySQLAccess(Controller unController) {
		this.monController = unController;
	}

	public void connection() throws IOException, SQLException, ClassNotFoundException {
		// Charge le fichier de propriété contenant les informations d'accès à la BDD
		Properties properties = new Properties();
		try (InputStream fis = getClass().getClassLoader().getResourceAsStream("data/conf.properties")) {
			properties.load(fis);
		}

		// Charge le driver
		Class.forName(properties.getProperty("jdbc.driver.class"));

		// Crée la connexion
		urlCnx = properties.getProperty("jdbc.url");
		loginCnx = properties.getProperty("jdbc.login");
		passwordCnx = properties.getProperty("jdbc.password");

		conn = DriverManager.getConnection(urlCnx, loginCnx, passwordCnx);
	}

//	private Connection readDataBase() throws FileNotFoundException, IOException, ClassNotFoundException {
//		Properties properties = new Properties();
//		try (FileInputStream fis = new FileInputStream("data/conf.properties")) {
//			properties.load(fis);
//		}
//		Class.forName(properties.getProperty("jdbc.driver.class"));
//
//		urlCnx = properties.getProperty("jdbc.url");
//		loginCnx = properties.getProperty("jdbc.login");
//		passwordCnx = properties.getProperty("jdbc.password");
//
//		try {
//			// Setup the connection with the DB
//			connect = DriverManager.getConnection(urlCnx, loginCnx, passwordCnx);
//			return connect;
//		} catch (Exception e) {
//			return null;
//		}
//	}

	public int nombreTotalQuestion() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
		
		try (Statement st = conn.createStatement()) {
		ResultSet resultSet = st.executeQuery("select count(*) from question");
		resultSet.next();
		int res = resultSet.getInt(1);
		return res;
		}
	}

	public int generateQuestionId(int nombreTotalQuestion) {
		int randomNumber = new Random().nextInt(nombreTotalQuestion + 0);
		return randomNumber;
	}

	private ArrayList<Answer> getAnswersFromQuestion(int id)
			throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {

		ArrayList<Answer> answers = new ArrayList<Answer>();

		String strSqlQuestion = "select * from answer where id_question = " + id + " order by rand()";

		try (Statement st = conn.createStatement()) {
			ResultSet resultSet = st.executeQuery(strSqlQuestion);
			for (int i = 0; i < 4; i++) {
				resultSet.next();
				String indexA = Integer.toString(i + 1);
				String descA = resultSet.getString(1);
				Boolean resA = resultSet.getBoolean(2);
				answers.add(new Answer(indexA, descA, resA));
			}
			return answers;
		}

	}

	public ArrayList<Question> getQuestions(int maxQuestions)
			throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {

		ArrayList<Question> questions = new ArrayList<Question>();

		ArrayList<Integer> listeIdQuestion = listeIdQuestion(maxQuestions);

		try (Statement st = conn.createStatement()) {

			for (int i : listeIdQuestion) {
				System.out.println(i);

				ResultSet resultSet = st.executeQuery("select * from question where id_question = " + i);
				resultSet.next();

				int idQuestion = resultSet.getInt(1);
				String nomQuestion = resultSet.getString(2);

				questions.add(new Question(nomQuestion, getAnswersFromQuestion(idQuestion)));
			}
		}
		return questions;
	}

	private ArrayList<Integer> listeIdQuestion(int maxQuestions)
			throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {

		ArrayList<Integer> listeIdQuestion = new ArrayList<Integer>();
		int nombreTotalQuestion = nombreTotalQuestion();

		for (int i = 0; i < maxQuestions; i++) {
			int test = generateQuestionId(nombreTotalQuestion);

			if (listeIdQuestion.contains(test)) {
				while (listeIdQuestion.contains(test)) {
					test = generateQuestionId(nombreTotalQuestion);
				}
				listeIdQuestion.add(test);
			} else {
				listeIdQuestion.add(test);
			}
		}
		return listeIdQuestion;
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