package Entity;

import Audio.AudioPlayer;
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
	private double health;
	private double maxHealth;
	private double mana;
	private double maxMana;
	private int level;
	public boolean dead;
	private boolean isflinch;
	private boolean flinching;
	private long flinchTimer;
	private int exp;
	
	// Sound
	private HashMap<String,AudioPlayer> sfx; 
	
	// Magic 1-6
	private boolean casting;
	private boolean castingMagic1;
	private boolean castingMagic2;
	private boolean castingMagic3;
	private boolean castingMagic4;
	private boolean castingMagic5;
	private boolean castingMagic6;
	private int castCostMagic1;
	private int castCostMagic2;
	private int castCostMagic3;
	private int castCostMagic4;
	private int castCostMagic5;
	private int castCostMagic6;
	private int damageMagic1;
	private int damageMagic2;
	private int damageMagic3;
	private int damageMagic4;
	private int damageMagic5;
	private int healMagic6;
	private int stackMagic1;
	private int stackMagic2;
	private int stackMagic3;
	private int stackMagic4;
	private int stackMagic5;
	private int stackMagic6;
	private int magic1lv;
	private int magic2lv;
	private int magic3lv;
	private int magic4lv;
	private int magic5lv;
	private int magic6lv;
	private ArrayList<Skills> magics;
	
	// Slash
	private boolean slashing;
	private int slashDamage;
	private int slashRange;
	
	// animations
	private ArrayList<BufferedImage[]> sprites;
	private final int[] numFrames = {
		4 ,6 ,4 ,2 ,1//how many of frame of each action
	};
	
	// animation actions
	private static final int IDLE = 0;
	private static final int WALKING = 1;
	private static final int SLASHING = 2;
	private static final int CASTING = 3;
	private static final int FLINCH = 4;
	
	private Font font;
	
	public void exp(int gain){
		exp+=gain;
	}
	
	public Player(TileMap tm) {
		
		super(tm);
		
		width = 50;
		height = 60;
		cwidth = 20;
		cheight = 20;
		
		moveSpeed = 1.6;
		maxSpeed = 1.6;
		stopSpeed = 0.4;
		magic1lv=1;
		magic2lv=1;
		magic3lv=1;
		magic4lv=1;
		magic5lv=1;
		magic6lv=1;
		
		facingRight = true;
		
		health = maxHealth = 20;
		mana = maxMana = 200;
		
//		fire = maxFire = 2500;
//		fireCost = 200;
//		fireBallDamage = 5;
//		fireBalls = new ArrayList<FireBall>();
		
		castCostMagic1 = 20;
		damageMagic1 = 25;	
		castCostMagic2 = 40;
		damageMagic2 = 50;
		castCostMagic3 = 100;
		damageMagic3 = 100;
		castCostMagic4 = 250;
		damageMagic4 = 375;
		castCostMagic5 = 300;
		damageMagic5 = 450;
		castCostMagic6 = 50;
		healMagic6 = 75;
		magics = new ArrayList<Skills>();
		
		stackMagic1 = 0;
		stackMagic2 = 0;
		stackMagic3 = 0;
		stackMagic4 = 0;
		stackMagic5 = 0;
		stackMagic6 = 0;
		
		slashDamage = 20;
		slashRange = 60;
		
		level = 1;
		exp = 0;
		
		// load sprites
		try {
			
			BufferedImage spritesheet = ImageIO.read(
				getClass().getResourceAsStream(
					"/Sprites/Player/c1.png"
				)
			);
			
			sprites = new ArrayList<BufferedImage[]>();
			for(int i = 0; i < 5; i++) { 			//play frame
				
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
			
			// Font
			font = new Font("Arial Rounded MT Bold", Font.PLAIN, 20);
			sfx = new HashMap<String,AudioPlayer>();
			sfx.put("Slash",new AudioPlayer("/SFX/Slash.mp3"));
			sfx.put("Skill1",new AudioPlayer("/Sounds/skill1.wav"));
			sfx.put("Skill2",new AudioPlayer("/Sounds/skill2.wav"));
			sfx.put("Skill3",new AudioPlayer("/Sounds/skill3.wav"));
			sfx.put("Skill4",new AudioPlayer("/Sounds/skill4.wav"));
			sfx.put("Skill5",new AudioPlayer("/Sounds/skill5.wav"));
			sfx.put("Skill6",new AudioPlayer("/Sounds/skill6.wav"));
			sfx.put("cantCast",new AudioPlayer("/Sounds/cantCast.wav"));
			sfx.put("deadState",new AudioPlayer("/Sounds/deadState.wav"));
			sfx.put("playerDamaged",new AudioPlayer("/Sounds/playerDamaged.wav"));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		animation = new Animation();
		currentAction = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelay(400);
		
	}
	
	public double getHealth() { return health; }
	public double getMaxHealth() { return maxHealth; }
	public double getMana() { return mana; }
	public double getMaxMana() { return maxMana; }
	
	public void setCastingMagic1() { 
		if(slashing==false){
		casting = true;
		castingMagic1 = true;}
	}
	public void setCastingMagic2() {
		if(slashing==false){
		casting = true;
		castingMagic2 = true;}
	}
	public void setCastingMagic3() {
		if(slashing==false){
		casting = true;
		castingMagic3 = true;}
	}
	public void setCastingMagic4() {
		if(slashing==false){
		casting = true;
		castingMagic4 = true;}
	}
	public void setCastingMagic5() {
		if(slashing==false){
		casting = true;
		castingMagic5 = true;}
	}
	public void setCastingMagic6() {
		if(slashing==false){
		casting = true;
		castingMagic6 = true;}
	}
	public void setSlashing() {
		if(!castingMagic6&&!castingMagic5&&!castingMagic4&&!castingMagic3&&!castingMagic2&&!castingMagic1)
		slashing = true;
	}
//	public void setFiring() {
//		firing = true;
//	}

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
			for(int j = 0; j < magics.size(); j++) {
				if(castingMagic1){
					if(magics.get(j).intersects(e)) {
					e.hit(damageMagic1);
					magics.get(j).setHit();
					break;
				}}
				else if(castingMagic2){
					if(magics.get(j).intersects(e)) {
					e.hit(damageMagic2);
					magics.get(j).setHit();
					break;
				}}
				else if(castingMagic3){
					if(magics.get(j).intersects(e)) {
					e.hit(damageMagic3);
					magics.get(j).setHit();
					break;
				}}
				else if(castingMagic4){
					if(magics.get(j).intersects(e)) {
					e.hit(damageMagic4);
					magics.get(j).setHit();
					break;
				}}
				else if(castingMagic5){
					if(magics.get(j).intersects(e)) {
					e.hit(damageMagic5);
					magics.get(j).setHit();
					break;
				}}
			}
			
			// check enemy collision
			if(intersects(e)) {
				hit(e.getDamage());
			}
			
		}
		
	}
	
	public void hit(int damage) {
		if(flinching) return;
		isflinch = true;
		if(facingRight){dx-=7;}
		else{dx+=7;}
		health -= damage;
		sfx.get("playerDamaged").play();
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
			if(up) {
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
		}
		else if(right) {
			dx += moveSpeed;
			if(dx > maxSpeed) {
				dx = maxSpeed;
			}
			if(up) {
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
		}
		else if(up) {
			dy -= moveSpeed;
			if(dy < -maxSpeed) {
				dy = -maxSpeed;
				dx = 0;
			}
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
		}
		else if(down) {
			dy += moveSpeed;
			if(dy > maxSpeed) {
				dy = maxSpeed;
			}
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
		if (currentAction == SLASHING || currentAction == CASTING) {
			dx = 0;
			dy = 0;
		}
		
	}
	
	public void update() {
		
		// update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		//dead
//		if (dead) {}
		
		// check attack has stopped
		if(currentAction == SLASHING) {
			if(animation.hasPlayedOnce()) slashing = false;
		}
		
		if(currentAction == CASTING) {
			if(animation.hasPlayedOnce()) { 
				casting = false;
				castingMagic1 = false;
				castingMagic2 = false;
				castingMagic3 = false;
				castingMagic4 = false;
				castingMagic5 = false;
				castingMagic6 = false;
			}
		}
		
//		if (currentAction == FIREBALL) {
//			if(animation.hasPlayedOnce()) firing = false;
//		}
		
		//Skills attack
		mana += 0.05; // regen mana
		if (mana > maxMana) { mana = maxMana; }
		if (casting && currentAction != CASTING) {
			if (casting == castingMagic1) {
				currentMagic = 0;
				if (mana > castCostMagic1) {
					sfx.get("Skill1").play();
					mana -= castCostMagic1;
					stackMagic1 += 1;
					if (stackMagic1 == 10) {
						magic1lv+=1;
						stackMagic1 = 0;
						castCostMagic1 += 4;
						damageMagic1 += 8;
					}
					Skills mg = new Skills(tileMap,facingRight,currentMagic);
					mg.setPosition(x,y);
					magics.add(mg);
				}
				else sfx.get("cantCost").play();
			}
			else if (casting == castingMagic2) {
				currentMagic = 1;
				if (mana > castCostMagic2) {
					sfx.get("Skill2").play();
					mana -= castCostMagic2;
					stackMagic2 += 1;
					if (stackMagic2 == 10) {
						magic2lv+=1;
						stackMagic2 = 0;
						castCostMagic2 += 8;
						damageMagic2 += 20;
					}
					Skills mg = new Skills(tileMap,facingRight,currentMagic);
					if (facingRight) mg.setPosition(x+100,y-(height/2));
					else mg.setPosition(x-100,y-(height/2));
					magics.add(mg);
				}
				else sfx.get("cantCost").play();
			}
			else if (casting == castingMagic3) {
				currentMagic = 2;
				if (mana > castCostMagic3) {
					sfx.get("Skill3").play();
					mana -= castCostMagic3;
					stackMagic3 += 1;
					if (stackMagic3 == 10) {
						magic3lv+=1;
						stackMagic3 = 0;
						castCostMagic3 += 6;
						damageMagic3 += 10;
					}
					Skills mg = new Skills(tileMap,facingRight,currentMagic);
					if (facingRight) mg.setPosition(x+height,y);
					else mg.setPosition(x-height,y);
					magics.add(mg);
				}
				else sfx.get("cantCost").play();
			}
			else if (casting == castingMagic4) {
				currentMagic = 3;
				if (mana > castCostMagic4) {
					sfx.get("Skill4").play();
					mana -= castCostMagic4;
					stackMagic4 += 1;
					if (stackMagic4 == 10) {
						magic4lv+=1;
						stackMagic4 = 0;
						castCostMagic4 += 30;
						damageMagic4 += 100;
					}
					Skills mg = new Skills(tileMap,facingRight,currentMagic);
					if (facingRight) mg.setPosition(x+100,y-(height/4));
					else mg.setPosition(x-100,y-(height/4));
					magics.add(mg);
				}
				else sfx.get("cantCost").play();
			}
			else if (casting == castingMagic5) {
				currentMagic = 4;
				if (mana > castCostMagic5) {
					sfx.get("Skill5").play();
					mana -= castCostMagic5;
					stackMagic5 += 1;
					if (stackMagic5 == 10) {
						magic5lv+=1;
						stackMagic5 = 0;
						castCostMagic5 += 40;
						damageMagic5 += 150;
					}
					Skills mg = new Skills(tileMap,facingRight,currentMagic);
					if (facingRight) mg.setPosition(x+100,y);
					else mg.setPosition(x-100,y);
					magics.add(mg);
				}
				else sfx.get("cantCost").play();
			}
			else if (casting == castingMagic6) {
				currentMagic = 5;
				if (mana > castCostMagic6) {
					sfx.get("Skill6").play();
					mana -= castCostMagic6;
					stackMagic6 += 1;
					health+=healMagic6;
					if(health>maxHealth) health=maxHealth;
					if (stackMagic6 == 10) {
						magic6lv+=1;
						stackMagic6 = 0;
						castCostMagic6 += 12;
						healMagic6 += 10;
					}
					Skills mg = new Skills(tileMap,facingRight,currentMagic);
					mg.setPosition(x,y);
					magics.add(mg);
				}
				else sfx.get("cantCost").play();
			}
		}
		
		//update magics
		for(int i=0;i<magics.size();i++) {
			magics.get(i).update();
			if(magics.get(i).shouldRemove()) {
				magics.remove(i);
				i--;
			}
		}
		
		// check done flinching
		if(flinching) {
			long elapsed =
				(System.nanoTime() - flinchTimer) / 1000000;
			if(elapsed > 1000) {
				isflinch = false;
				flinching = false;
			}
		}
		
		// set animation
		if(slashing) {
			if(currentAction != SLASHING) {
				sfx.get("Slash").play();
				currentAction = SLASHING;
				animation.setFrames(sprites.get(SLASHING));
				animation.setDelay(90);
				width = 100;
				maxSpeed = 0;
			}
		}
		else if (casting) {
				if(currentAction != CASTING) {
					currentAction = CASTING;
					animation.setFrames(sprites.get(CASTING));
					animation.setDelay(300);
					width = 50;
					maxSpeed = 0;
				}
		}
		else if(left || right || up || down) {
			if(currentAction != WALKING) {
				currentAction = WALKING;
				animation.setFrames(sprites.get(WALKING));
				animation.setDelay(160);
				width = 50;
				maxSpeed = 1.6;
			}
		}
		else if(isflinch) {
			if(currentAction != FLINCH) {
				currentAction = FLINCH;
				animation.setFrames(sprites.get(FLINCH));
				animation.setDelay(160);
				width = 50;
			}
		}
		else {
			if(currentAction != IDLE) {
				currentAction = IDLE;
				animation.setFrames(sprites.get(IDLE));
				animation.setDelay(400);
				width = 50;
			}
		}
		
		
		
		animation.update();
		
			// set direction
			if(currentAction != SLASHING
				&& currentAction != CASTING // CASTING
			) {
				if(right) facingRight = true;
				if(left) facingRight = false;
			}
			if(exp==10){
				level+=1;
				health = maxHealth += 25;
				mana = maxMana += 20;
				slashDamage+=5;
				exp = 0;
			}
		}
	
	public void draw(Graphics2D g) {
		
		setMapPosition();
		
		// draw magics
		for(int i = 0; i < magics.size(); i++) {
			magics.get(i).draw(g);
		}
		
		// draw player
		if(flinching) {
			long elapsed =
				(System.nanoTime() - flinchTimer) / 1000000;
			if(elapsed / 100 % 2 == 0) {
				return;
			}
		}
		
		g.setFont(font);
		g.drawString(
				"LEVEL : " + level,
				10,
				95
			);
		g.setColor(Color.BLACK);
		g.drawString(""+magic1lv,495,80);
		g.drawString(""+magic2lv,535,80);
		g.drawString(""+magic3lv,570,80);
		g.drawString(""+magic4lv,608,80);
		g.drawString(""+magic5lv,645,80);
		g.drawString(""+magic6lv,682,80);
		
		super.draw(g);
		
	}
	
}
