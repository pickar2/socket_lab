package sockets.packets;

public class PacketBadRequest extends Packet {
	public String error;

	public PacketBadRequest(String error) {
		this.error = error;
	}

	public PacketBadRequest() {
		error = "no error";
	}

	@Override
	public String pack() {
		return gson.toJson(this);
	}

	@Override
	public void unpack(String str) {
		this.error = gson.fromJson(str, PacketBadRequest.class).error;
	}

	@Override
	public int getPacketId() {
		return 5;
	}
}
