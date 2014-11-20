package Enemy.Entity;

import Entity.Enemy;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Entity.Animation;
import Entity.Player;
import TileMap.TileMap;

public class Skull extends Enemy{
	private final int[] numFrames = {
			8 ,5//how many of frame of each action
		};
	private int atkrange;
	private boolean isatk;
	private int ATK=1;
	private int WALK=0;
	private int x , y;
	private int mx,my;
	private int powup=0;
	private ArrayList<BufferedImage[]> sprites;
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
		health = maxHealth = 18+(6*this.power);
		damage = 10+(12*this.power);
		atkrange = 20;
		// load Sprites
		width = 50;
		height = 60;
		cwidth = 20;
		cheight = 20;
		try{
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/en1.png"));
			sprites = new ArrayList<BufferedImage[]>();
			for(int i = 0; i < 2; i++) { 			//play frame
				BufferedImage[] bi =
				new BufferedImage[numFrames[i]];
				for(int j = 0; j < numFrames[i]; j++) {
					if(i != ATK) {
						bi[j] = spritesheet.getSubimage(j * width,i * height,width,height);
					}
					else {
						bi[j] = spritesheet.getSubimage(j * width * 2,i * height,width * 2,height);
					}
				}
				
				sprites.add(bi);
				
			}
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
		currentAction = WALK;
		animation = new Animation();
		animation.setFrames(sprites.get(WALK));
		animation.setDelay(300);
		
		right = true;
		facingRight = true;
	}
	public Skull(TileMap tm,int power) {
		super(tm);
		this.power = power;
		moveSpeed = 0.8;
		maxSpeed = 0.8;
		health = maxHealth = 18+(6*this.power);
		damage = 10+(12*this.power);
		atkrange = 20;
		// load Sprites
		width = 50;
		height = 60;
		cwidth = 20;
		cheight = 20;
		try{
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/en1.png"));
			sprites = new ArrayList<BufferedImage[]>();
			for(int i = 0; i < 2; i++) { 			//play frame
				BufferedImage[] bi =
				new BufferedImage[numFrames[i]];
				for(int j = 0; j < numFrames[i]; j++) {
					if(i != ATK) {
						bi[j] = spritesheet.getSubimage(j * width,i * height,width,height);
					}
					else {
						bi[j] = spritesheet.getSubimage(j * width * 2,i * height,width * 2,height);
					}
				}
				
				sprites.add(bi);
				
			}
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
		currentAction = WALK;
		animation = new Animation();
		animation.setFrames(sprites.get(WALK));
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
		if(currentAction == ATK) {
			if(animation.hasPlayedOnce()) isatk = false;
		}
		//update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp,ytemp);
		animation.update();
		
		 if((mx-x<40&&mx-x>-40&&my-y<20&&my-y>-20)) {
				if(currentAction != ATK) {
					currentAction = ATK;
					isatk=true;
					animation.setFrames(sprites.get(ATK));
					animation.setDelay(160);
					width = 80;
					maxSpeed =0;
				}
			}
		else{
			if(currentAction != WALK) {
				currentAction = WALK;
				animation.setFrames(sprites.get(WALK));
				animation.setDelay(160);
				width = 50;
				maxSpeed = 0.8;
			}
		}
	}
	public void draw(Graphics2D g){
		setMapPosition();
		super.draw(g);
		
	}
}
