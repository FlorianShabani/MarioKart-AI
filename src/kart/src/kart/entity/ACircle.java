package kart.entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ACircle implements Entity {

	int x, y, width, height, rot;
	
	BufferedImage img;

	// Rot 1 ____ 2 ___ ...
	// ( )
	// | |
	public ACircle(int x, int y, int width, int height, int rot) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.rot = rot;
		
		render();
	}

	@Override
	public boolean isTouching(Entity e) {
		return false;
	}

	@Override
	public boolean isInside(int x, int y) {
		if ((x > this.x) && (x < this.x + width) && (y > this.y) && (y < this.y + height)) {
			if(rot == 1) {
				if((((x - this.x) * (x - this.x) / (width * width) )
						+ ((y - this.y ) * (y - this.y) / (height * height)) > 1))
				return true;
			}else if(rot == 2) {
				if((((x - this.x) * (x - this.x) / (width * width))
						+ ((height - y + this.y) * (height - y + this.y) / (height * height)) > 1))
				return true;
			}else if(rot == 3) {
				if((((x - this.x) * (x - this.x) / (width * width))
						+ ((y - this.y ) * (y - this.y) / (height * height)) > 1))
				return true;
			}else if(rot == 4) {
				if((((x - this.x - width) * (x - this.x - width) / width * width)
						+ ((y - this.y) * (y - this.y) / height * height) > 1))
				return true;
			}
		}
		return false;
	}

	@Override
	public void draw(Graphics gd) {
		gd.drawImage(img, x, y, null);
	}

	public void render() {
		img = new BufferedImage((width > 0) ? width : 1, (height > 0) ? height : 1, BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g = img.createGraphics();
		g.setColor(Color.blue);
		g.fillRect(0, 0, width, height);
		// clear pi0els
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OUT));
		g.setColor(new Color(255, 255, 255, 0));
		if (rot == 1) {
			g.fillOval(0, 0, width * 2, height * 2);
		} else if (rot == 2) {
			g.fillOval(0 - width, 0, width * 2, height * 2);
		} else if (rot == 3) {
			g.fillOval(0 - width, 0 - height, width * 2, height * 2);
		} else if (rot == 4) {
			g.fillOval(0, 0 - height, width * 2, height * 2);
		}
		g.dispose();
		// draw new graphics
	}
	
	public int getY() {
		return y;
	}

	public int getX() {
		return x;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setWidth(int width) {
		this.width = width;
		render();
	}

	public void setHeight(int height) {
		this.height = height;
		render();
	}

	@Override
	public int getRot() {
		return rot;
	}
}
