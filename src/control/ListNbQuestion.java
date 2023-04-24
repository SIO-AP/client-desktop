package control;

import java.awt.Component;
import java.awt.Font;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;

import controller.Controller;

public class ListNbQuestion extends JComboBox<Object> {

	private Controller monController;

	public ListNbQuestion(Controller unController, int x, int y, int width, int height) {
		monController = unController;

		setFont(new Font("corbel", Font.PLAIN, 20));
		setBounds(x, y, width, height);
		setRenderer(new CenteredCellRenderer());

		int nombreTotalQuestion;
		try {
			nombreTotalQuestion = monController.getLaBase().nombreTotalQuestion();
			nombreTotalQuestion = (int) (Math.floor(nombreTotalQuestion / 5));

			for (int i = 1; i < nombreTotalQuestion + 1; i++) {
				addItem(i * 5);
			}
		} catch (ClassNotFoundException | IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

class CenteredCellRenderer extends DefaultListCellRenderer {
	private static final long serialVersionUID = 1L;

	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		if (renderer instanceof JLabel) {
			JLabel label = (JLabel) renderer;
			label.setHorizontalAlignment(JLabel.CENTER);
		}
		return renderer;
	}
}