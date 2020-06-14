package sockets.packets;

public class PacketCloseConnection extends Packet {
	public static final int packetId = 0;

	static {
		packets.put(packetId, PacketCloseConnection.class);
	}

	@Override
	public int getPacketId() {
		return packetId;
	}

	@Override
	public String pack() {
		return "empty";
	}

	@Override
	public void unpack(String str) { }
}
