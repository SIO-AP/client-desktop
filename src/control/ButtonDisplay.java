package control;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ButtonDisplay extends JButton {
	
	private ImageIcon backgroundImage;

	public ButtonDisplay(int x, int y, int width, int height, String a, String b) {
		setBounds(x, y, width, height);
		
		InputStream input = getClass().getClassLoader().getResourceAsStream(a);
		
		try {
			backgroundImage = new ImageIcon(ImageIO.read(input));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	//	backgroundImage = new ImageIcon(getClass().getClassLoader().getResource(a));

		Image img = backgroundImage.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
		backgroundImage = new ImageIcon(img);
		setIcon(backgroundImage);
		// Supprimer la bordure du bouton
        setBorderPainted(false);
        		
        // Ajouter un MouseListener pour changer l'image lorsqu'une souris passe dessus
        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
            	// Changer l'image de fond lorsque la souris rentre dans le bouton
            	InputStream input = getClass().getClassLoader().getResourceAsStream(b);
        		
        		try {
        			ImageIcon background = new ImageIcon(ImageIO.read(input));
                	Image img = background.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
            		background = new ImageIcon(img);
            		setIcon(background);
            		setCursor(new Cursor(Cursor.HAND_CURSOR));
        		} catch (IOException e1) {
        			// TODO Auto-generated catch block
        			e1.printStackTrace();
        		}
            	
               
            }
            public void mouseExited(MouseEvent e) {
                // Changer l'image de fond lorsque la souris sort du bouton
                setIcon(backgroundImage);
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        });
	}
}
