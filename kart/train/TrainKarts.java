package kart.train;

import java.awt.Graphics;

import kart.main.NKart;
import neuralnetwork.MNeuralNetwork;
import setup.ManagerA;
import setup.Window;

public class TrainKarts extends ManagerA {
	boolean load = false;
	public static final int layers = 5;
	public static final int[] nnodes = new int[] { NKart.nrays + 1, 10, 10, 10, 3 };// nese merr error MNN;
	int gens = 200;
	int nkarts = 30;
	double mutation = 0.02;
	double mutationRate = 0.15;
	String map = "yoshi";
	//Individual karts. (Each will create a new thread when training)
	String name[] = new String[] { "Mario"};
	//File load/save location on desktop. (type only name) 
	String loc = "MarioKartMNNF";

	TrainKart[] tk;
	RaceKarts rk;

	boolean train;

	public TrainKarts(boolean train) {
		tk = new TrainKart[name.length];
		this.train = train;
		//New thread for each kart
		if (train) {
			for (int i = 0; i < name.length; i++) {
				//Check if you are creating new or loading old
				tk[i] = new TrainKart(loc + "/" + name[i], name[i], gens, mutation, mutationRate, layers, nnodes);
				tk[i].start();
			}
		} else {
			NKart[] karts = new NKart[name.length];
			for (int i = 0; i < name.length; i++) {
				karts[i] = new NKart(MNeuralNetwork.readBMNN(loc + "/" + name[i]));
				karts[i].setX(7364);
				karts[i].setY(13692);
			}
			rk = new RaceKarts(karts, "yoshi", 1000, 800);
		}
	}

	public void tick() {
		if (!train)
			rk.tick();
	}

	public void draw(Graphics g) {
		if (!train)
			rk.draw(g);
	}

	public void keyPressed(int e) {
		rk.keyPressed(e);
	}

	public void keyReleased(int e) {
		rk.keyReleased(e);
	}

	public static void main(String[] args) {
		boolean train = false;
		//If !train create new window which loads the nkarts and tests them
		if (!train) {
			Window wind = new Window(1000, 800, 60, 60, "Train Karts", new TrainKarts(false));
		}
		//if not just run the simulations with no graphical output(only console)
		else 
			new TrainKarts(true);
	}
}
