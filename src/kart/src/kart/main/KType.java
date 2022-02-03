package kart.main;

import java.util.ArrayList;

import matrixmath.Matrix;

public class KType {
	
	private ArrayList<NKart> karts = new ArrayList<NKart>();
	String name;
	int sub;
	double mutation, mutationRate;
	int[] nnodes;
	int layers;
	int[] fitness;
	
	public KType(String name, int sub, int layers, int[] nnodes, double mutation, double mutationRate) {
		this.name = name;
		this.sub = sub;
		this.layers = layers;
		this.nnodes = nnodes;
		this.mutation = mutation;
		this.mutationRate = mutationRate;
		fitness = new int[sub];
	}
	
	public KType(String location) {
		//load KType
	}
	
	public void init() {
		NKart ikart = new NKart();
		for(int i = 0; i < sub; i++) {
			karts.add(new NKart(ikart, ikart, mutation, mutationRate));
		}
	}
	
	public NKart pickRandom() {//MOVE TO TRAIN
		return karts.get((int) (Math.floor((Math.random() * sub))));
	}
	
	public void  newGen(int[] fitness) {
		this.fitness = fitness;
		ArrayList<NKart> newkarts = new ArrayList<NKart>();
		for(int i = 0; i < sub; i++) {
			NKart k1mnn = karts.get(Matrix.getPool(fitness));
			NKart k2mnn = karts.get(Matrix.getPool(fitness));
			newkarts.add(new NKart(k1mnn, k2mnn, mutation, mutationRate));
		}
		karts.clear();
		for(NKart k : newkarts) {
			karts.add(k);
		}
	}
	
	public void saveType(String location) {
		
	}
}
