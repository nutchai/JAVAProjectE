package Entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class HUD {
	
	private Player player;
	
	private BufferedImage image;
	private Font font;
	
	private double currentHealth;
	private double currentMana;
	
	public HUD(Player p) {
		player = p;
		try {
			image = ImageIO.read(
				getClass().getResourceAsStream(
					"/HUD/HUD.png"
				)
			);
			font = new Font("Arial Rounded MT Bold", Font.PLAIN, 10);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g) {
		
		g.drawImage(image, 0, 10, null);
		
		// draw background of Health & Mana slots
		g.setColor(Color.BLACK);
		g.fillRect(
			12,
			24,
			224,
			11
		);
		g.fillRect(
			12,
			51,
			224,
			11
		);
		
		// current Health & Mana
		currentHealth = (player.getHealth()/player.getMaxHealth());
		currentMana = (player.getMana()/player.getMaxMana());
		
		// draw Health
		g.setColor(Color.RED);
		g.fillRect(
			12,
			24,
			(int) (currentHealth*224),
			11
		);
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString(
			(int) (player.getHealth()) + "/" + (int) (player.getMaxHealth()),
			105,
			33
		);
		
		// draw Mana
		g.setColor(Color.BLUE);
		g.fillRect(
			12,
			51,
			(int) (currentMana*224),
			11
		);
		g.setColor(Color.WHITE);
		g.drawString(
			(int) (player.getMana()) + "/" + (int) (player.getMaxMana()),
			105,
			60
		);

	}
	
}
