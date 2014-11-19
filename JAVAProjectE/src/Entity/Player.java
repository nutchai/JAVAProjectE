package Entity;

import Entity.Animation;
import Entity.MapObject;
import TileMap.*;

import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Player extends MapObject {
	
	// player stuff
	private int health;
	private int maxHealth;
//	private int mana;
//	private int maxMana;
	private boolean dead;
	private boolean flinching;
	private long flinchTimer;
	
	// Magic 1-6
//	private boolean casting;
//	private boolean castingMagic1;
//	private boolean castingMagic2;
//	private boolean castingMagic3;
//	private boolean castingMagic4;
//	private boolean castingMagic5;
//	private boolean castingMagic6;
//	private int castCostMagic1;
//	private int castCostMagic2;
//	private int castCostMagic3;
//	private int castCostMagic4;
//	private int castCostMagic5;
//	private int castCostMagic6;
//	private int damageMagic1;
//	private int damageMagic2;
//	private int damageMagic3;
//	private int damageMagic4;
//	private int damageMagic5;
//	private int healMagic6;
//	private ArrayList<Skills> magic;
	
	// Slash
	private boolean slashing;
	private int slashDamage;
	private int slashRange;
	
	// animations
	private ArrayList<BufferedImage[]> sprites;
	private final int[] numFrames = {
		4 ,6 ,4 ,2 //how many of frame of each action
	};
	
	// animation actions
	private static final int IDLE = 0;
	private static final int WALKING = 1;
	private static final int SLASHING = 2;
	//private static final int CASTING = 3;
	private static final int FIREBALL = 3;
	
	// fireball
	private int fire;
	private int maxFire;
	private boolean firing;
	private int fireCost;
	private int fireBallDamage;
	private ArrayList<FireBall> fireBalls;
	
	//private HashMap<String, AudioPlayer> sfx;
	
	public Player(TileMap tm) {
		
		super(tm);
		
		width = 50;
		height = 60;
		cwidth = 20;
		cheight = 20;
		
		moveSpeed = 1.6;
		maxSpeed = 1.6;
		stopSpeed = 0.4;
		
		facingRight = true;
		
		health = maxHealth = 200;
		//mana = maxMana = 200;
		
		fire = maxFire = 2500;
		fireCost = 200;
		fireBallDamage = 5;
		fireBalls = new ArrayList<FireBall>();
		
//		castCostMagic1 = 20;
//		damageMagic1 = 25;
//		
//		castCostMagic2 = 40;
//		damageMagic2 = 50;
//		
//		castCostMagic3 = 100;
//		damageMagic3 = 130;
//		
//		castCostMagic4 = 200;
//		damageMagic4 = 300;
//		
//		castCostMagic5 = 150;
//		damageMagic5 = 250;
//		
//		castCostMagic6 = 50;
//		healMagic6 = 25;
//		
//		magics = new ArrayList<Skills>();
		
		slashDamage = 10;
		slashRange = 55;
		
		
		
		// load sprites
		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Player/c1.png"));
			sprites = new ArrayList<BufferedImage[]>();
			for(int i = 0; i < 4; i++) { 			//play frame
				BufferedImage[] bi =
				new BufferedImage[numFrames[i]];
				for(int j = 0; j < numFrames[i]; j++) {
					if(i != SLASHING) {
						bi[j] = spritesheet.getSubimage(j * width,i * height,width,height);
					}
					else {
						bi[j] = spritesheet.getSubimage(j * width * 2,i * height,width * 2,height);
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
	//public int getMana() { return mana; }
	//public int getMaxMana() { return maxMana; }
	public int getFire() { return fire; }
	public int getMaxFire() { return maxFire; }
	
//	public void setCastingMagic1() { 
//		casting = true;
//		castingMagic1 = true;
//	}
//	public void setCastingMagic2() {
//		casting = true;
//		castingMagic2 = true;
//	}
//	public void setCastingMagic3() {
//		casting = true;
//		castingMagic3 = true;
//	}
//	public void setCastingMagic4() {
//		casting = true;
//		castingMagic4 = true;
//	}
//	public void setCastingMagic5() {
//		casting = true;
//		castingMagic5 = true;
//	}
//	public void setCastingMagic6() {
//		casting = true;
//		castingMagic6 = true;
//	}
	public void setSlashing() {
		slashing = true;
	}
	public void setFiring() {
		firing = true;
	}

	public void checkAttack(ArrayList<Enemy> enemies) {
		
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
//			for(int j = 0; j < skills.size(); j++) {
//				if(skills.get(j).intersects(e)) {
//					e.hit(spellDamage);
//					skills.get(j).setHit();
//					break;
//				}
//			}
			
			// check enemy collision
			if(intersects(e)) {
				hit(e.getDamage());
				
			}
			
		}
		
	}
	
	public void hit(int damage) {
		if(flinching) return;
		if(facingRight){dx-=7;}
		else{dx+=7;}
		health -= damage;
		if(health < 0) health = 0;
		if(health == 0) dead = true;
		flinching = true;
		flinchTimer = System.nanoTime();
	} 
	
	//cannot move while attacking
	//----------
	
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
			if (currentAction == SLASHING) {
				maxSpeed =0;
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
//		if(currentAction == CASTING) {
//			castingMagic1 = false;
//			castingMagic2 = false;
//			castingMagic3 = false;
//			castingMagic4 = false;
//			castingMagic5 = false;
//			castingMagic6 = false;
//			if(animation.hasPlayedOnce()) casting = false;
//		}
		if (currentAction == FIREBALL) {
			if(animation.hasPlayedOnce()) firing = false;
		}
		
		//Skills attack
//		mana += 1; // regen mana
//		if (mana > maxMana) mana = maxMana;
//		if (casting && currentAction != CASTING) {
//			if (casting == castingMagic1) {
//				currentMagic = 0;
//				System.out.println("check case castingMagic1");
//				if (mana > castCostMagic1) {
//					mana -= castCostMagic1;
//					Skills mg = new Skills(tileMap,facingRight,currentMagic);
//					mg.setPosition(x,y);
//					skills.add(mg);
//				}
//			}
//			else if (casting == castingMagic2) {
//				currentMagic = 1;
//				System.out.println("check case castingMagic2");
//				if (mana > castCostMagic2) {
//					mana -= castCostMagic2;
//					Skills mg = new Skills(tileMap,facingRight,currentMagic);
//					mg.setPosition(x,y-height);
//					skills.add(mg);
//				}
//			}
//			else if (casting == castingMagic3) {
//				currentMagic = 2;
//				System.out.println("check case castingMagic3");
//				if (mana > castCostMagic3) {
//					mana -= castCostMagic3;
//					Skills mg = new Skills(tileMap,facingRight,currentMagic);
//					mg.setPosition(x,y-height);
//					skills.add(mg);
//				}
//			}
//			else if (casting == castingMagic4) {
//				currentMagic = 3;
//				System.out.println("check case castingMagic4");
//				if (mana > castCostMagic4) {
//					mana -= castCostMagic4;
//					Skills mg = new Skills(tileMap,facingRight,currentMagic);
//					mg.setPosition(x,y-height);
//					skills.add(mg);
//				}
//			}
//			else if (casting == castingMagic5) {
//				currentMagic = 4;
//				System.out.println("check case castingMagic5");
//				if (mana > castCostMagic5) {
//					mana -= castCostMagic5;
//					Skills mg = new Skills(tileMap,facingRight,currentMagic);
//					mg.setPosition(x,y-height);
//					skills.add(mg);
//				}
//			}
//			else if (casting == castingMagic6) {
//				currentMagic = 5;
//				System.out.println("check case castingMagic6");
//				if (mana > castCostMagic6) {
//					mana -= castCostMagic6;
//					Skills mg = new Skills(tileMap,facingRight,currentMagic);
//					mg.setPosition(x,y);
//					skills.add(mg);
//				}
//			}
//		}
		
		//fireball attack
		fire += 1;
		if(fire > maxFire) fire = maxFire;
		if(firing && currentAction!= FIREBALL) {
			if(fire > fireCost) {
				fire -= fireCost;
				FireBall fb = new FireBall(tileMap, facingRight);
				fb.setPosition(x,y-25);
				fireBalls.add(fb);
			}
		}
		
		// update fireballs
		for(int i=0;i<fireBalls.size();i++) {
			fireBalls.get(i).update();
			if(fireBalls.get(i).shouldRemove()) {
				fireBalls.remove(i);
				i--;
			}
		}
		
		//update skills
//		for(int i=0;i<skills.size();i++) {
//			skills.get(i).update();
//			if(skills.get(i).shouldRemove()) {
//				skills.remove(i);
//				i--;
//			}
//		}
		
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
				animation.setDelay(90);
				width = 100;
				maxSpeed =0;
			}
		}
		else if(firing) {
			if(currentAction != FIREBALL) {
				currentAction = FIREBALL;
				animation.setFrames(sprites.get(FIREBALL));
				animation.setDelay(100);
				width = 50;
			}
		}
//		else if (castingMagic1) {
//			if(currentAction != CASTING) {
//				currentAction = CASTING;
//				animation.setFrames(sprites.get(CASTING));
//				animation.setDelay(300);
//				width = 50;
//			}
//		}
//		else if (castingMagic2) {
//			if(currentAction != CASTING) {
//				currentAction = CASTING;
//				animation.setFrames(sprites.get(CASTING));
//				animation.setDelay(40);
//				width = 50;
//			}
//		}
//		else if (castingMagic3) {
//			if(currentAction != CASTING) {
//				currentAction = CASTING;
//				animation.setFrames(sprites.get(CASTING));
//				animation.setDelay(50);
//				width = 50;
//			}
//		}
//		else if (castingMagic4) {
//			if(currentAction != CASTING) {
//				currentAction = CASTING;
//				animation.setFrames(sprites.get(CASTING));
//				animation.setDelay(200);
//				width = 50;
//			}
//		}
//		else if (castingMagic5) {
//			if(currentAction != CASTING) {
//				currentAction = CASTING;
//				animation.setFrames(sprites.get(CASTING));
//				animation.setDelay(150);
//				width = 50;
//			}
//		}
//		else if (castingMagic6) {
//			if(currentAction != CASTING) {
//				currentAction = CASTING;
//				animation.setFrames(sprites.get(CASTING));
//				animation.setDelay(25);
//				width = 50;
//				}
//		}
		else if(left || right || up || down) {
			if(currentAction != WALKING) {
				currentAction = WALKING;
				animation.setFrames(sprites.get(WALKING));
				animation.setDelay(160);
				width = 50;
				maxSpeed =1.6;
			}
		}
		else {
			if(currentAction != IDLE) {
				currentAction = IDLE;
				animation.setFrames(sprites.get(IDLE));
				animation.setDelay(400);
				width = 50;
				maxSpeed =1.6;
			}
		}
		
		animation.update();
		// set direction
		if(currentAction != SLASHING
				&& currentAction != FIREBALL //CASTING
		) {
			if(right) facingRight = true;
			if(left) facingRight = false;
		}
	}
	
	public void draw(Graphics2D g) {
		
		setMapPosition();
		
		// draw skills
//		for(int i = 0; i < skills.size(); i++) {
//			skills.get(i).draw(g);
//		}
		
		// draw fireballs
		for(int i=0;i<fireBalls.size();i++) {
			fireBalls.get(i).draw(g);
		}
		
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