package sockets.impl;

import sockets.Client;
import sockets.Server;
import sockets.packets.*;
import sockets.utils.Matrix;
import sockets.utils.Settings;
import view.Controller;

import java.util.Arrays;

public class CalcServer extends Server {
	Client calcClient;

	public CalcServer(Client client) {
		super("CalcServer", Settings.CALC_PORT, Controller.INSTANCE.log_2);
		this.calcClient = client;
	}

	@Override
	public void receivePacket(Packet packet) {
		if (calcClient == null) {
			return;
		}
		if (packet instanceof PacketCalcInput) {
			PacketCalcInput received = (PacketCalcInput) packet;
			Matrix left = new Matrix(received.rowEnd - received.rowBeg, received.colEnd - received.colBeg);
			Matrix right = new Matrix(left.rows, left.cols);

			try {
				PacketDataInput input = new PacketDataInput(Integer.parseInt(received.matrixLeft.toLowerCase().replace("m", "")), received);
				PacketDataOutput dataOutput = (PacketDataOutput) calcClient.sendPacketAndReceive(input);
				left.set(dataOutput);

				input = new PacketDataInput(Integer.parseInt(received.matrixRight.toLowerCase().replace("m", "")), received);
				dataOutput = (PacketDataOutput) calcClient.sendPacketAndReceive(input);
				right.set(dataOutput);
			} catch (NumberFormatException nfe) {
				sendPacket(new PacketBadRequest("Wrong matrix id"));
				return;
			}

			log(Arrays.deepToString(left.m));
			log(Arrays.deepToString(right.m));

			if (left.cols != right.rows) {
				sendPacket(new PacketBadRequest("Cannot multiply matrices"));
				return;
			}

			Matrix ret = new Matrix(left.rows, right.cols);

			ret.m = new int[ret.rows][ret.cols];

			for (int row = 0; row < ret.rows; row++) {
				ret.m[row] = new int[ret.cols];
				for (int col = 0; col < ret.cols; col++) {
					ret.m[row][col] = 0;
					for (int i = 0; i < left.rows; i++) {
						ret.m[row][col] += left.m[row][i] * right.m[i][col];
					}
				}
			}

			sendPacket(new PacketCalcOutput(ret));
		} else {
			sendPacket(new PacketBadRequest("Wrong packet"));
		}
	}
}
