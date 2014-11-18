package Enemy.Entity;

import Entity.Enemy;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Entity.Animation;
import Entity.Player;
import TileMap.TileMap;

public class Skull extends Enemy{

	private BufferedImage[] sprites;
	
	public Skull(TileMap tm) {
		super(tm);
		
		moveSpeed = 0.8;
		maxSpeed = 0.8;
		
		health = maxHealth = 20;
		damage = 10;
		// load Sprites
		width = 50;
		height = 60;
		cwidth = 20;
		cheight = 20;
		try{
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/en1.png"));
			sprites = new BufferedImage[2];
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
		if(left) {
			dx -= moveSpeed;
			if(dx < -maxSpeed) {
				dx = -maxSpeed;
			}
		}
		else if(right) {
			dx += moveSpeed;
			if(dx > maxSpeed) {
				dx = maxSpeed;
			}
		}
//		else if(up) {
//			dy -= moveSpeed;
//			if(dy < -maxSpeed) {
//				dy = -maxSpeed;
//			}
//		}
//		else if(down) {
//			dy += moveSpeed;
//			if(dy > maxSpeed) {
//				dy = maxSpeed;
//			}
//		}
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
