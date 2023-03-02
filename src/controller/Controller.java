package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import javax.swing.UnsupportedLookAndFeelException;

import data.ClientWebsocket;
import data.MySQLAccess;
import model.QuizGame;
import view.ConsoleGUI;

public class Controller {
	
	private MySQLAccess laBase;

	//specification
	//user interact with console
	private ConsoleGUI laConsole;
	//implementation
	//default constructor
	
	private QuizGame laGame;
	
	public Controller() throws ParseException, UnsupportedLookAndFeelException, FileNotFoundException, ClassNotFoundException, IOException, SQLException {
		this.laGame = null;
		this.laBase = new MySQLAccess(this);
		this.laBase.connection();
		this.laConsole = new ConsoleGUI(this);
		this.laConsole.setVisible(true);
		this.laConsole.setLocationRelativeTo(null);
		
		
	}
	public MySQLAccess getLaBase() {
		return laBase;
	}
	public void setLaBase(MySQLAccess laBase) {
		this.laBase = laBase;
	}
	public QuizGame getLaGame() {
		return laGame;
	}
	public void setLaGame(QuizGame laGame) {
		this.laGame = laGame;
	}
	public ConsoleGUI getLaConsole() {
		return laConsole;
	}
	public void setLaConsole(ConsoleGUI laConsole) {
		this.laConsole = laConsole;
	}	
	
	
	
	
}
