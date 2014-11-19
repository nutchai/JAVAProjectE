package GameState;

import TileMap.Background;

import java.awt.*;
import java.awt.event.KeyEvent;

public class DeadState extends GameState{

	private Background bg;
	
	public DeadState(GameStateManager gsm) {
		this.gsm = gsm;
		
		try {
			bg = new Background("/Backgrounds/gameover.png",1);	
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void init() {}
	
	public void update() {
		bg.update();
	}
	
	public void draw(Graphics2D g) {
		// draw bg
		bg.draw(g);

	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER) {
			gsm.setState(GameStateManager.MENUSTATE);
		}
	}

	public void keyReleased(int k) {}
	
}
