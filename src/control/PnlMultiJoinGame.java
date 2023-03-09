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
import model.LesParty;
import model.Party;

public class PnlMultiJoinGame extends JPanel {

	private Controller monController;
	private String[][] datas;
	private JTable table;
	private JLabel lblNameGame2;
	private JLabel lblNbQuestionGame2;
	private JLabel lblTimeGame2;
	private JLabel lblIdGame2;

	public PnlMultiJoinGame(Controller unController, LesParty lesParty) {
		monController = unController;
		
		this.setBounds(10, 10, 678, 453);
		this.setLayout(null);
		
		int nbParty = lesParty.getLesParty().size();
		this.datas = new String[nbParty][];

		int i = 0;

		for (Party party : lesParty.getLesParty()) {
			String s[] = { party.getName(), Integer.toString(party.getNbQuestion()), party.getTime(),
					Integer.toString(party.getIdParty()) };
			datas[i] = s;
			i++;
		}

		table = new JTable(new MyTableModel(datas));

		table.getTableHeader().setReorderingAllowed(false);
		JScrollPane jp = new JScrollPane(table);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MyTableModel model = (MyTableModel) table.getModel();
				int row = table.getSelectedRow();
				ArrayList<String> value = model.getValueRow(row);

				lblNameGame2.setText(value.get(0));
				lblNbQuestionGame2.setText(value.get(1));
				lblTimeGame2.setText(value.get(2));
				lblIdGame2.setText(value.get(3));
			}
		});

		jp.setBounds(10, 47, 658, 246);
		this.add(jp);

		JButton btnJoinGame = new JButton("Rejoindre");
		btnJoinGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!lblIdGame2.getText().equals("")) {
					System.out.println(lblIdGame2.getText());
					
					monController.getLaConsole().setCreateGameMulti(false);
					monController.getLeClient().joinGame(Integer.parseInt(lblIdGame2.getText()));
				} else {
					System.out.println("Null");
				}
			}
		});
		btnJoinGame.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnJoinGame.setBounds(266, 397, 153, 46);
		this.add(btnJoinGame);
		
		JButton btnJoinReturn = new JButton("Annuler");
		btnJoinReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				monController.getLaConsole().PreviousPanel(monController.getLaConsole().getPnlMultiJoinGame());
			}
		});
		btnJoinReturn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnJoinReturn.setBounds(50, 397, 153, 46);
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

	class MyTableModel implements TableModel {

		private Object[][] datas;

		private String[] s = { "Nom de la partie", "Nombre de question", "Heure de début de la partie",
				"Identifiant de la partie" };

		public MyTableModel(String[][] data) {
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
