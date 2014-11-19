package Entity;

import TileMap.TileMap;
import Entity.Animation;
import Entity.Enemy;
import Entity.MapObject;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Skills extends MapObject {
	
	private boolean hit;
	private boolean remove;
	
	// animations
	private BufferedImage[] sprites;
	private BufferedImage[] hitSprites;
	
	private static final int MAGIC1 = 0;
	private static final int MAGIC2 = 1;
	private static final int MAGIC3 = 2;
	private static final int MAGIC4 = 3;
	private static final int MAGIC5 = 4;
	private static final int MAGIC6 = 5;
	
	public Skills(TileMap tm,boolean right,int currentMagic) {
		
		super(tm);
		
		facingRight = right;
		
		moveSpeed = 3.8;
		if(right) dx = moveSpeed;
		else dx = -moveSpeed;
		
		width = 50;
		height = 60;
		cwidth = 14;
		cheight = 14;
		
		// load sprites
		try {
			
			BufferedImage spritesheet = ImageIO.read(
				getClass().getResourceAsStream("/skills/skills.png")
			);
			
			if (currentMagic == MAGIC1) {
				sprites = new BufferedImage[5];
				for(int i=0;i<sprites.length;i++) {
					sprites[i] = spritesheet.getSubimage(
						i * width * 2, 
						height * 11,
						width * 2,
						height
					);
				}
				
				hitSprites = new BufferedImage[5];
				for(int i=0;i<sprites.length;i++) {
					hitSprites[i] = spritesheet.getSubimage(
						i * width * 2,
						height * 11,
						width * 2, 
						height
					);
				}
			}
			
			else if (currentMagic == MAGIC2) {
				sprites = new BufferedImage[9];
				for(int i=0;i<sprites.length;i++) {
					sprites[i] = spritesheet.getSubimage(
						i * width,
						height * 2,
						width,
						height * 2
					);
				}
				
				hitSprites = new BufferedImage[9];
				for(int i=0;i<sprites.length;i++) {
					hitSprites[i] = spritesheet.getSubimage(
						i * width,
						height * 2,
						width,
						height * 2
					);
				}
			}
			
			else if (currentMagic == MAGIC3) {
				sprites = new BufferedImage[12];
				for(int i=0;i<sprites.length;i++) {
					sprites[i] = spritesheet.getSubimage(
						i * width * 2,
						height * 4,
						width * 2,
						height * 2
					);
				}
				
				hitSprites = new BufferedImage[12];
				for(int i=0;i<sprites.length;i++) {
					hitSprites[i] = spritesheet.getSubimage(
						i * width * 2,
						height * 4,
						width * 2,
						height * 2
					);
				}
			}
			
			else if (currentMagic == MAGIC4) {
				sprites = new BufferedImage[3];
				for(int i=0;i<sprites.length;i++) {
					sprites[i] = spritesheet.getSubimage(
						i * width * 4,
						height * 6,
						width * 4,
						height * 2
					);
				}
				
				hitSprites = new BufferedImage[3];
				for(int i=0;i<sprites.length;i++) {
					hitSprites[i] = spritesheet.getSubimage(
						i * width * 4,
						height * 6,
						width * 4,
						height * 2
					);
				}
			}
			
			else if (currentMagic == MAGIC5) {
				sprites = new BufferedImage[6];
				for(int i=0;i<sprites.length;i++) {
					sprites[i] = spritesheet.getSubimage(
						i * width * 4,
						height * 8,
						width * 4,
						height * 2
					);
				}
				
				hitSprites = new BufferedImage[6];
				for(int i=0;i<sprites.length;i++) {
					hitSprites[i] = spritesheet.getSubimage(
						i * width * 4,
						height * 8,
						width * 4,
						height * 2
					);
				}
			}
			
			else if (currentMagic == MAGIC6) {
				sprites = new BufferedImage[15];
				for(int i=0;i<sprites.length;i++) {
					sprites[i] = spritesheet.getSubimage(
						i * width * 2,
						height * 10,
						width * 2,
						height
					);
				}
				
				hitSprites = new BufferedImage[15];
				for(int i=0;i<sprites.length;i++) {
					hitSprites[i] = spritesheet.getSubimage(
						i * width * 2,
						height * 10,
						width * 2,
						height
					);
				}
			}
			
//			sprites = new BufferedImage[7];
//			for (int i=0;i<7;i++) {
//				
//				BufferedImage[] bi =
//					new	BufferedImage[skillsFrames[i]];
//				
//				System.out.println("in loop i of print subimage");
//				
//				for (int j=0;j<skillsFrames[i];j++) {
//					
//					if(currentMagic == MAGIC1) {
//						System.out.println("CM : " + currentMagic);
//						bi[j] = spritesheet.getSubimage(
//							j * width,
//							0,
//							width,
//							height
//						);
//					}
//					else if(currentMagic == MAGIC2) {
//						System.out.println("CM : " + currentMagic);
//						bi[j] = spritesheet.getSubimage(
//							j * width,
//							0,
//							width,
//							height
//						);
//					}
//					else if(currentMagic == MAGIC3) {
//						System.out.println("CM : " + currentMagic);
//						bi[j] = spritesheet.getSubimage(
//							j * width,
//							0,
//							width,
//							height
//						);
//					}
//					else if(currentMagic == MAGIC4) {
//						System.out.println("CM : " + currentMagic);
//						bi[j] = spritesheet.getSubimage(
//							j * width,
//							0,
//							width,
//							height
//						);
//					}
//					else if(currentMagic == MAGIC5) {
//						System.out.println("CM : " + currentMagic);
//						bi[j] = spritesheet.getSubimage(
//							j * width,
//							0,
//							width,
//							height
//						);
//					}
//					else if(currentMagic == MAGIC6) {
//						System.out.println("CM : " + currentMagic);
//						bi[j] = spritesheet.getSubimage(
//							j * width,
//							0,
//							width,
//							height
//						);
//					}
//				}
//			}
			
			animation = new Animation();
			animation.setFrames(sprites);;
			animation.setDelay(70);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setHit() {
		if(hit) return;
		hit = true;
		dx = 0;
	}

	public boolean shouldRemove() { return remove; }
	
	
	public void update() {
		
		checkTileMapCollision();
		setPosition(xtemp,ytemp);
		
		if(dx == 0 && !hit) {
			setHit();
		}
		
		animation.update();
		if(hit && animation.hasPlayedOnce()) {
			remove = true; 
		}
	
	}
	
	public void draw(Graphics2D g) {
		
		setMapPosition();
		
		super.draw(g);
	}
}
