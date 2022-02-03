package matrixmath;

import java.util.LinkedList;
import java.util.Random;

public class Matrix implements Cloneable {

	public float[][] matrix;
	public int rows;
	public int cols;

	Random random = new Random();

	public Matrix(int rows, int cols) {
		matrix = new float[rows][cols];
		this.rows = rows;
		this.cols = cols;
	}

	public void setZ() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				matrix[i][j] = 0;
			}
		}
	}

	public void randomize(float limit) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				matrix[i][j] = (float) (Math.random() * limit * 2) - limit;
			}
		}
	}

	public void add(Matrix m) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				matrix[i][j] += m.matrix[i][j];
			}
		}
	}

	public static float[][] multiply(Matrix m, float l) {

		float[][] result = m.matrix.clone();

		for (int i = 0; i < m.matrix.length; i++) {
			for (int j = 0; j < m.matrix[i].length; j++) {
				result[i][j] *= l;
			}
		}
		return result;
	}

	public static float[][] multiply(Matrix m1, Matrix m2) {
		float[][] m = m1.matrix.clone();

		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m[i].length; j++) {
				m[i][j] *= m2.matrix[i][j];
			}
		}
		return m;
	}

	public static float[][] add(Matrix m1, Matrix m2) {
		float[][] m = m1.matrix.clone();

		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m[i].length; j++) {
				m[i][j] += m2.matrix[i][j];
			}
		}
		return m;
	}

	public static float[][] add(Matrix m, float l) {
		float[][] ma = m.matrix.clone();

		for (int i = 0; i < ma.length; i++) {
			for (int j = 0; j < ma[i].length; j++) {
				ma[i][j] += l;
			}
		}
		return ma;
	}

	public static Matrix dot(Matrix m1, Matrix m2) {
		Matrix result = new Matrix(m1.rows, m2.cols);

		for (int i = 0; i < result.rows; i++) {
			for (int j = 0; j < result.cols; j++) {
				float sum = 0;
				for (int k = 0; k < m1.cols; k++) {
					sum += m1.matrix[i][k] * m2.matrix[k][j];
				}
				result.matrix[i][j] = sum;
			}
		}

		return result;
	}

	public static float[][] transpose(Matrix m) {
		float[][] result = new float[m.rows][m.cols];

		for (int i = 0; i < m.rows; i++) {
			for (int j = 0; j < m.cols; j++) {
				result[i][j] = m.matrix[j][i];
			}
		}
		return result;
	}

	public static Matrix fromArray(float[] array) {
		Matrix result = new Matrix(array.length, 1);
		for (int i = 0; i < array.length; i++) {
			result.matrix[i][0] = array[i];
		}
		return result;
	}

	public static Matrix fromArray(float[][] array) {
		Matrix result = new Matrix(array.length, array[0].length);
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				result.matrix[i][j] = array[i][j];
			}
		}
		return result;
	}

	public static float sigmoid(float x) {
		return (float) (1.0 / (1.0 + Math.exp(-x))) - 0.0001f;
	}

	public static float tanh(float x) {
		return (float) ((1.0 - Math.exp(-2.0 * x)) / 1.0 + Math.exp(-2.0 * x));
	}

	public float[][] toArray() {
		float[][] arr = new float[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				arr[i][j] = matrix[i][j];
			}
		}
		return arr;
	}

	public static Matrix fuse(Matrix m1, Matrix m2) {
		Matrix result = new Matrix(m1.rows, m1.cols);

		for (int i = 0; i < result.rows; i++) {
			for (int j = 0; j < result.cols; j++) {
				if (Math.random() > 0.5) {
					result.matrix[i][j] = m1.matrix[i][j];
				} else {
					result.matrix[i][j] = m2.matrix[i][j];
				}

			}
		}

		return result;
	}

	public static double map(double x, double in_min, double in_max,
			double out_min, double out_max) {
		return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
	}

	public static LinkedList<Integer> removeByValue(LinkedList<Integer> l,
			int v) {
		LinkedList<Integer> list = l;
		for (int i = 0; i < list.size(); i++) {
			int x = list.get(i);
			if (x == v) {
				list.remove(i);
				break;
			}
		}
		return list;
	}
	//feedforward to normal
	public static float[] ffN(float[][] ff) {
		float[] r = new float[ff.length];
		for(int i = 0; i < ff.length; i++) {
			r[i] = ff[i][0];
		}
		return r;
	}

	public Matrix clone() {
		try {
			return (Matrix) super.clone();
		} catch (Exception e) {
		}
		return null;
	}
	
	public static int getPool(int[] fit) {
		double total = 0;
		for (int i : fit) {
			total += i;
		}
		int at = (int)(Math.random() * total);
		for (int i = 0; i < fit.length; i++) {
			at -= fit[i];
			if (at <= 0) {
				return i;
			}
		}
		return 0;
	}
}
