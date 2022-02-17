import javax.swing.JLabel;
import javax.swing.JPanel;

public class PauseMenu extends JPanel{
	
	PauseMenu(){
		this.setVisible(false);
		this.add(new JLabel("Game Paused!"));
	}
}
