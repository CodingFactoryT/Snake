
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOverScreen extends JPanel implements ActionListener{
	
	public static JButton button = new JButton("Retry");
	
	GameOverScreen(){
		this.setVisible(false);
		this.add(new JLabel("Game Over!"));	
		button.addActionListener(this);
		this.add(button);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		GameFrame.gamePanel.retry();
	}
}
