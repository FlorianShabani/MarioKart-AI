package kart.main;

import java.awt.Color;
import java.awt.Graphics2D;

import kart.entity.Kart;
import kart.train.TrainKarts;
import matrixmath.Matrix;
import neuralnetwork.MNeuralNetwork;

public class NKart extends Kart {
	
	public static final int nrays = 30;
	private int lray = 15;
	private int initRay = 70;
	private double rayGrowth = 1.35;
	private int rayrange = (int) (lray * (initRay + Math.pow(rayGrowth, lray - 1)));

	public double displ = 0;
	public int wallsHit = 0;
	
	public boolean manual = false;

	private MNeuralNetwork mnn;
	public static final int layers = TrainKarts.layers;
	public static final int[] nnodes = TrainKarts.nnodes;
	/*
	 * MNEURALNETWORK INSTRUCTION
	 * 
	 * 
	 * NRAYS INPUTS and SPEED (INPUT LAYER)
	 * 
	 * MIDDLE LAYERS
	 * 
	 * OUTPUTS : 0 : RIGHT, R >= L --> R && R >= trigger(0.8) 1 : LEFT 2 :
	 * GAS/NOTHING/BREAK, 0 - 0.33 B, 0.34 - 0.66 N, 0.66+ G
	 * 
	 */
	float trigger = 0.8f;

	public NKart(boolean manual) {
		this.manual = manual;
		mnn = new MNeuralNetwork(layers, nnodes);
	}

	public NKart(MNeuralNetwork mnn) {
		super();
		this.mnn = mnn;
	}

	public NKart(boolean manual, boolean loadGraphics) {
		super(loadGraphics);
		mnn = new MNeuralNetwork(layers, nnodes);
	}
	
	public NKart() {
		super();
		mnn = new MNeuralNetwork(layers, nnodes);
	}

	public NKart(NKart k1, NKart k2, double mutation, double mutationRate) {
		super();
		mnn = MNeuralNetwork.fuse(k1.getMnn(), k2.getMnn(), mutation, mutationRate);
	}


	public void draw(Graphics2D g) {
		super.draw(g);

		// drawLines(g);
	}
	//Visualization of what the NKart can see
	@SuppressWarnings("unused")
	private void drawLines(Graphics2D g) {
		double angle;
		double angleR;
		g.setColor(Color.yellow);
		for (int i = 0; i < nrays; i++) {
			g.drawLine(x + (width / 2), y + (height / 2),
					x + (width / 2) + (int) (Math.cos(i * Math.PI / (nrays - 1) - +Math.PI / 2 + a) * rayrange),
					y + (height / 2) + (int) (Math.sin(i * Math.PI / (nrays - 1) - +Math.PI / 2 + a) * rayrange));
			angle = i * 180 / (double) (nrays - 1);
			angleR = Math.toRadians(angle) + a - (Math.PI / 2.0);
			for (int j = 0; j < lray; j++) {// CHECK RANGE
				double m = (j + 1) * (initRay + Math.pow(rayGrowth, j));
				int mx = (int) (Math.cos(angleR) * m) + x + (width / 2);
				int my = (int) (Math.sin(angleR) * m) + y + (height / 2);
				g.drawOval(mx - 5, my - 5, 10, 10);
			}
		}
	}
	//Update state
	public void tick(Map map) {
		//get the image and feed it to the Neural Network and get a response 
		if (!manual) {
			float[] img = getImg(map);
			float[] inputs = new float[img.length + 1];
			for (int i = 0; i < img.length; i++) {
				inputs[i] = img[i];
			}
			inputs[inputs.length - 1] = (float) (velm / 17.0);////// CHECK MAX VELOCITY
			float[] r = Matrix.ffN(mnn.feedforward(inputs));
			//Update response based on NeuralNetwork response.
			if (r[0] > r[1] && r[0] >= trigger) {
				super.steerR();
			} else if (r[1] >= trigger) {
				super.steerL();
			}
			System.out.println(r[2]);
			if (r[2] <= 0.33) {
				super.brake();
			} else if (r[2] >= 0.66) {
				super.driveF();
			}
		}
		//Check Collision
		
		// Front left point, Front right point
		int frx = (int) (Math.cos(a + 0.78) * 1.414 * width / 2) + x + width / 2 - 5;
		int fry = (int) (Math.sin(a + 0.78) * 1.4140 * width / 2) + y + height / 2 - 5;
		int flx = (int) (Math.cos(a - 0.78) * 1.414 * width / 2) + x + width / 2 - 5;
		int fly = (int) (Math.sin(a - 0.78) * 1.4140 * width / 2) + y + height / 2 - 5;
		//If colliding reflect of the wall.
		if (map.isInside(frx, fry) && map.isInside(flx, fly)) {
			x -= (int) (Math.cos(a) * velm);
			y -= (int) (Math.sin(a) * velm);
			velm *= -1;
			wallsHit++;
		} else if (map.isInside(frx, fry)) {
			a -= 0.2;
			velm *= 0.6;
			wallsHit++;
		} else if (map.isInside(flx, fly)) {
			a += 0.2;
			velm *= 0.6;
			wallsHit++;
		}
		//calculate displacement
		displ += Math.abs(velm);
		
		super.tick();
	}
	//Get the image which the neural network will see
	//Shoot rays in 180 degrees and store in array how far each ray has hit
	public float[] getImg(Map map) {
		float[] img = new float[nrays];
		double angle;
		double angleR;
		ray: for (int i = 0; i < nrays; i++) {
			angle = i * 180 / (double) (nrays - 1);
			angleR = Math.toRadians(angle) + a - (Math.PI / 2.0);
			for (int j = 0; j < lray; j++) {// CHECK RANGE
				double m = (j + 1) * (initRay + Math.pow(rayGrowth, j));
				int mx = (int) (Math.cos(angleR) * m) + x + (width / 2);
				int my = (int) (Math.sin(angleR) * m) + y + (height / 2);
				if (map.isInside(mx, my)) {
					img[i] = (float) m / rayrange;
					continue ray;
				}
			}
			img[i] = 1;
		}
		return img;
	}

	public MNeuralNetwork getMnn() {
		return mnn;
	}

	public void setMnn(MNeuralNetwork mnn) {
		this.mnn = mnn;
	}
}
