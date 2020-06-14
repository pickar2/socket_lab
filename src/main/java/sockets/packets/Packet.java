package sockets.packets;

import com.google.gson.Gson;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public abstract class Packet {
	public static final Map<Integer, Class<? extends Packet>> packets = new HashMap<>();
	protected static final Gson gson = new Gson();

	static {
		packets.put(0, PacketCloseConnection.class);
		packets.put(1, PacketCalcInput.class);
		packets.put(2, PacketCalcOutput.class);
		packets.put(3, PacketDataInput.class);
		packets.put(4, PacketDataOutput.class);
		packets.put(5, PacketBadRequest.class);
	}

	public static Packet fromString(String str) {
		String[] split = str.split("~");

		final int packetId = Integer.parseInt(split[0]);
		try {
			Packet packet = (Packet) packets.get(packetId).getDeclaredConstructors()[0].newInstance();
			packet.unpack(split[1]);
			return packet;
		} catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	public abstract String pack();

	public abstract void unpack(String str);

	public abstract int getPacketId();
}
