package GameState;

import java.util.ArrayList;

public class GameStateManager {

	
	private ArrayList<GameState> gameStates;
	protected int currentState;
	
	public static final int MENUSTATE = 0;
	public static final int LEVELSTATE = 1;
	public static final int DEADSTATE = 2;
	public static final int HTPSTATE = 3;
	
	public GameStateManager() {
		
		gameStates = new ArrayList<GameState>();
		
		currentState = MENUSTATE;
		gameStates.add(new MenuState(this));
		gameStates.add(new LevelState(this));
		gameStates.add(new DeadState(this));
		gameStates.add(new HowToPlayState(this));
		
	}
	
	public void setState(int state) {
		currentState = state;
		gameStates.get(currentState).init();
	}
	
	public void update() {
		gameStates.get(currentState).update();
	}
	
	public void draw(java.awt.Graphics2D g) {
		gameStates.get(currentState).draw(g);
	}
	
	public void keyPressed(int k) {
		gameStates.get(currentState).keyPressed(k);
	}
	
	public void keyReleased(int k) {
		gameStates.get(currentState).keyReleased(k);
	}
	
}
