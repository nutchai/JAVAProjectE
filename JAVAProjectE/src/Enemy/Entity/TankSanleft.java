package Enemy.Entity;

import Entity.Enemy;
import Entity.Player;
import Enemy.Entity.FireBall;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Entity.Animation;
import TileMap.TileMap;

public class TankSanleft extends Enemy{
	private final int[] numFrames = {
			6 ,20//how many of frame of each action
		};
	private ArrayList<FireBall> bullet;
	private long ShootTimer;
	private long shoot=1;
	private boolean isatk;
	private int ATK=1;
	private int WALK=0;
	private ArrayList<BufferedImage[]> sprites;
	public FireBall getbullet(FireBall fb){
		return fb;
	}
	public TankSanleft(TileMap tm) {
		super(tm);
		bullet = new ArrayList<FireBall>();
		moveSpeed = 0.0;
		maxSpeed = 0.0;
		ShootTimer = System.nanoTime();
		health = maxHealth = 500000;
		damage = 10;
		// load Sprites
		width = 50;
		height = 60;
		cwidth = 20;
		cheight = 20;
		try{
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/es.png"));
			sprites = new ArrayList<BufferedImage[]>();
			for(int i = 0; i < 2; i++) { 			//play frame
				BufferedImage[] bi =
				new BufferedImage[numFrames[i]];
				for(int j = 0; j < numFrames[i]; j++) {
					if(i != ATK) {
						bi[j] = spritesheet.getSubimage(j * width,i * height,width,height);
					}
					else {
						bi[j] = spritesheet.getSubimage(j * width * 2,i * height,width,height);
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
		animation.setDelay(100);
		
		right = false;
		facingRight = false;
	}
	public void update(){
		shoot = ((System.nanoTime()-ShootTimer)/1000000000)+1;
		if(currentAction == ATK) {
			if(animation.hasPlayedOnce());
		}
		health+=5000;
		//update position
		checkTileMapCollision();
		setPosition(xtemp,ytemp);
		
		animation.update();
		 if(shoot%5==0) {
				if(currentAction != WALK) {
					currentAction = WALK;
					isatk=true;
					animation.setFrames(sprites.get(WALK));
					animation.setDelay(140);
					width = 70;
				}
				
			}
		else{
			if(currentAction != WALK) {
				isatk=false;
				currentAction = WALK;
				animation.setDelay(100);
				width = 50;
			}
		}
		 /// SHOOT!
		 if (isatk) {
					FireBall fb = new FireBall(tileMap);
					fb.setPosition(x,y);
					bullet.add(fb);
					isatk = false;
					}
		  //bullet update
			for(int i=0;i<bullet.size();i++) {
				bullet.get(i).update();
				if(bullet.get(i).shouldRemove()) {
					bullet.remove(i);
					i--;
				}
			}
			
	}
	
	
	public void draw(Graphics2D g){
		setMapPosition();
		for(int i = 0; i < bullet.size(); i++) {
			bullet.get(i).draw(g);
		}
		super.draw(g);
		
	}
}
