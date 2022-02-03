package kart.entity;

import java.awt.Graphics;

public interface Entity {
	public boolean isTouching(Entity e);
	public boolean isInside(int x, int y);
	public void draw(Graphics g);
	public int getX();
	public int getY();
	public int getWidth();
	public int getHeight();
	public void setWidth(int w);
	public void setHeight(int h);
	public int getRot();
}
