package sockets.packets;

public class PacketCalcInput extends Packet {
	public static final int packetId = 1;
	public int colBeg;
	public int colEnd;
	public int rowBeg;
	public int rowEnd;
	public String matrixLeft;
	public String matrixRight;

	public PacketCalcInput() { }

	public PacketCalcInput(int colBeg, int colEnd, int rowBeg, int rowEnd, String matrixLeft, String matrixRight) {
		this.colBeg = colBeg;
		this.colEnd = colEnd;
		this.rowBeg = rowBeg;
		this.rowEnd = rowEnd;
		this.matrixLeft = matrixLeft;
		this.matrixRight = matrixRight;
	}

	public void set(PacketCalcInput packet) {
		this.colBeg = packet.colBeg;
		this.colEnd = packet.colEnd;
		this.rowBeg = packet.rowBeg;
		this.rowEnd = packet.rowEnd;
		this.matrixLeft = packet.matrixLeft;
		this.matrixRight = packet.matrixRight;
	}

	@Override
	public String pack() {
		return gson.toJson(this);
	}

	@Override
	public void unpack(String str) {
		this.set(gson.fromJson(str, PacketCalcInput.class));
	}

	@Override
	public int getPacketId() {
		return packetId;
	}
}
