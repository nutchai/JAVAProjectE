package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import Enemy.Entity.Skull;
import Entity.*;
import Main.GamePanel;
import TileMap.TileMap;

public class LevelState extends GameState {
	private int killcount;
	private ArrayList<Enemy> enemies;
	private ArrayList<Enemy> enemies2;
	private ArrayList<Explosion> explosion;
	private TileMap tileMap;
	
	private Player player;
	private int monpow =1;
	private Skull skull;
	private Skull skull2;
	Point[] points = new Point[] {
			new Point(40, 160),
			new Point(710, 530),
			new Point(710, 160),
			new Point(40, 530),
			new Point(459, 400),
			new Point(700, 310),
			new Point(250, 189),
			new Point(340, 325),
			new Point(620, 148),
		};
	private long Timer = System.nanoTime();
	private long time;
	private long tick=1;
	Random rand = new Random();
	private int xran,yran;
	private HUD hud;
	private Font font;
	
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
		
		explosion = new ArrayList<Explosion>();
		enemies = new ArrayList<Enemy>();
		enemies2 = new ArrayList<Enemy>();
		skull = new Skull(tileMap);
		skull2 = new Skull(tileMap);
		skull.setPosition(330, 250);
		enemies.add(skull);
		hud = new HUD(player);
		// Font
					font = new Font("Arial Rounded MT Bold", Font.PLAIN, 20);
		
	}
	
	private void randomxy(){
		xran = rand.nextInt(8)+0;
		yran = rand.nextInt(8)+0;
	}
	
	public void update() {
		
		// update player
		player.update();
		
		// update monster
		for(int i = 0 ;i<enemies.size();i++){
			Enemy e = enemies.get(i);
			e.update();
			if(enemies.get(i).isDead()){
				randomxy();
				player.exp(1);
				killcount++;
				enemies.remove(i);
				i--;
				skull = new Skull(tileMap);
				skull.setPosition(points[xran].x, points[yran].y);
				skull.power(3);
				enemies.add(skull);
				explosion.add(new Explosion(e.getx(),e.gety()));
			}
		}
		for(int i = 0 ;i<enemies2.size();i++){
			Enemy f = enemies2.get(i);
			f.update();
			if(f.isDead()){
				randomxy();
				killcount++;
				enemies2.remove(i);
				i--;
				skull2 = new Skull(tileMap);
				explosion.add(new Explosion(f.getx(),f.gety()));
			}
		}
		
		// attack Enemy
		player.checkAttack(enemies);
		player.checkAttack(enemies2);
		
		// update player position to monster
		skull.getxy(player.getx(),player.gety());
		skull2.getxy(player.getx(),player.gety());
		
		// update explosion
		for(int i =0;i<explosion.size();i++){
			explosion.get(i).update();
			if(explosion.get(i).shouldRemove()){
				explosion.remove(i);
				i--;
			}
		}		

		// update monster position
		skull.getmonxy(skull.getx(),skull.gety());
		skull2.getmonxy(skull2.getx(),skull2.gety());
		
		// dead
		if (player.dead) {
			gsm.setState(GameStateManager.DEADSTATE);
		}
		
	}
	
	public void draw(Graphics2D g) {
		
		// clear screen
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		
		// draw tilemap
		tileMap.draw(g);
		
		// draw player
		player.draw(g);
		
		// draw explosions
				for(int i =0;i<explosion.size();i++){
					explosion.get(i).draw(g);
				}
				
		// draw monster
		for(int i=0 ;i<enemies.size();i++){
			enemies.get(i).draw(g);
		}
		for(int i =0 ;i<enemies2.size();i++){
			enemies2.get(i).draw(g);
		}		
		
		// draw hud
		hud.draw(g);
		g.setFont(font);
		g.drawString(""+killcount,350,80);

	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_LEFT) player.setLeft(true);
		if(k == KeyEvent.VK_RIGHT) player.setRight(true);
		if(k == KeyEvent.VK_UP) player.setUp(true);
		if(k == KeyEvent.VK_DOWN) player.setDown(true);
		if(k == KeyEvent.VK_D) player.setSlashing();
		if(k == KeyEvent.VK_1) player.setCastingMagic1();
		if(k == KeyEvent.VK_2) player.setCastingMagic2();
		if(k == KeyEvent.VK_3) player.setCastingMagic3();
		if(k == KeyEvent.VK_4) player.setCastingMagic4();
		if(k == KeyEvent.VK_5) player.setCastingMagic5();
		if(k == KeyEvent.VK_6) player.setCastingMagic6();
//		if(k == KeyEvent.VK_F) player.setFiring();
		//if(k == KeyEvent.VK_PAUSE) player.setPause(true);
	}
	
	public void keyReleased(int k) {
		if(k == KeyEvent.VK_LEFT) player.setLeft(false);
		if(k == KeyEvent.VK_RIGHT) player.setRight(false);
		if(k == KeyEvent.VK_UP) player.setUp(false);
		if(k == KeyEvent.VK_DOWN) player.setDown(false);
		/*if(k == KeyEvent.VK_PAUSE) player.setPause(false);*/
	}
	
}