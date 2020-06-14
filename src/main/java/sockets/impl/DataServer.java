package sockets.impl;

import sockets.Server;
import sockets.packets.Packet;
import sockets.packets.PacketDataInput;
import sockets.packets.PacketDataOutput;
import sockets.utils.Settings;
import view.Controller;

public class DataServer extends Server {
	public DataServer() {
		super("Data", Settings.DATA_PORT, Controller.INSTANCE.log_3);
	}

	@Override
	public void receivePacket(Packet packet) {
		if (packet instanceof PacketDataInput) {
			PacketDataInput received = ((PacketDataInput) packet);

			int[] ints = new int[(received.rowEnd - received.rowBeg) * (received.colEnd - received.colBeg)];
			int index = 0;
			for (int col = received.colBeg; col < received.colEnd; col++) {
				for (int row = received.rowBeg; row < received.rowEnd; row++) {
					ints[index++] = (int) Math.floor((Math.cos(col / 8.0) + Math.sin((double) row / received.id)) * 10);
				}
			}
			sendPacket(new PacketDataOutput(ints));
		}
	}
}
