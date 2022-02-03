package neuralnetwork;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import matrixmath.Matrix;

public class MNeuralNetwork {
	private int layers;
	private int[] nnodes;
	private Matrix[] weights;
	private Matrix[] biases;
	private Matrix[] nodes;
	private int[] connections;
	private int[] nbiases;

	public MNeuralNetwork(int layers, int[] nnodes) {
		this.layers = layers;
		this.nnodes = nnodes;
		weights = new Matrix[layers - 1];
		biases = new Matrix[layers - 1];
		nodes = new Matrix[layers];

		connections = new int[layers - 1];
		nbiases = new int[layers - 1];

		for (int i = 0; i < layers - 1; i++) {
			weights[i] = new Matrix(nnodes[i + 1], nnodes[i]);
			weights[i].randomize(1);
			biases[i] = new Matrix(nnodes[i + 1], 1);
			biases[i].randomize(1);
		}

		for (int i = 0; i < connections.length; i++) {
			connections[i] = weights[i].cols * weights[i].rows;
			nbiases[i] = biases[i].rows;
		}
	}

	public float[][] feedforward(float[] inputs) {
		float[][] output = new float[nnodes[nnodes.length - 1]][0];
		nodes[0] = Matrix.fromArray(inputs);
		for (int i = 1; i < layers; i++) {
			nodes[i] = Matrix.dot(weights[i - 1], nodes[i - 1]);
			nodes[i].add(biases[i - 1]);
			for (int j = 0; j < nodes[i].rows; j++) {
				nodes[i].matrix[j][0] = Matrix.sigmoid(nodes[i].matrix[j][0]);
			}
		}
		output = nodes[nodes.length - 1].toArray();
		return output;
	}

	public static MNeuralNetwork fuse(MNeuralNetwork n1, MNeuralNetwork n2, double mutation) {
		MNeuralNetwork result = new MNeuralNetwork(n1.getLayers(), n1.getNnodes());
		Matrix[] fweights = new Matrix[n1.getWeights().length];
		Matrix[] fbiases = new Matrix[n1.getBiases().length];
		for (int i = 0; i < fweights.length; i++) {
			fweights[i] = Matrix.fuse(n1.getWeights()[i], n2.getWeights()[i]);
			fbiases[i] = Matrix.fuse(n1.getBiases()[i], n2.getBiases()[i]);

			for (int j = 0; j < n1.getConnections()[i]; j++) {
				if (Math.random() < mutation) {
					fweights[i].matrix[(int) (Math.random() * fweights[i].rows)][(int) (Math.random()
							* fweights[i].cols)] += (Math.random() * 2 - 1) / 5.0;
				}
			}
			for (int j = 0; j < n1.getNbiases()[i]; j++) {
				if (Math.random() < mutation) {
					fbiases[i].matrix[(int) (Math.random() * fbiases[i].rows)][0] += (Math.random() * 2 - 1) / 5.0;
				}
			}
		}
		result.setWeights(fweights);
		result.setBiases(fbiases);
		return result;
	}

	public static MNeuralNetwork fuse(MNeuralNetwork n1, MNeuralNetwork n2, double mutation, double mutationRate) {
		MNeuralNetwork result = new MNeuralNetwork(n1.getLayers(), n1.getNnodes());
		Matrix[] fweights = new Matrix[n1.getWeights().length];
		Matrix[] fbiases = new Matrix[n1.getBiases().length];
		for (int i = 0; i < fweights.length; i++) {
			fweights[i] = Matrix.fuse(n1.getWeights()[i], n2.getWeights()[i]);
			fbiases[i] = Matrix.fuse(n1.getBiases()[i], n2.getBiases()[i]);

			for (int j = 0; j < n1.getConnections()[i]; j++) {
				if (Math.random() < mutation) {
					fweights[i].matrix[(int) (Math.random() * fweights[i].rows)][(int) (Math.random()
							* fweights[i].cols)] += (Math.random() * 2 - 1) * mutationRate;
				}
			}
			for (int j = 0; j < n1.getNbiases()[i]; j++) {
				if (Math.random() < mutation) {
					fbiases[i].matrix[(int) (Math.random() * fbiases[i].rows)][0] += (Math.random() * 2 - 1)
							* mutationRate;
				}
			}
		}
		result.setWeights(fweights);
		result.setBiases(fbiases);
		return result;
	}

