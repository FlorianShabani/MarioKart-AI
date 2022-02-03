package kart.train;

import java.util.Arrays;

import kart.main.NKart;
import neuralnetwork.MNeuralNetwork;

public class TrainKart extends Thread {

	// The class creates or loads already existing karts and trains them for the
	// specified amount of generations
	// and saves it either to the location it loaded from or the specified location
	String saveLoc, loadLoc, name;
	int gens, nKarts;

	double mutation, mutationRate;
	int layers;
	int[] nnodes;
	NKart[] karts;

	// Constructor for the already existing karts file
	public TrainKart(String loadLoc, String name, int gens, double mutation, double mutationRate, int layers,
			int[] nnodes) {
		super(name);
		this.saveLoc = loadLoc;
		this.name = name;
		this.loadLoc = loadLoc;
		this.gens = gens;
		this.mutation = mutation;
		this.mutationRate = mutationRate;
		this.layers = layers;
		this.nnodes = nnodes;
		MNeuralNetwork[] mnns = MNeuralNetwork.readAMNN(loadLoc);
		karts = new NKart[mnns.length];
		for (int i = 0; i < karts.length; i++) {
			karts[i] = new NKart(mnns[i]);
		}
		nKarts = karts.length;
	}

	// Constructor for a new kart.
	public TrainKart(String saveLoc, String name, int nKarts, int gens, double mutation, double mutationRate,
			int layers, int[] nnodes) {
		super();
		this.saveLoc = saveLoc;
		this.name = name;
		this.gens = gens;
		this.nKarts = nKarts;
		this.mutation = mutation;
		this.mutationRate = mutationRate;
		this.layers = layers;
		this.nnodes = nnodes;
		karts = new NKart[nKarts];
		for (int i = 0; i < karts.length; i++) {
			karts[i] = new NKart(false, false);
		}
	}

	public void draw() {

	}

	/*
	 * Method creates a loop where each cycle it creates a new environment, runs the
	 * simulation of the karts and based on the score it creates a new generation of
	 * karts with *genetic material* of the previous generation Lasts, it saves the
	 * karts information to the desktop using MNeuralNetwork method
	 */
	@Override
	public void run() {
		int[] score = null;
		for (int i = 0; i < gens; i++) {
			System.out.println(i);
			//Create new Environment and run it
			RaceKarts race = new RaceKarts(karts, "yoshi");
			score = race.runSim();
			//Create new karts based on the last generation. Use MNeuralNetwork constructor which uses two MNN 
			NKart[] babyKarts = new NKart[nKarts];
			for (int j = 0; j < nKarts; j++) {
				babyKarts[j] = new NKart(karts[getPoolSquared(score)], karts[getPoolSquared(score)], mutation,
						mutationRate);
			}
			karts = babyKarts;
			System.out.println(name + " " + Arrays.toString(score));
		}
		MNeuralNetwork[] mnns = new MNeuralNetwork[nKarts];
		for (int i = 0; i < nKarts; i++) {
			mnns[i] = karts[i].getMnn();
		}
		// Write to File All MNeuralNetworks
		MNeuralNetwork.writeToFAMNN(layers, nnodes, saveLoc, mnns, score);
	}
	//method used to choose a kart MNN from the pool based on the score it got
	//better when karts are still relatively new
	public static int getPool(int[] fit) {
		double total = 0;
		double at = 0;
		for (int i : fit) {
			total += i;
		}
		double num = Math.random() * total;
		for (int i = 0; i < fit.length; i++) {
			at += fit[i];
			if (at >= num) {
				return i;
			}
		}
		return 0;
	}
	//method used to choose a kart MNN from the pool based on the square of the score
	//(Better for when karts are mastering their skills)
	public static int getPoolSquared(int[] fit) {
		double total = 0;
		double at = 0;
		for (int i : fit) {
			total += i * i;
		}
		double num = Math.random() * total;
		for (int i = 0; i < fit.length; i++) {
			at += fit[i] * fit[i];
			if (at >= num) {
				return i;
			}
		}
		return 0;
	}
}
