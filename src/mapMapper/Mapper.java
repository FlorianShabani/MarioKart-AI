package mapMapper;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import kart.entity.ACircle;
import kart.entity.Block;
import kart.entity.Circle;
import kart.entity.Entity;
import kart.entity.Slope;
import kart.main.Main;
import kart.main.Map;
import setup.Manager;
import setup.Window;

public class Mapper implements Manager {

	/*
	 * mode = 1 Block | mode = 2 Circle | mode = 3 ACircle Rot 1 | mode = 4 ACircle Rot 2 |
	 * mode = 5 ACircle Rot 3 | mode = 6 ACircle Rot 4 | mode = 7 Slope |
	 */
	
	// TODO : ADD SLOPES   | \
	//					    \ \
	//						 \ |

	public static final int width = Main.Width, height = Main.Height;
	private int shiftX = 0, shiftY = 0, mX = 200, mY = 200;

	private String location = "yoshi2";

	int mode = 1, iX, iY;

	int shiftSpeed = 10;

	boolean drawi = false;

	int scale = 1;
	private BufferedImage image;
	ArrayList<Entity> ents = new ArrayList<Entity>();

	public Mapper() {
		try {
			image = ImageIO.read(getClass().getResource("/yoshiMap.png"));
			
		} catch (IOException e) {
		}
		ents.add(new Circle(244, 345, 125, 131));
		ents.add(new Block(248, 394, 118, 19));
		ents.add(new Block(254, 274, 98, 19));
		ents.add(new ACircle(341, 238, 27, 41, 3));
		ents.add(new Block(368, 178, 8, 87));
		ents.add(new Block(314, 165, 14, 60));
		ents.add(new Block(248, 466, 82, 18));
		ents.add(new ACircle(128, 221, 157, 117, 1));
		ents.add(new ACircle(128, 348, 136, 120, 4));
		ents.add(new Block(122, 326, 8, 45));
		ents.add(new Circle(324, 478, 22, 24));
		ents.add(new Slope(328, 468, -15, 73));
		ents.add(new Slope(312, 542, -90, 68));
		ents.add(new Slope(378, 436, -36, 170));
		ents.add(new Slope(342, 606, -72, 52));
		ents.add(new Slope(372, 634, -64, 48));
		ents.add(new Slope(372, 633, 134, 144));
		ents.add(new Circle(298, 679, 68, 71));
		ents.add(new ACircle(213, 638, 17, 108, 1));
		ents.add(new ACircle(216, 683, 96, 84, 4));
		ents.add(new Block(270, 757, 48, 10));
		ents.add(new Slope(370, 717, -54, 41));
		ents.add(new Slope(370, 718, 56, 63));
		ents.add(new Slope(426, 781, -100, 84));
		ents.add(new Block(308, 868, 18, 123));
		ents.add(new Circle(425, 795, 17, 28));
		ents.add(new Slope(502, 799, -126, 121));
		ents.add(new Circle(498, 800, 16, 32));
		ents.add(new ACircle(323, 963, 196, -1, 2));
		ents.add(new Circle(401, 946, 49, 52));
		ents.add(new Block(400, 958, 256, 17));
		ents.add(new ACircle(328, 968, 160, 75, 4));
		ents.add(new Block(378, 1031, 290, 11));
		ents.add(new ACircle(336, 413, 38, 59, 2));
		ents.add(new Circle(648, 927, 76, 99));
		ents.add(new ACircle(644, 937, 90, 94, 3));
		ents.add(new Block(730, 902, 10, 84));
		ents.add(new Slope(698, 826, 40, 61));
		ents.add(new Slope(698, 828, 192, -176));
		ents.add(new Slope(640, 794, 202, -186));
		ents.add(new Slope(618, 812, 57, 88));
		ents.add(new ACircle(634, 816, 309, -117, 4));
		ents.add(new Block(626, 796, 16, 64));
		ents.add(new Circle(698, 841, 16, 27));
		ents.add(new Circle(832, 619, 38, 44));
		ents.add(new Slope(830, 595, -108, 41));
		ents.add(new Block(890, 594, 19, 86));
		ents.add(new ACircle(834, 558, 59, 62, 2));
		ents.add(new Block(820, 540, 34, 20));
		ents.add(new Slope(820, 531, -98, 29));
		ents.add(new Slope(722, 560, -198, -50));
		ents.add(new Slope(724, 635, -262, -77));
		ents.add(new Circle(524, 525, 20, 28));
		ents.add(new ACircle(512, 431, 126, 83, 3));
		ents.add(new Block(638, 402, 9, 58));
		ents.add(new ACircle(475, 521, 41, 53, 4));
		ents.add(new ACircle(474, 478, 34, 54, 1));
		ents.add(new Slope(474, 454, 89, -16));
		ents.add(new Circle(536, 414, 106, 119));
		ents.add(new Block(550, 318, 105, -143));
		ents.add(new Slope(606, 327, 43, 60));
		ents.add(new Block(554, 327, 10, 48));
		ents.add(new Slope(588, 269, -30, 45));
		ents.add(new Slope(607, 327, 31, -60));
		ents.add(new Block(623, 259, 14, 45));
		ents.add(new ACircle(551, 207, 77, 78, 2));
		ents.add(new Circle(565, 278, 54, 52));
		ents.add(new Slope(560, 252, -58, 13));
		ents.add(new Slope(510, 264, -48, -26));
		ents.add(new Block(445, 176, 19, 92));
		ents.add(new Block(508, 160, 13, 56));
		ents.add(new Slope(510, 187, 66, -8));
		ents.add(new Circle(416, 186, 96, 74));
		ents.add(new ACircle(324, 107, 106, 85, 1));
		ents.add(new ACircle(406, 106, 108, 85, 2));


	}

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		Window wind = new Window(width, height, 60, 60, "Mapper", new Mapper());
	}

	@Override
	public void draw(Graphics gd) {
		Graphics2D g = (Graphics2D) gd;

		AffineTransform at = g.getTransform();
		g.translate(-shiftX, -shiftY);
		g.scale(scale, scale);
		g.drawImage(image, 0, 0, null);
		for (Entity e : ents) {
			e.draw(g);
		}
		g.setTransform(at);
		
		g.drawOval(0, 0, 20, 20);
		g.setColor(Color.black);
		g.drawString(Integer.toString(mX) + " " + Integer.toString(mY), mX , mY);
		
		g.dispose();
	}

	@Override
	public void tick() {
		if (!drawi) {
			if (mX < 50)
				shiftX -= shiftSpeed;
			else if (mX > width - 50)
				shiftX += shiftSpeed;

			if (mY < 50)
				shiftY -= shiftSpeed;
			else if (mY > height - 100)
				shiftY += shiftSpeed;
		}
	}

	public void save(String loc) {
		try {
			File f = new File(System.getProperty("user.home") + "/Desktop/" + loc + ".txt");
			f.createNewFile();

			FileWriter fw = new FileWriter(f.getAbsoluteFile());

			BufferedWriter bw = new BufferedWriter(fw);

			for (Entity e : ents) {
				int x = e.getX() / scale * Map.scale;
				int y = e.getY() / scale * Map.scale;
				int width = e.getWidth() / scale * Map.scale;
				int height = e.getHeight() / scale * Map.scale;
				int rot = e.getRot();
				if (e instanceof Block) {
					bw.write("entities.add(new Block(" + x  + ", " + y + ", " + width + ", " + height + "));");
				} else if (e instanceof Circle) {
					bw.write("entities.add(new Circle(" + x + ", " + y + ", " + width + ", " + height + "));");
				} else if (e instanceof ACircle) {
					bw.write("entities.add(new ACircle(" + x + ", " + y + ", " + width + ", " + height + ", " + rot + "));");
				} else if (e instanceof Slope) {
					bw.write("entities.add(new Slope(" + x + ", " + y + ", " + width + ", " + height + "));");
				}
				bw.newLine();
			}
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Fak ju");
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mX = e.getX();
		mY = e.getY();
		if (drawi) {
			ents.get(ents.size() - 1).setWidth(mX - iX);
			ents.get(ents.size() - 1).setHeight(mY - iY);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (!drawi) {
			iX = mX;
			iY = mY;
			// mode
			if (mode == 1) {
				ents.add(new Block(iX + shiftX, iY + shiftY, 10, 10));
			} else if (mode == 2) {
				ents.add(new Circle(iX + shiftX, iY + shiftY, 10, 10));
			} else if (mode == 3) {
				ents.add(new ACircle(iX + shiftX, iY + shiftY, 10, 10, 1));
			} else if (mode == 4) {
				ents.add(new ACircle(iX + shiftX, iY + shiftY, 10, 10, 2));
			} else if (mode == 5) {
				ents.add(new ACircle(iX + shiftX, iY + shiftY, 10, 10, 3));
			} else if (mode == 6) {
				ents.add(new ACircle(iX + shiftX, iY + shiftY, 10, 10, 4));
			} else if (mode == 7 ) {
				ents.add(new Slope(iX + shiftX, iY + shiftY, 10, 10));
			}

			drawi = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void keyPressed(int e) {
		drawi = false;
		if (e == 35)
			mode = 1;
		else if (e == 40)
			mode = 2;
		else if (e == 34)
			mode = 3;
		else if (e == 37)
			mode = 4;
		else if (e == 12)
			mode = 5;
		else if (e == 39)
			mode = 6;
		else if (e == 36)
			mode = 7;
		else if (e == 107 && ents.size() > 0)
			ents.remove(ents.size() - 1);
		else if (e == 80) { // Save with P
			System.out.println("Yep");
			save(location);
		}

	}

	@Override
	public void keyReleased(int e) {

	}

	@Override
	public void keyTyped(int e) {

	}
}
