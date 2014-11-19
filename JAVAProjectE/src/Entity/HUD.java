package Entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class HUD {
	
	private double currentHealth;
	private double currentMana;
	
	private Player player;
	
	private BufferedImage image;
	
	public HUD(Player p) {
		player = p;
		try {
			image = ImageIO.read(
				getClass().getResourceAsStream(
					"/HUD/HUD.png"
				)
			);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g) {
		
		g.drawImage(image, 0, 10, null);
		
		// draw empty Health & Mana
//		g.setColor(Color.WHITE);
//		g.fillRect(
//			13,
//			24,
//			224,
//			11
//		); //13,24,"224",11
//		g.fillRect(
//			13, 
//			52, 
//			224,
//			11
//		); //13,52,224,11
		
		// draw current Health & Mana
		currentHealth = (player.getHealth()/player.getMaxHealth());
		currentMana = (player.getMana()/player.getMaxMana());
		
		g.setColor(Color.RED);
		g.fillRect(
			12, // 13-224
			24,
			(int) (currentHealth*224),
			11
		);
		g.setColor(Color.BLUE);
		g.fillRect(
			12,
			52,
			(int) (currentMana*224),
			11
			);

	}
	
}
