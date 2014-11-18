package GameState;

import Main.GamePanel;
import TileMap.*;
import Entity.*;

import java.awt.*;
import java.awt.event.KeyEvent;

public class LevelState extends GameState {
	
	private TileMap tileMap;
	
	private Player player;
	
	public LevelState(GameStateManager gsm) {
		this.gsm = gsm;
		init();
	}
	
	public void init() {
		
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Backgrounds/background.png");
		tileMap.loadMap("/Maps/level1-1.map");
		tileMap.setPosition(0, 0);
		
		player = new Player(tileMap);
		player.setPosition(350, 350);
		
	}
	
	
	public void update() {
		
		// update player
		player.update();
		
	}
	
	public void draw(Graphics2D g) {
		
		// clear screen
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		
		// draw tilemap
		tileMap.draw(g);
		
		// draw player
		player.draw(g);
		
	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_LEFT) player.setLeft(true);
		if(k == KeyEvent.VK_RIGHT) player.setRight(true);
		if(k == KeyEvent.VK_UP) player.setUp(true);
		if(k == KeyEvent.VK_DOWN) player.setDown(true);
		if(k == KeyEvent.VK_D) player.setSlashing();
		/*if(k == KeyEvent.VK_1) player.setSkill1();
		if(k == KeyEvent.VK_2) player.setSkill2();
		if(k == KeyEvent.VK_3) player.setSkill3();
		if(k == KeyEvent.VK_4) player.setSkill4();
		if(k == KeyEvent.VK_5) player.setSkill5();
		if(k == KeyEvent.VK_6) player.setSkill6();
		if(k == KeyEvent.VK_PAUSE) player.setPause(true);*/	
	}
	
	public void keyReleased(int k) {
		if(k == KeyEvent.VK_LEFT) player.setLeft(false);
		if(k == KeyEvent.VK_RIGHT) player.setRight(false);
		if(k == KeyEvent.VK_UP) player.setUp(false);
		if(k == KeyEvent.VK_DOWN) player.setDown(false);
		/*if(k == KeyEvent.VK_PAUSE) player.setPause(false);*/
	}
	
}