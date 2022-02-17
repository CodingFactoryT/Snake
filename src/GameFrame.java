import java.awt.Color;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class GameFrame extends JFrame{
	
	public static final int FRAME_WIDTH = 1000;
	public static final int FRAME_HEIGHT = 1000;
	public static GamePanel gamePanel= new GamePanel();
	
	GameFrame(){	
		this.setTitle("Snake");	
		this.getRootPane().setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, new Color(46, 160, 28)));	//dark green Border
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.add(gamePanel);
		try { 
			this.setIconImage(new ImageIcon(new File("").getCanonicalFile() + "\\resources\\AppIcon.png").getImage()); 
		}
		catch(IOException e) {}
		
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
}
