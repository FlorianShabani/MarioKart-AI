package kart.main;

import java.awt.Graphics;

import kart.train.RaceKarts;
import neuralnetwork.MNeuralNetwork;
import setup.ManagerA;
import setup.Window;

public class TestKart extends ManagerA {

	public static final int WIDTH = 800, HEIGHT = 700;
	
	RaceKarts rk;
	NKart[] karts;
	String map = "yoshi";
	String loadLoc = "MarioKartMNNF/Mario";
 	public TestKart(boolean raceTheKart) {
		if (raceTheKart) {
			karts = new NKart[2];
			karts[0] = new NKart(true);
			karts[1] = new NKart(MNeuralNetwork.readBMNN(loadLoc));
			
			rk = new RaceKarts(karts, map, WIDTH, HEIGHT);
		} else {
			karts = new NKart[1];
			karts[0] = new NKart(MNeuralNetwork.readBMNN(loadLoc));
			
			rk = new RaceKarts(karts, map, WIDTH, HEIGHT);
		}
	}
	
 	public void draw(Graphics g) {
 		rk.draw(g);
 	}
 	
 	public void tick() {
 		rk.tick();
 	}
 	
	public void keyPressed(int e) {
		rk.keyPressed(e);
	}
	
	public void keyReleased(int e) {
		rk.keyReleased(e);
	}

	public static void main(String[] args) {
		boolean raceTheKart = false;
		@SuppressWarnings("unused")
		Window wind = new Window(WIDTH, HEIGHT, 60, 60, "Test Kart", new TestKart(raceTheKart));
	}
}
