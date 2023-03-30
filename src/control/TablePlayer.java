package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import controller.Controller;
import model.Player;

public class TablePlayer extends JScrollPane {
	private Controller monController;
	private String[][] datas;
	private JTable table;

	public TablePlayer(Controller unController, int x, int y, int w, int h) {
		monController = unController;

		this.datas = new String[monController.getLaGame().getPlayerList().size()][];
		int i = 0;
		for (Player player : monController.getLaGame().getPlayerList()) {
			String s[] = { player.getMyName(), Integer.toString(player.getMyScore()) };
			datas[i] = s;
			i++;
		}

		table = new JTable(new TableModelPlayer(datas));

		table.getTableHeader().setReorderingAllowed(false);

		setViewportView(table);
		// new JScrollPane(table);

		setBounds(x, y, w, h);

	}

	private class TableModelPlayer implements TableModel {

		private Object[][] datas;

		private String[] s = { "Nom du joueur", "Score" };

		public TableModelPlayer(String[][] data) {
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

}
