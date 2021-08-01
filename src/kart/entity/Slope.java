package kart.entity;

import java.awt.Color;
import java.awt.Graphics;

public class Slope implements Entity {

	int x, y, height, width, fheight;
	double angle;

	public Slope(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.fheight = 300;
		angle = Math.atan(height / (double) (width));
	}

	@Override
	public boolean isTouching(Entity e) {
		return false;
	}

	@Override
	public boolean isInside(int x, int y) {
		if (x < this.x || x > this.x + width)
			return false;
		
		int dx = this.x - x;
		int iy = (int) Math.tan(angle) * dx;
		int fy = iy + fheight;

		if (y > iy && y < fy)
			return true;

		return false;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillPolygon(new int[] { x, x + width, x + width, x },
				new int[] { y, y + height, y + height + fheight, y + fheight }, 4);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	@Override
	public int getRot() {
		return 0;
	}

}
