package control;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import controller.Controller;
import model.LesGame;
import view.ConsoleGUI;
import model.Game;

public class PnlMultiJoinGame extends JPanel {

	private Controller monController;
	private String[][] datas;
	private JTable table;
	private JLabel lblNameGame2;
	private JLabel lblNbQuestionGame2;
	private JLabel lblTimeGame2;
	private JLabel lblIdGame2;

	public PnlMultiJoinGame(Controller unController, LesGame lesParty) {
		monController = unController;

		monController.getLaConsole().setBackground("img/PnlMultiJoinGame/back.png");

		setOpaque(false);

		this.setBounds(ConsoleGUI.rectangle);
		this.setLayout(null);

		int nbParty = lesParty.getLesGame().size();
		if (nbParty > 0) {
			this.datas = new String[nbParty][];

			int i = 0;

			for (Game party : lesParty.getLesGame()) {
				String s[] = { party.getName(), Integer.toString(party.getNbQuestion()), party.getTime(),
						Integer.toString(party.getIdGame()) };
				datas[i] = s;
				i++;
			}
		} else {
			datas = new String[1][];
			String s[] = { "", "", "", "" };
			datas[0] = s;
		}

		table = new JTable(new TableModelGame(datas));

		table.getTableHeader().setReorderingAllowed(false);
		JScrollPane jp = new JScrollPane(table);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				TableModelGame model = (TableModelGame) table.getModel();
				int row = table.getSelectedRow();
				ArrayList<String> value = model.getValueRow(row);

				lblNameGame2.setText(value.get(0));
				lblNbQuestionGame2.setText(value.get(1));
				lblTimeGame2.setText(value.get(2));
				lblIdGame2.setText(value.get(3));
			}
		});

		jp.setBounds(10, 50, 700, 250);
		this.add(jp);

		ButtonDisplay btnJoinGame = new ButtonDisplay(750, 50, 250, 50, "img/PnlMultiJoinGame/rejoindre_eteint.png",
				"img/PnlMultiJoinGame/rejoindre_allume.png");
		btnJoinGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!lblIdGame2.getText().equals("")) {
					monController.getLaConsole().setCreateGameMulti(false);
					monController.getLeClient().joinGame(Integer.parseInt(lblIdGame2.getText()));
				}
			}
		});
		this.add(btnJoinGame);

		JButton btnReloadGame = new ButtonDisplay(750, 150, 250, 50, "img/PnlMultiJoinGame/recharger_eteint.png",
				"img/PnlMultiJoinGame/recharger_allume.png");
		btnReloadGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				monController.getLaConsole().setReloadJoinGame(true);
				monController.initLesGames();
			}
		});
		this.add(btnReloadGame);

		JButton btnJoinReturn = new ButtonDisplay(750, 250, 250, 50, "img/PnlMultiJoinGame/retour_eteint.png",
				"img/PnlMultiJoinGame/retour_allume.png");
		btnJoinReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				monController.PreviousPanel(monController.getLaConsole().getPnlMultiJoinGame());
			}
		});
		this.add(btnJoinReturn);

		JLabel lblNameGame1 = new JLabel("Nom de la partie :");
		lblNameGame1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNameGame1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNameGame1.setBounds(23, 303, 130, 21);
		this.add(lblNameGame1);

		JLabel lblTimeGame1 = new JLabel("Heure de bébut :");
		lblTimeGame1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTimeGame1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTimeGame1.setBounds(10, 326, 143, 21);
		this.add(lblTimeGame1);

		JLabel lblNbQuestionGame1 = new JLabel("Nombre de question :");
		lblNbQuestionGame1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNbQuestionGame1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNbQuestionGame1.setBounds(380, 303, 130, 21);
		this.add(lblNbQuestionGame1);

		JLabel lblIdGame1 = new JLabel("Identifiant :");
		lblIdGame1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblIdGame1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblIdGame1.setBounds(380, 330, 130, 21);
		this.add(lblIdGame1);

		lblNameGame2 = new JLabel();
		lblNameGame2.setHorizontalAlignment(SwingConstants.LEFT);
		lblNameGame2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNameGame2.setBounds(163, 303, 207, 21);
		this.add(lblNameGame2);

		lblTimeGame2 = new JLabel();
		lblTimeGame2.setHorizontalAlignment(SwingConstants.LEFT);
		lblTimeGame2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTimeGame2.setBounds(163, 326, 207, 21);
		this.add(lblTimeGame2);

		lblNbQuestionGame2 = new JLabel();
		lblNbQuestionGame2.setHorizontalAlignment(SwingConstants.LEFT);
		lblNbQuestionGame2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNbQuestionGame2.setBounds(520, 303, 148, 21);
		this.add(lblNbQuestionGame2);

		lblIdGame2 = new JLabel();
		lblIdGame2.setHorizontalAlignment(SwingConstants.LEFT);
		lblIdGame2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblIdGame2.setBounds(520, 331, 148, 21);
		this.add(lblIdGame2);

	}

	private class TableModelGame implements TableModel {

		private Object[][] datas;

		private String[] s = { "Nom de la partie", "Nombre de question", "Heure de début de la partie",
				"Identifiant de la partie" };

		public TableModelGame(String[][] data) {
			this.datas = data;
		}

		public int getRowCount() {
			return datas.length; // To change body of implemented methods use File | Settings | File Templates.
		}

		public int getColumnCount() {
			return datas[0].length; // To change body of implemented methods use File | Settings | File Templates.
		}

		public String getColumnName(int columnIndex) {
			return s[columnIndex]; // To change body of implemented methods use File | Settings | File Templates.
		}

		public Class<?> getColumnClass(int columnIndex) {
			return String.class; // To change body of implemented methods use File | Settings | File Templates.
		}

		public boolean isCellEditable(int rowIndex, int columnIndex) {
			// everything is always editable...
			return false; // To change body of implemented methods use File | Settings | File Templates.
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			return datas[rowIndex][columnIndex]; // To change body of implemented methods use File | Settings | File
													// Templates.
		}

		public ArrayList<String> getValueRow(int rowIndex) {
			ArrayList<String> row = new ArrayList<>();
			row.add((String) datas[rowIndex][0]);
			row.add((String) datas[rowIndex][1]);
			row.add((String) datas[rowIndex][2]);
			row.add((String) datas[rowIndex][3]);
			return row; // To change body of implemented methods use File | Settings | File
			// Templates.
		}

		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			datas[rowIndex][columnIndex] = aValue;
			// To change body of implemented methods use File | Settings | File Templates.
		}

		public void addTableModelListener(TableModelListener l) {
			// To change body of implemented methods use File | Settings | File Templates.
		}

		public void removeTableModelListener(TableModelListener l) {
			// To change body of implemented methods use File | Settings | File Templates.
		}
	}
}
