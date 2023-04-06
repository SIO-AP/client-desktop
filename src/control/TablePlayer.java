package control;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import controller.Controller;
import model.Player;

public class TablePlayer extends JScrollPane {
	private Controller monController;
	private String[][] datas;
	private JTable table;

	public TablePlayer(Controller unController, int x, int y, int w, int h, boolean displayNbQuestion) {
		monController = unController;

		this.datas = new String[monController.getLaGame().getPlayerList().size()][];

		for (int i = 0; i < datas.length; i++) {
			Player player = monController.getLaGame().getPlayerList().get(i);
			String name = (player.getMyId() == monController.getMonPlayer().getMyId())
					? monController.getMonPlayer().getMyName() + " (vous)"
					: player.getMyName();

			String score = (Integer.toString((player.getMyId() == monController.getMonPlayer().getMyId())
					? monController.getMonPlayer().getMyScore()
					: player.getMyScore()));

			String question;
			int nbQuestions = (player.getMyId() == monController.getMonPlayer().getMyId())
					? monController.getMonPlayer().getNbQuestion()
					: player.getNbQuestion();
			int totalQuestions = monController.getLaGame().getNbQuestion();

			if (nbQuestions > totalQuestions) {
				question = "Fini";
			} else {
				question = nbQuestions + "/" + totalQuestions;
			}

			datas[i] = (displayNbQuestion) ? new String[] { name, score, question } : new String[] { name };
		}

		table = new JTable(new TableModelPlayer(datas, true));

		table.getTableHeader().setReorderingAllowed(false);

		CentreRenderer centerRenderer = new CentreRenderer();
		table.setDefaultRenderer(Object.class, centerRenderer);

		setViewportView(table);
		// new JScrollPane(table);

		setBounds(x, y, w, h);

	}

	private class TableModelPlayer implements TableModel {

		private Object[][] datas;

		private String[] s;

		public TableModelPlayer(String[][] data, boolean displayNbQuestion) {
			if (displayNbQuestion) {
				s = new String[] { "Joueur", "Score", "Avancement" };
			} else {
				s = new String[] { "Joueur" };
			}
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

	private class CentreRenderer extends DefaultTableCellRenderer {

		public CentreRenderer() {
			setHorizontalAlignment(SwingConstants.CENTER);
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			return this;
		}
	}
}