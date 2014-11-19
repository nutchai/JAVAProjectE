package Enemy.Entity;

import Entity.Enemy;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Entity.Animation;
import Entity.Player;
import TileMap.TileMap;

public class Skull extends Enemy{
	private final int[] numFrames = {
			8 ,5 //how many of frame of each action
		};
	private int ATK=1;
	private int x , y;
	private int mx,my;
	private BufferedImage[] sprites;
	public void getxy(int x,int y){
		this.x = x;
		this.y = y;
	}
	public void getmonxy(int mx,int my){
		this.mx = mx;
		this.my = my;
	}
	public Skull(TileMap tm) {
		super(tm);
		
		moveSpeed = 0.8;
		maxSpeed = 0.8;
		
		health = maxHealth = 1;
		damage = 10;
		// load Sprites
		width = 50;
		height = 60;
		cwidth = 20;
		cheight = 20;
		try{
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/en1.png"));
			sprites = new BufferedImage[8];
		{
			for(int i=0;i<sprites.length;i++){
				sprites[i] = spritesheet.getSubimage(i*width, 0, width, height);
			}
		}
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(300);
		
		right = true;
		facingRight = true;
	}
	
	public void getNextPosition(){
		// movement
		if(mx>x) {
			dx -= moveSpeed;
			if(dx < -maxSpeed) {
				dx = -maxSpeed;
			}
			facingRight = false;
		}
		else if(mx<x) {
			dx += moveSpeed;
			if(dx > maxSpeed) {
				dx = maxSpeed;
			}
			facingRight = true;
		}
		if(my>y) {
			dy -= moveSpeed;
			if(dy < -maxSpeed) {
				dy = -maxSpeed;
			}
		}
		else if(my<y) {
			dy += moveSpeed;
			if(dy > maxSpeed) {
				dy = maxSpeed;
			}
		}
		animation.update();
	}
	public void update(){
		//update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp,ytemp);
		animation.update();
	}
	
	public void draw(Graphics2D g){
		setMapPosition();
		super.draw(g);
		
	}
}
