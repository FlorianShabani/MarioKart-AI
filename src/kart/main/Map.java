package kart.main;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import kart.entity.CheckPoint;
import kart.entity.Entity;
import kart.entity.Kart;

public class Map {

	ArrayList<Entity> entities = new ArrayList<Entity>();
	ArrayList<CheckPoint> checkPoints = new ArrayList<CheckPoint>();

	public double zoom = 0.3;
	public int WIDTH, HEIGHT;

	private static BufferedImage image;
	private static BufferedImage borderImage;

	public static final int scale = 14;

	public int WWidth, WHeight;// Window width and height

	public Map(int WWidth, int WHeight) {
		/*
		 * entities.add(new Block(0, -500, 5000, 300)); entities.add(new Block(0, 400,
		 * 4500, 300)); entities.add(new Block(5000, -500, 300, 10000));
		 * entities.add(new Block(4200, 700, 300, 10000));
		 */

		this.WWidth = WWidth;
		this.WHeight = WHeight;

		try {
			image = ImageIO.read(getClass().getResource("/yoshiMap.png"));
			BufferedImage borderImageO = ImageIO.read(getClass().getResource("/yoshiMapBorder.png"));
			borderImage = new BufferedImage(borderImageO.getWidth() * scale, borderImageO.getHeight() * scale,
					BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = (Graphics2D) borderImage.createGraphics();
			g.drawImage(borderImageO, 0, 0, borderImageO.getWidth() * scale, borderImageO.getHeight() * scale, null);
			WIDTH = borderImage.getWidth();
			HEIGHT = borderImage.getHeight();
		} catch (IOException e) {
			System.out.println("Didn load map");
		}

		/*
		 * entities.add(new Block(2480, 3940, 1180, 190)); entities.add(new Block(2540,
		 * 2740, 980, 190)); entities.add(new ACircle(3410, 2380, 270, 410, 3));
		 */

		/*
		 * for(int i = 0; i < 1000; i++) { entities.add(new Block((int)(Math.random() *
		 * 10000), (int)(Math.random() * 10000), 500, 500)); }
		 */
		loadCheckPoints();
	}

	public Map(int WWidth, int WHeight, String mapName) {

		this.WWidth = WWidth;
		this.WHeight = WHeight;

		try {
			image = ImageIO.read(getClass().getResource("/" + mapName + "Map" + ".png"));

			BufferedImage borderImageO = ImageIO.read(getClass().getResource("/" + mapName + "MapBorder" + ".png"));
			borderImage = new BufferedImage(borderImageO.getWidth() * scale, borderImageO.getHeight() * scale,
					BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = (Graphics2D) borderImage.createGraphics();
			g.drawImage(borderImageO, 0, 0, borderImageO.getWidth() * scale, borderImageO.getHeight() * scale, null);

			WIDTH = borderImage.getWidth();
			HEIGHT = borderImage.getHeight();

		} catch (IOException e) {
			System.out.println("Didn load map");
		}
		loadCheckPoints();

	}

	public Map(int WWidth, int WHeight, String mapName, boolean loadGraphics) {

		this.WWidth = WWidth;
		this.WHeight = WHeight;

		if (borderImage == null) {
			try {
				if (loadGraphics) {
					image = ImageIO.read(getClass().getResource("/" + mapName + "Map" + ".png"));
				}

				BufferedImage borderImageO = ImageIO.read(getClass().getResource("/" + mapName + "MapBorder" + ".png"));
				borderImage = new BufferedImage(borderImageO.getWidth() * scale, borderImageO.getHeight() * scale,
						BufferedImage.TYPE_INT_ARGB);
				Graphics2D g = (Graphics2D) borderImage.createGraphics();
				g.drawImage(borderImageO, 0, 0, borderImageO.getWidth() * scale, borderImageO.getHeight() * scale,
						null);

				WIDTH = borderImage.getWidth();
				HEIGHT = borderImage.getHeight();

			} catch (IOException e) {
				System.out.println("Didn load map");
			}
		}
		loadCheckPoints();
	}

	public Map(String loc) {
		loadCheckPoints();
	}

	public void draw(Graphics gd, ArrayList<NKart> karts, int camI) {
		Graphics2D g = (Graphics2D) gd;

		AffineTransform at = g.getTransform();

		g.translate(WWidth / 2, WHeight / 2);
		g.scale(zoom, zoom);
		g.translate((int) (-karts.get(camI).getX()), (int) (-karts.get(camI).getY()));

		AffineTransform atS = g.getTransform();
		g.scale(scale, scale);
		g.drawImage(image, 0, 0, null);

		g.setTransform(atS);
		g.drawImage(borderImage, 0, 0, null);

		for (Entity e : entities)
			e.draw(g);

		for (NKart k : karts)
			k.draw(g);

		g.setTransform(at);
	}

	public void draw(Graphics gd, NKart[] karts, int camI) {
		Graphics2D g = (Graphics2D) gd;

		AffineTransform at = g.getTransform();

		g.translate(WWidth / 2, WHeight / 2);
		g.scale(zoom, zoom);
		g.translate((int) (-karts[camI].getX()), (int) (-karts[camI].getY()));

		AffineTransform atS = g.getTransform();
		g.scale(scale, scale);
		g.drawImage(image, 0, 0, null);

		g.setTransform(atS);
		g.drawImage(borderImage, 0, 0, null);

		for (Entity e : entities)
			e.draw(g);

		for (Kart k : karts)
			k.draw(g);

		g.setTransform(at);
	}

	public void loadCheckPoints() {
		checkPoints.add(new CheckPoint(7364, 13692, 126, 756));
		checkPoints.add(new CheckPoint(5586, 13678, 112, 728));
		checkPoints.add(new CheckPoint(4606, 12516, 812, 154));
		checkPoints.add(new CheckPoint(6174, 11102, 126, 798));
		checkPoints.add(new CheckPoint(5138, 9324, 126, 700));
		checkPoints.add(new CheckPoint(3570, 8582, 140, 700));
		checkPoints.add(new CheckPoint(4690, 6524, 448, 84));
		checkPoints.add(new CheckPoint(3388, 5796, 154, 728));
		checkPoints.add(new CheckPoint(3388, 3220, 98, 588));
		checkPoints.add(new CheckPoint(5768, 1582, 112, 504));
		checkPoints.add(new CheckPoint(7070, 3080, 126, 518));
		checkPoints.add(new CheckPoint(7910, 4788, 518, 140));
		checkPoints.add(new CheckPoint(7266, 6692, 98, 462));
		checkPoints.add(new CheckPoint(7210, 7546, 98, 490));
		checkPoints.add(new CheckPoint(10080, 8316, 98, 504));
		checkPoints.add(new CheckPoint(11662, 7868, 126, 504));
		checkPoints.add(new CheckPoint(11844, 8904, 112, 644));
		checkPoints.add(new CheckPoint(9618, 10920, 112, 742));
		checkPoints.add(new CheckPoint(9604, 12740, 574, 168));

		entities.add(new CheckPoint(7364, 13692, 126, 756));
		entities.add(new CheckPoint(5586, 13678, 112, 728));
		entities.add(new CheckPoint(4606, 12516, 812, 154));
		entities.add(new CheckPoint(6174, 11102, 126, 798));
		entities.add(new CheckPoint(5138, 9324, 126, 700));
		entities.add(new CheckPoint(3570, 8582, 140, 700));
		entities.add(new CheckPoint(4690, 6524, 448, 84));
		entities.add(new CheckPoint(3388, 5796, 154, 728));
		entities.add(new CheckPoint(3388, 3220, 98, 588));
		entities.add(new CheckPoint(5768, 1582, 112, 504));
		entities.add(new CheckPoint(7070, 3080, 126, 518));
		entities.add(new CheckPoint(7910, 4788, 518, 140));
		entities.add(new CheckPoint(7266, 6692, 98, 462));
		entities.add(new CheckPoint(7210, 7546, 98, 490));
		entities.add(new CheckPoint(10080, 8316, 98, 504));
		entities.add(new CheckPoint(11662, 7868, 126, 504));
		entities.add(new CheckPoint(11844, 8904, 112, 644));
		entities.add(new CheckPoint(9618, 10920, 112, 742));
		entities.add(new CheckPoint(9604, 12740, 574, 168));

	}

	public void tick(NKart[] karts, int[] kartsCheckPoints) {
		for (int i = 0; i < karts.length; i++) {
			NKart k = karts[i];
			if (kartsCheckPoints[i] == checkPoints.size() - 1) {
				// Final CheckPoint reached/
				if (k.isTouching(checkPoints.get(0))) {
					kartsCheckPoints[i] = checkPoints.size();
				}
			} else if (kartsCheckPoints[i] < checkPoints.size() - 1
					&& k.isTouching(checkPoints.get(kartsCheckPoints[i] + 1))) {
				kartsCheckPoints[i]++;
			}
		}
	}

	public void tick() {
	}

	public boolean isInside(int x, int y) {
		if (x < 0)
			x = 0;
		else if (x >= borderImage.getWidth())
			x = borderImage.getWidth() - 1;
		if (y < 0)
			y = 0;
		else if (y >= borderImage.getHeight())
			y = borderImage.getHeight() - 1;
		return (borderImage.getRGB(x, y) >> 24 & 0xFF) != 0;
	}

	public ArrayList<Entity> getEntities() {
		return entities;
	}

	public int nCheckPoints() {
		return checkPoints.size();
	}

	public int[] getStartingPosition() {
		return new int[] { checkPoints.get(0).getX() + checkPoints.get(0).getWidth() / 2,
				checkPoints.get(0).getY() + checkPoints.get(0).getHeight() / 2 };
	}
}
