//package view;
//
//import java.util.ArrayList;
//import java.util.Random;
//import java.util.Scanner;
//
//import controller.Controller;
//import data.MySQLAccess;
//import model.Answer;
//import model.Question;
//import model.QuizGame;
//
//
//public class ConsoleChar {
//	
//	//specification
//	private Controller monController;
//	private MySQLAccess db = new MySQLAccess();
//
//
//	public ConsoleChar(Controller unController) throws Exception {
//		super();
//		this.monController = unController;
//
//        QuizGame player1game = newQuizGame(playerName, 5, db);
//
//        for (Question question : player1game.getQuestions()) {
//        	
//            System.out.println(question.getDescriptionQuestion());
//
//            for (Answer answer : question.getAnswers()) {
//                System.out.println(answer.getCodeAnswer() + ") " + answer.getDescriptionAnswer());
//            }
//            
//            System.out.println("Choose your answer!");
//            String playerAnswer = myScanner.nextLine();
//
//            while (!player1game.isValidAnswer(playerAnswer)) {
//                System.out.println("Not a valid answer, select a correct option please!");
//                playerAnswer = myScanner.nextLine();
//            }
//
//            if (player1game.isCorrectThisAnswer(playerAnswer, question.getAnswers())) {
//                player1game.addPlayerScore(10);
//                System.out.println("Bravoooo, You got it right ! you accumulate 10 points!");
//            } else {
//                System.out.println("Oops.. wrong answer! Better luck next time ! :)");
//            }
//        }
//        String feedbackMessage = "";
//        if (player1game.getPlayerScore() >= 30) {
//            feedbackMessage = "Congratulations !! Your score is " + player1game.getPlayerScore() + " points ! You are a champ! :D";
//        } else {
//            feedbackMessage = "I'm afraid your score is " + player1game.getPlayerScore() + "points.. You lost the game! Better luck next time ;)";
//        }
//        System.out.println(feedbackMessage);
//    }
//
//   
//    public static QuizGame newQuizGame(String playerNameParam, int questionNumber, MySQLAccess db) throws Exception {
//    	ArrayList<Question> quizQuestions = db.getQuestions(questionNumber);
//        return new QuizGame(playerNameParam, 0, quizQuestions);
//    }
//
//    
//
//	}
