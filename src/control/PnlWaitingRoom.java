package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import controller.Controller;

public class PnlWaitingRoom extends JPanel {
	private Controller monController;
	
	public PnlWaitingRoom(Controller unController) {
		monController = unController;
		
		this.setBounds(10, 10, 678, 453);
		this.setLayout(null);
		
		JButton btnStart = new JButton("Commencer");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				monController.getLaConsole().NextPanel(monController.getLaConsole().getPnlWaitingRoom());
			}
		});
		btnStart.setBounds(254, 357, 201, 46);
		this.add(btnStart);
	}

}
