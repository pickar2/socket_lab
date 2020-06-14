package sockets;

import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;

public abstract class Server extends AbstractSocket {
	public ServerSocket serverSocket;

	public Server(String name, int port, TextArea logOutput) {
		try {
			this.name = name;
			this.logOutput = logOutput;
			serverSocket = new ServerSocket(port);
			log(name + ": Waiting for client to connect...");
			socket = serverSocket.accept();
			log(name + ": Connection established!");

			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void close() {
		try {
			socket.close();
			log(name + ": Client has disconnected!");
			serverSocket.close();
			log(name + ": Closing!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
