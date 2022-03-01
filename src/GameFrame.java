import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class GameFrame extends JFrame{
	
	public static final int FRAME_WIDTH = 900;
	public static final int FRAME_HEIGHT = 900;
	public static GamePanel gamePanel= new GamePanel();
	
	GameFrame(){	
		this.setTitle("Snake");	
		this.getRootPane().setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, new Color(46, 160, 28)));	//dark green Border
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.add(gamePanel);
		InputStream IconStream = new BufferedInputStream(getClass().getResourceAsStream("/resources/AppIcon.png"));
		try { 
			this.setIconImage(new ImageIcon(ImageIO.read(IconStream)).getImage()); 
		}
		catch(IOException e) {}
		
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
}