	/*
	 * try {
	 * System.out.println(Files.readString(Paths.get(System.getProperty("user.home")
	 * + "/Desktop/" + "hello" + ".txt"))); } catch (IOException e) {
	 * e.printStackTrace(); }
	 */

	public static void writeToFMNN(int layers, int[] nnodes, String filen, MNeuralNetwork n) {
		try {
			File f = new File(System.getProperty("user.home") + "/Desktop/" + filen + ".txt");
			f.createNewFile();
			FileWriter fw = new FileWriter(f.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);

			Matrix[] weights = n.getWeights();
			Matrix[] biases = n.getBiases();

			bw.write(layers + "/");
			for (int i = 0; i < layers; i++) {
				bw.write(nnodes[i] + "/");
			}
			bw.write("]");
			for (int i = 0; i < weights.length; i++) {
				for (int j = 0; j < weights[i].rows; j++) {
					for (int k = 0; k < weights[i].cols; k++) {
						bw.write(weights[i].matrix[j][k] + "/");
					}
					bw.write("!");
				}
				bw.write("#");
			}
			bw.write("]");

			for (int i = 0; i < biases.length; i++) {
				for (int j = 0; j < biases[i].rows; j++) {
					bw.write(biases[i].matrix[j][0] + "/");
				}
				bw.write("!");
			}

			bw.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void writeToFAMNN(int layers, int[] nnodes, String filen, MNeuralNetwork[] nn, int[] fitness) {
		new File(System.getProperty("user.home") + "/Desktop/" + filen).mkdirs();
		File info = new File(System.getProperty("user.home") + "/Desktop/" + filen + "/info.txt");
		FileWriter fw = null;
		try {
			fw = new FileWriter(info.getAbsoluteFile());
		} catch (IOException e) {
			System.out.println("here");
			e.printStackTrace();
		}
		BufferedWriter bw = new BufferedWriter(fw);

		try {
			bw.write(nn.length + "]");
			for (int i = 0; i < fitness.length; i++) {
				bw.write(fitness[i] + "/");
			}
			bw.close();
		} catch (IOException e) {
			System.out.println("here");
			e.printStackTrace();
		}

		for (int i = 0; i < nn.length; i++) {
			writeToFMNN(layers, nnodes, filen + "/" + i, nn[i]);
		}
	}

	public static void writeToFAMNN(int layers, int[] nnodes, String filen, MNeuralNetwork[] nn) {
		new File(System.getProperty("user.home") + "/Desktop/" + filen).mkdirs();
		File info = new File(System.getProperty("user.home") + "/Desktop/" + filen + "/info.txt");
		FileWriter fw = null;
		try {
			fw = new FileWriter(info.getAbsoluteFile());
		} catch (IOException e) {
			System.out.println("here");
			e.printStackTrace();
		}
		BufferedWriter bw = new BufferedWriter(fw);

		for (int i = 0; i < nn.length; i++) {
			writeToFMNN(layers, nnodes, filen + "/" + i, nn[i]);
		}
	}

	public static String readF(String name) {
		String read = "";
		try {
			File f = new File(System.getProperty("user.home") + "/Desktop" + "/" + name + ".txt");
			FileReader fw = new FileReader(f.getAbsoluteFile());
			BufferedReader br = new BufferedReader(fw);
			String line;
			while ((line = br.readLine()) != null) {
				read += line;
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return read;
	}

	public static MNeuralNetwork readMNN(String loc) {
		String data = readF(loc);

		String[] data2 = data.split("]");
		String MNNinfo = data2[0];
		String MNNweights = data2[1];
		String MNNbiases = data2[2];

		String[] info = MNNinfo.split("/");
		int layers = Integer.parseInt(info[0]);
		int[] nnodes = new int[layers];
		for (int i = 0; i < layers; i++) {
			nnodes[i] = Integer.parseInt(info[i + 1]);
		}

		MNeuralNetwork MNN = new MNeuralNetwork(layers, nnodes);

		String[] sweights = MNNweights.split("#");
		String[] sbiases = MNNbiases.split("!");
		Matrix[] weights = new Matrix[sweights.length];
		Matrix[] biases = new Matrix[sbiases.length];

		for (int i = 0; i < layers - 1; i++) {
			weights[i] = new Matrix(nnodes[i + 1], nnodes[i]);
			biases[i] = new Matrix(nnodes[i + 1], 1);
		}

		for (int i = 0; i < layers - 1; i++) {
			Matrix w = weights[i];
			Matrix b = biases[i];
			String[] bias = sbiases[i].split("/");

			for (int j = 0; j < b.rows; j++) {

				b.matrix[j][0] = Float.parseFloat(bias[j]);
			}

			String[] sw = sweights[i].split("!");
			String[][] ssw = new String[w.rows][w.cols];
			for (int j = 0; j < w.rows; j++) {
				ssw[j] = sw[j].split("/");
				for (int k = 0; k < w.cols; k++) {
					w.matrix[j][k] = Float.parseFloat(ssw[j][k]);
				}
			}
		}
		MNN.setBiases(biases);
		MNN.setWeights(weights);

		return MNN;
	}

	public static MNeuralNetwork[] readAMNN(String loc) {
		String read = "";
		try {
			File f = new File(System.getProperty("user.home") + "/Desktop/" + loc + "/info.txt");
			FileReader fw = new FileReader(f.getAbsoluteFile());
			BufferedReader br = new BufferedReader(fw);
			String line;
			while ((line = br.readLine()) != null) {
				read += line;
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] info = read.split("]");
		int n = Integer.parseInt(info[0]);
		MNeuralNetwork[] nn = new MNeuralNetwork[n];
		for (int i = 0; i < n; i++) {
			nn[i] = readMNN(loc + "/" + i);
		}
		return nn;
	}

	/*
	 * public static void save(String file, int sub, int layers, int[] nnodes, int[]
	 * fitness) { MNeuralNetwork[] mnn = new MNeuralNetwork[sub];
	 * 
	 * for (int i = 0; i < sub; i++) { // mnn[i] = players[i].getMnn(); }
	 * 
	 * MNeuralNetwork.writeToFAMNN(layers, nnodes, file, mnn, fitness); }
	 */
	// READ BEST MNN
	public static MNeuralNetwork readBMNN(String loc) {
		String read = readF(loc + "/info");

		String[] info = read.split("]");
		String[] sfitness = info[1].split("/");
		int[] fitness = new int[sfitness.length];
		int ind = 0;
		int max = 0;
		for (int i = 0; i < fitness.length; i++) {
			fitness[i] = Integer.parseInt(sfitness[i]);
			if (fitness[i] > max) {
				max = fitness[i];
				ind = i;
			}
		}
		System.out.println(ind);
		return readMNN(loc + "/" + ind);
	}

	public void backP() {

	}

	public Matrix[] getWeights() {
		return weights;
	}

	public void setWeights(Matrix[] weights) {
		this.weights = weights;
	}

	public Matrix[] getBiases() {
		return biases;
	}

	public void setBiases(Matrix[] biases) {
		this.biases = biases;
	}

	public int getLayers() {
		return layers;
	}

	public void setLayers(int layers) {
		this.layers = layers;
	}

	public int[] getNnodes() {
		return nnodes;
	}

	public void setNnodes(int[] nnodes) {
		this.nnodes = nnodes;
	}

	public int[] getConnections() {
		return connections;
	}

	public void setConnections(int[] connections) {
		this.connections = connections;
	}

	public int[] getNbiases() {
		return nbiases;
	}

	public void setNbiases(int[] nbiases) {
		this.nbiases = nbiases;
	}

}
