package sockets.packets;

import sockets.utils.Matrix;

public class PacketCalcOutput extends Packet {
	public static final int packetId = 2;

	static {
		packets.put(packetId, PacketCalcOutput.class);
	}

	// row, col
	public int[][] m;

	public PacketCalcOutput() {}

	public PacketCalcOutput(Matrix matrix) {
		m = matrix.m;
	}

	@Override
	public int getPacketId() {
		return packetId;
	}

	@Override
	public String pack() {
		return gson.toJson(this);
	}

	@Override
	public void unpack(String str) {
		m = gson.fromJson(str, PacketCalcOutput.class).m;
	}
}
