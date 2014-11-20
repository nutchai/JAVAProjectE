package GameState;

import java.util.ArrayList;

public class GameStateManager {

	
	private GameState[] gameStates;
	protected int currentState;
	
	public static final int ALLSTATE = 4;
	public static final int MENUSTATE = 0;
	public static final int LEVELSTATE = 1;
	public static final int DEADSTATE = 2;
	public static final int HTPSTATE = 3;
	
	public GameStateManager() {
		
		gameStates = new GameState[ALLSTATE];
		
		currentState = MENUSTATE;
		loadState(currentState);
		
	}
	public void loadState(int state){
		if(state == MENUSTATE)
			gameStates[state] = new MenuState(this);
		if(state == LEVELSTATE)
			gameStates[state] = new LevelState(this);
		if(state == HTPSTATE){
			gameStates[state] = new HowToPlayState(this);
		}
		if(state == DEADSTATE){
			gameStates[state] = new DeadState(this);
		}
	}
	public void unloadState(int state){
		gameStates[state] = null;
	}
	
	public void setState(int state) {
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
	}
	
	public void update() {
		try {
			gameStates[currentState].update();
		} catch(Exception e) {}
	}
	
	public void draw(java.awt.Graphics2D g) {
		try {
			gameStates[currentState].draw(g);
		} catch(Exception e) {}
	}
	
	public void keyPressed(int k) {
		gameStates[currentState].keyPressed(k);
	}
	
	public void keyReleased(int k) {
		gameStates[currentState].keyReleased(k);
	}
	
}
