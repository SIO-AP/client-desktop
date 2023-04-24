package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import controller.Controller;
import model.Player;
import view.ConsoleGUI;

public class PnlWaitingRoom extends JPanel {
	private Controller monController;
	private TablePlayer tablePlayer;

	public PnlWaitingRoom(Controller unController) {
		monController = unController;

		monController.getLaConsole().setBackground("img/PnlWaitingRoom/back.png");

		setOpaque(false);

		this.setBounds(ConsoleGUI.rectangle);
		this.setLayout(null);

		if (monController.isCreateGameMulti()) {
			ButtonDisplay btnStart = new ButtonDisplay(215, 360, 250, 50, "img/PnlWaitingRoom/commencer_eteint.png", "img/PnlWaitingRoom/commencer_allume.png");
			btnStart.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					monController.NextPanel(monController.getLaConsole().getPnlWaitingRoom());
				}
			});
			this.add(btnStart);
		}

		tablePlayer = new TablePlayer(monController, 10, 47, 658, 246, false);
		this.add(tablePlayer);
	}

	public TablePlayer getTablePlayer() {
		return tablePlayer;
	}

	public void setTablePlayer(TablePlayer tablePlayer) {
		this.tablePlayer = tablePlayer;
	}
	
	

}
