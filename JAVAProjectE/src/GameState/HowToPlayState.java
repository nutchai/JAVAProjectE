package GameState;

import TileMap.Background;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class HowToPlayState extends GameState{
	
	private Background bg;
	
	private int currentChoice = 0;
	private BufferedImage[] images = {
			
	};
	
	public HowToPlayState(GameStateManager gsm) {
		this.gsm = gsm;
		
		try {
			bg = new Background("/Backgrounds/HTP1.png",1); // first page
		}
		catch(Exception e) {
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
		if(k == KeyEvent.VK_LEFT) {
			if(currentChoice == 0) {
				currentChoice = 1;
				bg = new Background("/Backgrounds/HTP2.png",1);
			}
			else {
				currentChoice--;
				bg = new Background("/Backgrounds/HTP1.png",1);
			}
		}
		if(k == KeyEvent.VK_RIGHT) {
			if(currentChoice == 0) {
				currentChoice++;
				bg = new Background("/Backgrounds/HTP2.png",1);
			}
			else {
				currentChoice = 0;
				bg = new Background("/Backgrounds/HTP1.png",1);
			}
		}
		if(k == KeyEvent.VK_ENTER) {
			gsm.setState(GameStateManager.MENUSTATE);
		}
	}

	public void keyReleased(int k) {}

}
