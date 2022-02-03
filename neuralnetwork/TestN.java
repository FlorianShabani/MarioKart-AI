package neuralnetwork;

import java.util.LinkedList;

public class TestN {
	public static void main(String[] args) {
		/*
		 * long stime = System.currentTimeMillis();
		 * 
		 * int layers = 10; int[] nnodes = { 2, 120, 100, 140, 170, 100, 100,
		 * 100, 100, 100 };
		 * 
		 * MNeuralNetwork mnn = new MNeuralNetwork(layers, nnodes);
		 * MNeuralNetwork mnn1 = new MNeuralNetwork(layers, nnodes);
		 * 
		 * MNeuralNetwork mnn2 = MNeuralNetwork.fuse(mnn, mnn1, 0.01);
		 * 
		 * float[][] o = mnn2.feedforward(new float[] { 5, 4 });
		 * 
		 * int[][] nums = { { 0, 0 }, { 1, 0 }, { 2, 0 }, { 3, 0 }, { 4, 0 }, {
		 * 5, 0 }, { 6, 0 }, { 7, 0 } };
		 * 
		 * for (int i = 0; i < o.length; i++) { o[i][0] = (float)
		 * Math.floor(o[i][0] * 8); for (int j = 0; j < nums.length; j++) { if
		 * (o[i][0] == nums[j][0]) { nums[j][1]++; } } } for(int i = 0; i <
		 * nums.length; i++) { System.out.print(nums[i][1] + " "); }
		 * System.out.println();
		 * 
		 * long etime = System.currentTimeMillis(); System.out.println(etime -
		 * stime);
		 */
		/*
		 * for (int i = 0; i < 10000; i++) { float f = Matrix.sigmoid((float)
		 * (Math.random() * 100) - 200); if (f > 1) System.out.println(f); }
		 */
		
		LinkedList<Integer> indexes = new LinkedList<Integer>();
		for(int i = 0; i < 10; i++) {
			indexes.add(i);
		}
		indexes.remove(3);
		indexes.remove(7);
		for(int i : indexes) {
			System.out.print(i + " ");
		}
	}
}
