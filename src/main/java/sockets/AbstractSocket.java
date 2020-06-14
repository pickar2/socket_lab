package sockets;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import sockets.packets.Packet;
import sockets.packets.PacketCloseConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public abstract class AbstractSocket {
	public BufferedReader reader;
	public PrintWriter writer;
	public String name;
	public Socket socket;
	public Packet lastPacket;
	public boolean waiting = false;
	public boolean takenPacket = false;
	public TextArea logOutput;
	public boolean running = true;

	public void sendPacket(Packet packet) {
		String packed = packet.getPacketId() + "~" + packet.pack();
		log(name + ": Sending: " + packed);
		writer.println(packed);
	}

	public void log(String string) {
		System.out.println(string);
		if (logOutput != null) {
			Platform.runLater(() -> logOutput.appendText(string + "\n"));
		}
	}

	public Packet sendPacketAndReceive(Packet packet) {
		waiting = true;
		sendPacket(packet);

		String received = null;
		try {
			synchronized (this) {
				if (takenPacket) {
					takenPacket = false;
					log(name + ": Received and resending: " + lastPacket.pack());
					return lastPacket;
				}

				received = reader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (received == null) {
			return null;
		}
		log(name + ": Received and resending: " + received);
		return Packet.fromString(received);
	}

	public abstract void receivePacket(Packet packet);

	public void open() {
		try {
			String received;
			while (running) {
				synchronized (this) {
					if (takenPacket) {
						continue;
					}
					received = reader.readLine();
					if (received == null) {
						continue;
					}
					log(name + ": Received: " + received);
					lastPacket = Packet.fromString(received);
					if (waiting && !takenPacket) {
						waiting = false;
						takenPacket = true;
						continue;
					}
				}

				if (lastPacket instanceof PacketCloseConnection) {
					break;
				} else {
					receivePacket(lastPacket);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public abstract void close();
}
