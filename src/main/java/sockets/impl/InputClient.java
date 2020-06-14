package sockets.impl;

import sockets.Client;
import sockets.packets.Packet;
import sockets.packets.PacketBadRequest;
import sockets.packets.PacketCalcOutput;
import sockets.utils.Settings;
import view.Controller;

import java.util.Arrays;

public class InputClient extends Client {
	public InputClient() {
		super("Input", Settings.HOST, Settings.CALC_PORT, Controller.INSTANCE.log_1);
	}

	@Override
	public void receivePacket(Packet packet) {
		if (packet instanceof PacketCalcOutput) {
			log(Arrays.deepToString(((PacketCalcOutput) packet).m));
		} else if (packet instanceof PacketBadRequest) {
			log(((PacketBadRequest) packet).error);
		}
	}
}
