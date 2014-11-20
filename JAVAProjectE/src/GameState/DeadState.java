package GameState;

import TileMap.Background;
import Audio.AudioPlayer;
import GameState.LevelState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class DeadState extends GameState{

	LevelState ls;
	
	private Background bg;
	
	private Font font;
	
	private AudioPlayer bgMusic;
	
	public DeadState(GameStateManager gsm) {

		bgMusic = new AudioPlayer("/Sounds/deadState.wav");
		bgMusic.play();
		
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
		
		font = new Font("Arial Rounded MT Bold", Font.PLAIN, 50);
		
		System.out.println(ls.killcount);
		
		g.setFont(font);
		g.setColor(Color.GREEN);
		g.drawString(""+ls.killcount,350,200);

	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER) {
			gsm.setState(GameStateManager.MENUSTATE);
		}
	}

	public void keyReleased(int k) {}
	
}
