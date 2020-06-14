package sockets.packets;

import java.util.Arrays;

public class PacketDataOutput extends Packet {
	public static final int packetId = 4;

	static {
		packets.put(packetId, PacketDataOutput.class);
	}

	public int[] ints;

	public PacketDataOutput() { }

	public PacketDataOutput(int[] ints) {
		this.ints = ints;
	}

	@Override
	public int getPacketId() {
		return packetId;
	}

	@Override
	public String pack() {
		return Arrays.toString(ints);
	}

	@Override
	public void unpack(String str) {
		ints = Arrays.stream(str.substring(1, str.length() - 1).split(", ")).mapToInt(Integer::parseInt).toArray();
	}
}
