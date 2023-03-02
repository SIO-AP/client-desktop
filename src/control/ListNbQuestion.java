package control;

import java.awt.Font;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JComboBox;

import controller.Controller;

public class ListNbQuestion extends JComboBox<Object> {

	private Controller monController;

	public ListNbQuestion(Controller unController) {
		monController = unController;

		this.setFont(new Font("Tahoma", Font.PLAIN, 15));
		this.setBounds(204, 214, 153, 48);

		int nombreTotalQuestion;
		try {
			nombreTotalQuestion = monController.getLaBase().nombreTotalQuestion();
			nombreTotalQuestion = (int) (Math.floor(nombreTotalQuestion / 5));

			for (int i = 1; i < nombreTotalQuestion + 1; i++) {
				this.addItem(i * 5);
			}
		} catch (ClassNotFoundException | IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
