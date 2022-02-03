package kart.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Kart implements Entity {

	protected int x, y, width = 70, height = 70, velx = 0, vely = 0;
	public double a = 3.14, velm = 0, acc = 0, speedCap = 4, angleR = 0.05, thrust = 0.5, brakeF = -1, frictC = 0.010;

	protected static BufferedImage img;

	public Kart() {
		// Check Starting Position
		render();
	}

	public Kart(boolean loadGraphics) {
		if(loadGraphics) render();
	}

	public void render() {
		try {
			BufferedImage image = ImageIO.read(getClass().getResource("/MarioK.png"));
			img = new BufferedImage(width / 3, height / 3, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = img.createGraphics();
			g2d.drawImage(image, 0, 0, width / 3, height / 3, null);
			g2d.dispose();
		} catch (IOException e) {}
	}

	protected void tick() {
		velm += acc;
		if (velm < 0)
			velm = 0;
		/*
		 * if(velm >= speedCap) { velm = speedCap; }
		 */
		velx = (int) (Math.cos(a) * velm);
		vely = (int) (Math.sin(a) * velm);
		x += velx;
		y += vely;
		acc = 0;
		applyFriction();
	}

	public void applyF(double f) {
		acc += f;
	}

	public void driveF() {
		this.applyF(thrust);
	}

	public void driveB() {
		this.applyF(-thrust);
	}

	public void brake() {
		this.applyF(brakeF);
	}

	public void applyFriction() {
		this.applyF(-velm * frictC - 0.015);
	}

	public void steerR() {
		a += angleR;
	}

	public void steerL() {
		a -= angleR;
	}

	@Override
	public boolean isTouching(Entity e) {
		return (e.isInside(x, y) || e.isInside(x + width, y) || e.isInside(x, y + height)
				|| e.isInside(x + width, y + height));
	}

	@Override
	public boolean isInside(int x, int y) {
		if ((x > this.x) && (x < this.x + width) && (y > this.y) && (y < this.y + height)) {
			return true;
		}
		return false;
	}

	@Override
	public void draw(Graphics gd) {
		Graphics2D g = (Graphics2D) gd;
		AffineTransform at = g.getTransform();
		g.setColor(Color.red);
		g.rotate(a + Math.PI / 2, x + width / 2, y + height / 2);
		g.drawImage(img, x, y, width, height, null);
		g.setTransform(at);
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

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public double getVelm() {
		return velm;
	}

	public void setVelm(double velm) {
		this.velm = velm;
	}

	@Override
	public int getRot() {
		return 0;
	}

}
