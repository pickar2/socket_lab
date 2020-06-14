package sockets.utils;

import sockets.packets.PacketDataOutput;

public class Matrix {
	public final int rows, cols;
	public int[][] m;

	public Matrix(int rows, int cols) {
		this.cols = cols;
		this.rows = rows;
	}

	public void set(PacketDataOutput data) {
		m = new int[rows][cols];

		int index = 0;
		for (int row = 0; row < rows; row++) {
			m[row] = new int[cols];
			for (int col = 0; col < cols; col++) {
				m[row][col] = data.ints[index++];
			}
		}
	}
}
