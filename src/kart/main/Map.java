package kart.main;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import kart.entity.ACircle;
import kart.entity.Block;
import kart.entity.Circle;
import kart.entity.Entity;
import kart.entity.Slope;

public class Map {

	ArrayList<Entity> entities = new ArrayList<Entity>();
	Point[] checkPoint;

	public double zoom = 0.5;

	private BufferedImage image;

	public Map() {
		/*
		 * entities.add(new Block(0, -500, 5000, 300)); entities.add(new Block(0, 400,
		 * 4500, 300)); entities.add(new Block(5000, -500, 300, 10000));
		 * entities.add(new Block(4200, 700, 300, 10000));
		 */

		try {
			image = ImageIO.read(getClass().getResource("/yoshiMap.png"));
		} catch (IOException e) {
		}

		entities.add(new Circle(2440, 3450, 1250, 1310));
		/*
		 * entities.add(new Block(2480, 3940, 1180, 190)); entities.add(new Block(2540,
		 * 2740, 980, 190)); entities.add(new ACircle(3410, 2380, 270, 410, 3));
		 */
		/*
		 * entities.add(new Block(3680, 1780, 80, 870)); entities.add(new Block(3140,
		 * 1650, 140, 600)); entities.add(new Block(2480, 4660, 820, 180));
		 * entities.add(new ACircle(1280, 2210, 1570, 1170, 1)); entities.add(new
		 * ACircle(1280, 3480, 1360, 1200, 4)); entities.add(new Block(1220, 3260, 80,
		 * 450)); entities.add(new Circle(3240, 4780, 220, 240)); entities.add(new
		 * Slope(3280, 4680, -150, 730)); entities.add(new Slope(3120, 5420, -900,
		 * 680)); entities.add(new Slope(3780, 4360, -360, 1700)); entities.add(new
		 * Slope(3420, 6060, -720, 520)); entities.add(new Slope(3720, 6340, -640,
		 * 480)); entities.add(new Slope(3720, 6330, 1340, 1440)); entities.add(new
		 * Circle(2980, 6790, 680, 710)); entities.add(new ACircle(2130, 6380, 170,
		 * 1080, 1)); entities.add(new ACircle(2160, 6830, 960, 840, 4));
		 * entities.add(new Block(2700, 7570, 480, 100)); entities.add(new Slope(3700,
		 * 7170, -540, 410)); entities.add(new Slope(3700, 7180, 560, 630));
		 * entities.add(new Slope(4260, 7810, -1000, 840)); entities.add(new Block(3080,
		 * 8680, 180, 1230)); entities.add(new Circle(4250, 7950, 170, 280));
		 * entities.add(new Slope(5020, 7990, -1260, 1210)); entities.add(new
		 * Circle(4980, 8000, 160, 320)); entities.add(new ACircle(3230, 9630, 1960,
		 * -10, 2)); entities.add(new Circle(4010, 9460, 490, 520)); entities.add(new
		 * Block(4000, 9580, 2560, 170)); entities.add(new ACircle(3280, 9680, 1600,
		 * 750, 4)); entities.add(new Block(3780, 10310, 2900, 110)); entities.add(new
		 * ACircle(3360, 4130, 380, 590, 2)); entities.add(new Circle(6480, 9270, 760,
		 * 990)); entities.add(new ACircle(6440, 9370, 900, 940, 3)); entities.add(new
		 * Block(7300, 9020, 100, 840)); entities.add(new Slope(6980, 8260, 400, 610));
		 * entities.add(new Slope(6980, 8280, 1920, -1760)); entities.add(new
		 * Slope(6400, 7940, 2020, -1860)); entities.add(new Slope(6180, 8120, 570,
		 * 880)); entities.add(new ACircle(6340, 8160, 3090, -1170, 4));
		 * entities.add(new Block(6260, 7960, 160, 640)); entities.add(new Circle(6980,
		 * 8410, 160, 270)); entities.add(new Circle(8320, 6190, 380, 440));
		 * entities.add(new Slope(8300, 5950, -1080, 410)); entities.add(new Block(8900,
		 * 5940, 190, 860)); entities.add(new ACircle(8340, 5580, 590, 620, 2));
		 * entities.add(new Block(8200, 5400, 340, 200)); entities.add(new Slope(8200,
		 * 5310, -980, 290)); entities.add(new Slope(7220, 5600, -1980, -500));
		 * entities.add(new Slope(7240, 6350, -2620, -770)); entities.add(new
		 * Circle(5240, 5250, 200, 280)); entities.add(new ACircle(5120, 4310, 1260,
		 * 830, 3)); entities.add(new Block(6380, 4020, 90, 580)); entities.add(new
		 * ACircle(4750, 5210, 410, 530, 4)); entities.add(new ACircle(4740, 4780, 340,
		 * 540, 1)); entities.add(new Slope(4740, 4540, 890, -160)); entities.add(new
		 * Circle(5360, 4140, 1060, 1190)); entities.add(new Block(5500, 3180, 1050,
		 * -1430)); entities.add(new Slope(6060, 3270, 430, 600)); entities.add(new
		 * Block(5540, 3270, 100, 480)); entities.add(new Slope(5880, 2690, -300, 450));
		 * entities.add(new Slope(6070, 3270, 310, -600)); entities.add(new Block(6230,
		 * 2590, 140, 450)); entities.add(new ACircle(5510, 2070, 770, 780, 2));
		 * entities.add(new Circle(5650, 2780, 540, 520)); entities.add(new Slope(5600,
		 * 2520, -580, 130)); entities.add(new Slope(5100, 2640, -480, -260));
		 * entities.add(new Block(4450, 1760, 190, 920)); entities.add(new Block(5080,
		 * 1600, 130, 560)); entities.add(new Slope(5100, 1870, 660, -80));
		 * entities.add(new Circle(4160, 1860, 960, 740)); entities.add(new
		 * ACircle(3240, 1070, 1060, 850, 1)); entities.add(new ACircle(4060, 1060,
		 * 1080, 850, 2));
		 */




		/*
		 * for(int i = 0; i < 1000; i++) { entities.add(new Block((int)(Math.random() *
		 * 10000), (int)(Math.random() * 10000), 500, 500)); }
		 */
	}

	public Map(String loc) {

	}

	public static int scale = 10;

	public void draw(Graphics gd, ArrayList<NKart> karts, int camI) {
		Graphics2D g = (Graphics2D) gd;

		AffineTransform at = g.getTransform();
		
		g.translate(Main.Width / 2, Main.Height / 2);
		g.scale(zoom, zoom);
		g.translate((int) (-karts.get(camI).getX()), (int) (-karts.get(camI).getY()));

		AffineTransform atS = g.getTransform();

		g.scale(scale, scale);
		g.drawImage(image, 0, 0, null);

		g.setTransform(atS);
		
		for (Entity e : entities) 
			e.draw(g);
		 


		for (NKart k : karts)
			k.draw(g);

		g.setTransform(at);
	}

	public void tick() {
	}

	public ArrayList<Entity> getEntities() {
		return entities;
	}

	public void saveMap(String loc) {

	}

}
