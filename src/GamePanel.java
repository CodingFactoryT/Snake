import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.util.Random;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements ActionListener{
	private final int TIMER_DELAY = 100;
	
	private int frameWidth = GameFrame.FRAME_WIDTH;
	private int frameHeight = GameFrame.FRAME_HEIGHT;

	private final int UNIT_SIZE = 50;
	private int unitsX = frameWidth/UNIT_SIZE;
	private int unitsY = frameHeight/UNIT_SIZE;	
	private final int bodyPartsX[] = new int[unitsX * unitsY];
	private final int bodyPartsY[] = new int[unitsX * unitsY];
	
	private int foodItemPositionX = 0;
	private int foodItemPositionY = 0;
			
	private int score = 0;
	
	private int bodyParts = 3;
	private int snakeDirection = 'D';
	
	private boolean isGameRunning = false;
	Timer timer;
	
	PauseMenu pauseMenu = new PauseMenu();
    GameOverScreen goScreen = new GameOverScreen();

	public GamePanel(){
		generateNewFoodItemPosition();
		
		this.setPreferredSize(new Dimension(frameWidth, frameHeight));
		timer = new Timer(TIMER_DELAY, this);
		timer.start();
		this.setFocusable(true);
		this.addKeyListener(new InputManager());
		this.add(pauseMenu);
		this.add(goScreen);
	}
	
	public void generateNewFoodItemPosition() {
		Random random = new Random();
		foodItemPositionX = random.nextInt(unitsX) * UNIT_SIZE;
		foodItemPositionY = random.nextInt(unitsY) * UNIT_SIZE;
	}
	
	public void moveSnake() {
		for(int i = bodyParts; i > 0; i--) {
			bodyPartsX[i] = bodyPartsX[i-1];
			bodyPartsY[i] = bodyPartsY[i-1];
		}
		
		switch(snakeDirection) {
		case 'U':
			bodyPartsY[0] = bodyPartsY[0] - UNIT_SIZE;
			break;	
		case 'D':
			bodyPartsY[0] = bodyPartsY[0] + UNIT_SIZE;
			break;		
		case 'L':
			bodyPartsX[0] = bodyPartsX[0] - UNIT_SIZE;
			break;
		case 'R':
			bodyPartsX[0] = bodyPartsX[0] + UNIT_SIZE;
			break;	
		}

	}
	
	public boolean checkColission() {
		for(int i = bodyParts; i > 0; i--) {	//checks if the head of the snake collides with its body
			if(bodyPartsX[0] == bodyPartsX[i] && bodyPartsY[0] == bodyPartsY[i]) {
				return true;
			}
		}
		if(bodyPartsX[0] < 0) return true;				//checks if head collides with the left border
		if(bodyPartsX[0] > frameWidth) return true;		//checks if head collides with the right border
		if(bodyPartsY[0] < 0) return true;				//checks if head collides with the top border
		if(bodyPartsY[0] > frameWidth) return true;		//checks if head collides with the bottom border
		
		return false;
	}
	
	private boolean checkFoodEaten() {
		if(bodyPartsX[0] == foodItemPositionX && bodyPartsY[0] == foodItemPositionY) return true;
		return false;
	}
	
	private void gameOver() {
		isGameRunning = false;
		timer.stop();
		goScreen.setVisible(true);
	}
	
	public void retry() {
		goScreen.setVisible(false);
		bodyParts = 3;
		snakeDirection = 'D';
		
		for(int i = 0; i < bodyParts; i++) {
			bodyPartsX[i] = 0;
			bodyPartsY[i] = 0;
		}
		
		isGameRunning = false;
		timer.start();
		generateNewFoodItemPosition();
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Color darkUnitColor = new Color(163, 208, 74);
		Color lightUnitColor = new Color(169, 216, 81);
		
		int unitCounter = 0;
		for(int i = 0; i < unitsX; i++) {
			for(int j = 0; j < unitsY; j++) {
				if(unitCounter % 2 == 0) g.setColor(darkUnitColor);
				else g.setColor(lightUnitColor);
				
				g.fillRect(i * UNIT_SIZE, j * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
				unitCounter++;
			}
			unitCounter++;
		}
		
		g.setColor(Color.red);
		g.fillOval(foodItemPositionX + 8, foodItemPositionY + 8, UNIT_SIZE -16, UNIT_SIZE -16);
		
		g.setColor(Color.blue);
		for(int i = 0; i < bodyParts; i++) {
			if(i == 0) g.fillOval(bodyPartsX[i] , bodyPartsY[i], UNIT_SIZE, UNIT_SIZE);
			else g.fillRect(bodyPartsX[i], bodyPartsY[i], UNIT_SIZE, UNIT_SIZE);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(isGameRunning) {
			moveSnake();
			
			if(checkFoodEaten()) {
				generateNewFoodItemPosition();
				bodyParts++;
				score++;
				System.out.println("Score: " + score);
			}		
			
			if(checkColission()) {
				gameOver();
			}

		}
		repaint();
		
	}
	
	public class InputManager extends KeyAdapter{
		@Override 
		public void keyPressed(KeyEvent e) {
			
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(snakeDirection != 'R') snakeDirection = 'L';
				break;	
			case KeyEvent.VK_RIGHT:
				if(snakeDirection != 'L') snakeDirection = 'R';
				break;
			case KeyEvent.VK_UP:
				if(snakeDirection != 'D') snakeDirection = 'U';
				break;
			case KeyEvent.VK_DOWN:
				if(snakeDirection != 'U') snakeDirection = 'D';
				break;
			case KeyEvent.VK_SPACE:
				isGameRunning = !isGameRunning;
				if(isGameRunning) pauseMenu.setVisible(false);
				else if(!goScreen.isVisible())pauseMenu.setVisible(true);
				break;
			case KeyEvent.VK_ENTER:
				if(goScreen.isVisible()) retry();
			}
		}
	}
}
