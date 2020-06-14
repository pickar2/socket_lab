package sockets.packets;

public class PacketDataInput extends Packet {
	public static final int packetId = 3;

	static {
		packets.put(packetId, PacketDataInput.class);
	}

	public int id;
	public int colBeg;
	public int colEnd;
	public int rowBeg;
	public int rowEnd;

	public PacketDataInput() { }

	public PacketDataInput(int id, PacketCalcInput calcInput) {
		this.id = id;
		this.colBeg = calcInput.colBeg;
		this.colEnd = calcInput.colEnd;
		this.rowBeg = calcInput.rowBeg;
		this.rowEnd = calcInput.rowEnd;
	}

	@Override
	public int getPacketId() {
		return packetId;
	}

	@Override
	public String pack() {
		return id + ", " + rowBeg + ", " + rowEnd + ", " + colBeg + ", " + colEnd;
	}

	@Override
	public void unpack(String str) {
		String[] split = str.split(", ");
		id = Integer.parseInt(split[0]);
		colBeg = Integer.parseInt(split[1]);
		colEnd = Integer.parseInt(split[2]);
		rowBeg = Integer.parseInt(split[3]);
		rowEnd = Integer.parseInt(split[4]);
	}
}
