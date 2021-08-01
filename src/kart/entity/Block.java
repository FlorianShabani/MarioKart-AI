package kart.entity;

import java.awt.Color;
import java.awt.Graphics;

public class Block implements Entity {
	int x, y, width, height;

	public Block(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public boolean isTouching(Entity e) {
		return false;
	}

	@Override
	public boolean isInside(int x, int y) {
		if ((x > this.x) && (x < this.x + width) && (y > this.y) && (y < this.y + height)) {
			return true;
		}
		return false;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.blue);
		g.fillRect(x, y, width, height);
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
	}

	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public int getRot() {
		return 0;
	}
}
