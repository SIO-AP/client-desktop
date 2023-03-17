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

public class PnlWaitingRoom extends JPanel {
	private Controller monController;
	private TablePlayer tablePlayer;

	public PnlWaitingRoom(Controller unController) {
		monController = unController;
		setOpaque(false);
		this.setBounds(10, 10, 678, 453);
		this.setLayout(null);

		if (monController.getLaConsole().isCreateGameMulti()) {
			JButton btnStart = new JButton("Commencer");
			btnStart.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					monController.getLaConsole().NextPanel(monController.getLaConsole().getPnlWaitingRoom());
				}
			});
			btnStart.setBounds(254, 357, 201, 46);
			this.add(btnStart);
		}

//		jp.setBounds(10, 47, 658, 246);
		tablePlayer = new TablePlayer(monController, 10, 47, 658, 246);
		this.add(tablePlayer);
	}

	public TablePlayer getTablePlayer() {
		return tablePlayer;
	}

	public void setTablePlayer(TablePlayer tablePlayer) {
		this.tablePlayer = tablePlayer;
	}
	
	

}
