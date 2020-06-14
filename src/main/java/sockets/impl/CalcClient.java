package sockets.impl;

import sockets.Client;
import sockets.packets.Packet;
import sockets.utils.Settings;
import view.Controller;

public class CalcClient extends Client {
	public CalcClient() {
		super("CalcClient", Settings.HOST, Settings.DATA_PORT, Controller.INSTANCE.log_2);
	}

	@Override
	public void receivePacket(Packet packet) {
		//void
	}
}
