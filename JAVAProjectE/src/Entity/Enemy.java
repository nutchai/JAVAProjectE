package Entity;

import TileMap.TileMap;

public class Enemy extends MapObject{
	
	protected int deadc;
	protected int health;
	protected int maxHealth;
	protected boolean dead;
	protected int damage;
	
	
	public Enemy(TileMap tm) {
		super(tm);
		
	}
	public int getdead(){
		return deadc;
	};
	
	public boolean isDead() {return dead;}
	
	public int getDamage() {return damage;}
	
	public void hit(int damage){
		if(dead) {deadc+=1;return;}
		if(dx>0) {dx=-7;}
		else if(dx<0) {dx=+7;}
		health -= damage;
		if(health < 0) {deadc+=1;health = 0;}
		if(health == 0) dead = true;
	}
	public void update() {}
}
