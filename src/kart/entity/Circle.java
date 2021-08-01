package kart.entity;

import java.awt.Color;
import java.awt.Graphics;

public class Circle implements Entity {

	int x, y, width, height;

	public Circle(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	@Override
	public boolean isTouching(Entity e) {
		return false;
	}

	@Override
	public boolean isInside(int x, int y) {
		if (((x - this.x - (width/2)) * (x - this.x - (width/2)) / (double)(width * width / 4)) + 
				((y - this.y - (height/2)) * (y - this.y - (height/2)) / (double)(height * height) / 4) < 1.0) {
			return true;
		}
		return false;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.blue);
		g.fillOval(x - width / 2, y - height / 2, width, height);
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
