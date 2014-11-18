package Entity;

import TileMap.*;
//import Audio.AudioPlayer;

import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Player extends MapObject {
	
	// player stuff
	private int health;
	private int maxHealth;
	private int mana;
	private int maxMana;
	private boolean dead;
	private boolean flinching;
	private long flinchTimer;
	
	
	// Magic 1-6
	private boolean casting;
	private int castCost;
	private boolean spelling;
	private int spellDamage;
	//private ArrayList<Skills> skills;
	
	// Slash
	private boolean slashing;
	private int slashDamage;
	private int slashRange;
	
	//Cancel Casting
	private boolean cancelcasting;
	
	// animations
	private ArrayList<BufferedImage[]> sprites;
	private final int[] numFrames = {
		1 ,4 ,9 ,6 ,7 ,5 //how many of frame of each action
	};
	
	// animation actions
	private static final int IDLE = 0;
	private static final int WALKING = 1;
	private static final int SLASHING = 2;
	private static final int CASTING = 3;
	private static final int SPELLING = 4;
	private static final int MAGIC1 = 5;
	/*private static final int MAGIC2 = 6;
	private static final int MAGIC3 = 7;
	private static final int MAGIC4 = 8;
	private static final int MAGIC5 = 9;
	private static final int MAGIC6 = 10;*/
	
	
	//private HashMap<String, AudioPlayer> sfx;
	
	public Player(TileMap tm) {
		
		super(tm);
		
		width = 60;
		height = 60;
		cwidth = 20;
		cheight = 20;
		
		moveSpeed = 0.3;
		maxSpeed = 1.6;
		stopSpeed = 0.4;
		
		facingRight = true;
		
		health = maxHealth = 200;
		mana = maxMana = 200;
		
		/*fireCost = 200;
		fireBallDamage = 5;
		fireBalls = new ArrayList<FireBall>(); */ //magic
		
		slashDamage = 10;
		slashRange = 40;
		
		// load sprites
		try {
			
			BufferedImage spritesheet = ImageIO.read(
				getClass().getResourceAsStream(
					"/Sprites/Player/Character.gif"
				)
			);
			
			sprites = new ArrayList<BufferedImage[]>();
			for(int i = 0; i < 6; i++) { //play frame!!!!!
				
				BufferedImage[] bi =
					new BufferedImage[numFrames[i]];
				
				for(int j = 0; j < numFrames[i]; j++) {
					
					if(i != SLASHING) {
						bi[j] = spritesheet.getSubimage(
								j * width,
								i * height,
								width,
								height
						);
					}
					else {
						bi[j] = spritesheet.getSubimage(
								j * width * 2,
								i * height,
								width * 2,
								height
						);
					}
					
				}
				
				sprites.add(bi);
				
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		animation = new Animation();
		currentAction = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelay(400);
		
		/*sfx = new HashMap<String, AudioPlayer>();
		sfx.put("jump", new AudioPlayer("/SFX/jump.mp3"));
		sfx.put("scratch", new AudioPlayer("/SFX/scratch.mp3"));*/
		
	}
	
	public int getHealth() { return health; }
	public int getMaxHealth() { return maxHealth; }
	public int getMana() { return mana; }
	public int getMaxMana() { return maxMana; }
	
	public void setCasting() { 
		casting = true;
	}
	public void setSlashing() {
		slashing = true;
	}
	public void setCancelCasting(boolean b) { 
		cancelcasting = b;
	}
	
	/*public void checkAttack(ArrayList<Enemy> enemies) {
		
		// loop through enemies
		for(int i = 0; i < enemies.size(); i++) {
			
			Enemy e = enemies.get(i);
			
			// slash attack
			if(slashing) {
				if(facingRight) {
					if(
						e.getx() > x &&
						e.getx() < x + slashRange && 
						e.gety() > y - height / 2 &&
						e.gety() < y + height / 2
					) {
						e.hit(slashDamage);
					}
				}
				else {
					if(
						e.getx() < x &&
						e.getx() > x - slashRange &&
						e.gety() > y - height / 2 &&
						e.gety() < y + height / 2
					) {
						e.hit(slashDamage);
					}
				}
			}
			
			// skills
			for(int j = 0; j < skills.size(); j++) {
				if(skills.get(j).intersects(e)) {
					e.hit(spellDamage);
					skills.get(j).setHit();
					break;
				}
			}
			
			// check enemy collision
			if(intersects(e)) {
				hit(e.getDamage());
			}
			
		}
		
	}
	
	public void hit(int damage) {
		if(flinching) return;
		health -= damage;
		if(health < 0) health = 0;
		if(health == 0) dead = true;
		flinching = true;
		flinchTimer = System.nanoTime();
	} */
	
	private void getNextPosition() {
		
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
		else if(up) {
			dy -= moveSpeed;
			if(dy < -maxSpeed) {
				dy = -maxSpeed;
			}
		}
		else if(down) {
			dy += moveSpeed;
			if(dy > maxSpeed) {
				dy = maxSpeed;
			}
		}
		else {
			if(dx > 0) {
				dx -= stopSpeed;
				if(dx < 0) {
					dx = 0;
				}
			}
			else if(dx < 0) {
				dx += stopSpeed;
				if(dx > 0) {
					dx = 0;
				}
			}
			else if(dy > 0) {
				dy -= stopSpeed;
				if(dy < 0) {
					dy = 0;
				}
			}
			else if(dy < 0) {
				dy += stopSpeed;
				if(dy > 0) {
					dy = 0;
				}
			}
		}
		
	}
	
	public void update() {
		
		// update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		// check attack has stopped
		if(currentAction == SLASHING) {
			if(animation.hasPlayedOnce()) slashing = false;
		}
		if(currentAction == CASTING || currentAction == SPELLING) {
			if(animation.hasPlayedOnce()) casting = false;
		}
		
		//Skills attack
		
		//update skills
		
		// check done flinching
		if(flinching) {
			long elapsed =
				(System.nanoTime() - flinchTimer) / 1000000;
			if(elapsed > 1000) {
				flinching = false;
			}
		}
		
		// set animation
		if(slashing) {
			if(currentAction != SLASHING) {
				//sfx.get("scratch").play();
				currentAction = SLASHING;
				animation.setFrames(sprites.get(SLASHING));
				animation.setDelay(50);
				width = 60;
			}
		}
		else if(casting) {
			if(currentAction != CASTING) {
				currentAction = CASTING;
				animation.setFrames(sprites.get(CASTING));
				animation.setDelay(100);
				width = 30;
				/*if (currentAction != SPELLING) {
					currentAction = SPELLING;
					//if (skills ==) {
					animation.setFrame(sprites.get(SPELLING);
					animation.setDelay(100);
					width = 30
					}
				}*/
			}
		}
		else if(left || right || up || down) {
			if(currentAction != WALKING) {
				currentAction = WALKING;
				animation.setFrames(sprites.get(WALKING));
				animation.setDelay(160);
				width = 60;
			}
		}
		else {
			if(currentAction != IDLE) {
				currentAction = IDLE;
				animation.setFrames(sprites.get(IDLE));
				animation.setDelay(400);
				width = 60;
			}
		}
		
		animation.update();
		
		// set direction
		if(currentAction != SLASHING && currentAction != CASTING) {
			if(right) facingRight = true;
			if(left) facingRight = false;
		}
		
	}
	
	public void draw(Graphics2D g) {
		
		setMapPosition();
		
		// draw skills
		/*for(int i = 0; i < skills.size(); i++) {
			skills.get(i).draw(g);
		}*/
		
		// draw player
		if(flinching) {
			long elapsed =
				(System.nanoTime() - flinchTimer) / 1000000;
			if(elapsed / 100 % 2 == 0) {
				return;
			}
		}
		
		super.draw(g);
		
	}
	
}

















