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
import kart.entity.CheckPoint;
import kart.entity.Circle;
import kart.entity.Entity;
import kart.entity.Slope;
import kart.main.ManualTest;
import kart.main.Map;
import setup.Manager;
import setup.Window;

public class Mapper implements Manager {

	/*
	 * mode = 1 Block | mode = 2 Circle | mode = 3 ACircle Rot 1 | mode = 4 ACircle
	 * Rot 2 | mode = 5 ACircle Rot 3 | mode = 6 ACircle Rot 4 | mode = 7 Slope |
	 */

	// TODO : ADD SLOPES | \
	// \ \
	// \ |

	public static final int width = ManualTest.Width, height = ManualTest.Height;
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
		g.drawString(Integer.toString(mX) + " " + Integer.toString(mY), mX, mY);

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

	public void read(String loc) {

	}

	public void saveF(String loc) {

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
				if (e instanceof CheckPoint) {
					bw.write("entities.add(new CheckPoint(" + x + ", " + y + ", " + width + ", " + height + "));");
				} else if (e instanceof Block) {
					bw.write("entities.add(new Block(" + x + ", " + y + ", " + width + ", " + height + "));");
				} else if (e instanceof Circle) {
					bw.write("entities.add(new Circle(" + x + ", " + y + ", " + width + ", " + height + "));");
				} else if (e instanceof ACircle) {
					bw.write("entities.add(new ACircle(" + x + ", " + y + ", " + width + ", " + height + ", " + rot
							+ "));");
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
			} else if (mode == 7) {
				ents.add(new Slope(iX + shiftX, iY + shiftY, 10, 10));
			} else if (mode == 8) {
				ents.add(new CheckPoint(iX + shiftX, iY + shiftY, 10, 10));
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
		else if (e == 38)
			mode = 8;
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
