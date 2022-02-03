package kart.main;

import java.awt.Color;
import java.awt.Graphics;import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

import kart.entity.Entity;
import neuralnetwork.MNeuralNetwork;
import setup.Manager;
import setup.Window;

public class ManualTest implements Manager {
	public static final int Width = 700, Height = 600;

	NKart kart = new NKart(true);

	int nkarts = 0;

	private int following = 0;

	ArrayList<NKart> karts = new ArrayList<NKart>();
	ArrayList<Entity> entities;
	Map map;

	// private BufferedImage image;

	//////////
	boolean tright = false, tleft = false, forward = false, backward = false;
	/////////

	// KARTS

	float img[];

	public ManualTest() {
		map = new Map(Width, Height);
		karts.add(kart);
		kart.setX(7364);
		kart.setY(13692);
		
		for (int i = 0; i < nkarts; i++) {
			karts.add(new NKart());
		}

		/*
		 * try { image = ImageIO.read(getClass().getResource("/marioCircuit.jpg")); }
		 * catch (IOException e) { }
		 */

		entities = map.getEntities();
	}

	public synchronized void train() {

	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, Width, Height);

		map.draw(g, karts, following);

		/////////////
		for (int i = 0; i < img.length; i++) {
			g.setColor(new Color(0, 0, (int) (img[i] * 250)));
			g.fillRect(i * 20 + 100, 550, 20, 20);
		}
	}

	@Override
	public void tick() {

		map.tick();

		if (tright)
			kart.steerR();
		else if (tleft)
			kart.steerL();
		if (forward)
			kart.driveF();
		else if (backward)
			kart.driveB();

		img = kart.getImg(map);

		for (int i = 0; i < karts.size(); i++) {
			karts.get(i).tick(map);
		}
		//System.out.println(map.isInside(0, 0));
	}

	public void load(String file) {

	}

	public void save(String file, int sub, int layers, int[] nnodes, int[] fitness) {
		MNeuralNetwork[] mnn = new MNeuralNetwork[sub];

		for (int i = 0; i < sub; i++) {
			// mnn[i] = players[i].getMnn();
		}

		MNeuralNetwork.writeToFAMNN(layers, nnodes, file, mnn, fitness);
	}

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		Window wind = new Window(Width, Height, 60, 60, "Mario Kart Neural Network", new ManualTest());
	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {

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

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void keyPressed(int e) {
		// WASD
		if (e == 68) {
			tright = true;
		} else if (e == 65) {
			tleft = true;
		}
		if (e == 87) {
			forward = true;
		} else if (e == 83) {
			backward = true;
		}

		if (e == 32) {// SPACE
			following++;
			if (following >= nkarts) {
				following = 0;
				System.out.println("FUUUUUUUCK UUU");
			}
		}
	}

	@Override
	public void keyReleased(int e) {
		if (e == 68) {
			tright = false;
		} else if (e == 65) {
			tleft = false;
		}

		if (e == 87) {
			forward = false;
		} else if (e == 83) {
			backward = false;
		}
	}

	@Override
	public void keyTyped(int e) {

	}

}
