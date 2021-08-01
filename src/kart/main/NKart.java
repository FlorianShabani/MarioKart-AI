package kart.main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

import kart.entity.Entity;
import kart.entity.Kart;
import matrixmath.Matrix;
import neuralnetwork.MNeuralNetwork;

public class NKart extends Kart {

	private int nrays = 30;
	private float lray = 100;
	private float rayrange = 700;
	public boolean isdead = false;

	private boolean manual = false;

	private MNeuralNetwork mnn;
	private int layers;
	private int[] nnodes;; // Ma von
	/*
	 * MNN INSTRUCTION
	 * 
	 * 
	 * NRAYS INPUTS and SPEED
	 * 
	 * MIDDLE LAYERS
	 * 
	 * OUTPUTS : 0 : RIGHT, R >= L --> R && R >= trigger(0.8) 1 : LEFT 2 :
	 * GAS/NOTHING/BREAK, 0 - 0.33 B, 0.34 - 0.66 N, 0.66+ G
	 * 
	 */
	float trigger = 0.5f;

	public NKart(boolean manual) {
		this.manual = true;
		layers = 4;
		nnodes = new int[] { nrays + 1, 6, 6, 3 };// nese merr error MNN
		mnn = new MNeuralNetwork(layers, nnodes);
	}

	public NKart(int layers, int[] nnodes) {
		super();
		this.layers = layers;
		nnodes[0] = nrays + 1;
		this.nnodes = nnodes;
		mnn = new MNeuralNetwork(layers, nnodes);
	}

	public NKart() {
		super();
		layers = 4;
		nnodes = new int[] { nrays + 1, 6, 6, 3 };// nese merr error MNN
		mnn = new MNeuralNetwork(layers, nnodes);
	}

	public NKart(NKart k1, NKart k2, double mutation, double mutationRate) {
		super();
		mnn = MNeuralNetwork.fuse(k1.getMnn(), k2.getMnn(), mutation, mutationRate);
	}

	public void draw(Graphics2D g) {
		super.draw(g);
		drawLines(g);
	}

	@SuppressWarnings("unused")
	private void drawLines(Graphics2D g) {
		g.setColor(new Color(0, 0, 255, 100));
		for (int i = 0; i < nrays; i++) {
			g.drawLine(x + (width / 2), y + (height / 2),
					x + (width / 2) + (int) (Math.cos(i * Math.PI / (nrays - 1) - +Math.PI / 2 + a) * rayrange),
					y + (height / 2) + (int) (Math.sin(i * Math.PI / (nrays - 1) - +Math.PI / 2 + a) * rayrange));
		}
	}

	public void tick(List<Entity> entities) {
		if (!manual) {
			float[] img = getImg(entities);
			float[] inputs = new float[img.length + 1];
			for (int i = 0; i < img.length; i++) {
				inputs[i] = img[i];
			}
			inputs[inputs.length - 1] = (float) (velm / 17.0);////// CHECK
			float[] r = Matrix.ffN(mnn.feedforward(inputs));

			if (r[0] > r[1] && r[0] >= trigger) {
				super.steerR();
			} else if (r[1] >= trigger) {
				super.steerL();
			}

			if (r[2] <= 0.33) {
				super.brake();
			} else if (r[2] >= 0.66) {
				super.driveF();
			}
			super.applyFriction();
		}
		super.tick();
	}

	public float[] getImg(List<Entity> entities) {
		float[] img = new float[nrays];
		double angle;
		double angleR;
		int inc = (int) (rayrange / lray);
		ray: for (int i = 0; i < nrays; i++) {
			angle = i * 180 / (double) (nrays - 1);
			angleR = Math.toRadians(angle) + a - (Math.PI / 2.0);
			for (int j = 0; j < lray; j++) {// CHECK RANGE
				int m = (j + 1) * inc;
				int mx = (int) (Math.cos(angleR) * m) + x + (width / 2);
				int my = (int) (Math.sin(angleR) * m) + y + (height / 2);
				for (Entity e : entities) {
					if (e.isInside(mx, my)) {
						img[i] = m / (float) rayrange;
						continue ray;
					}
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
