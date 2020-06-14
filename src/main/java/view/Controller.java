package view;

import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sockets.Client;
import sockets.Server;
import sockets.impl.CalcClient;
import sockets.impl.CalcServer;
import sockets.impl.DataServer;
import sockets.impl.InputClient;
import sockets.packets.Packet;
import sockets.packets.PacketCalcInput;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Controller {
	public static Controller INSTANCE;
	public TextArea log_1;
	public TextArea log_2;
	public TextArea log_3;
	public TextField input_rowBeg;
	public TextField input_rowEnd;
	public TextField input_colBeg;
	public TextField input_colEnd;
	public TextField input_mLeft;
	public TextField input_mRight;
	public boolean dataServerRunning = false;
	public boolean calcServerRunning = false;
	public boolean calcClientRunning = false;
	public boolean inputClientRunning = false;
	public ExecutorService threadPool = Executors.newFixedThreadPool(4);
	public Client inputClient;
	public Client calcClient;
	public Server calcServer;
	public Server dataServer;

	public Controller() {

	}

	public void initialize() {
		INSTANCE = this;
	}

	private boolean allRunning() {
		return dataServerRunning && calcServerRunning && calcClientRunning && inputClientRunning;
	}

	public void sendRequest(ActionEvent actionEvent) {
		if (allRunning()) {
			try {
				Packet packet = new PacketCalcInput(
						Integer.parseInt(input_rowBeg.getText()),
						Integer.parseInt(input_rowEnd.getText()),
						Integer.parseInt(input_colBeg.getText()),
						Integer.parseInt(input_colEnd.getText()),
						input_mLeft.getText(),
						input_mRight.getText()
				);
				inputClient.sendPacket(packet);
			} catch (NumberFormatException nfe) {
				inputClient.log("Error: Empty field!");
			}
		}
	}

	public void startCalcClient(ActionEvent actionEvent) {
		if (!calcClientRunning) {
			calcClientRunning = true;
			threadPool.execute(() -> {
				calcClient = new CalcClient();
				calcClient.open();
				calcClient.close();
				calcClientRunning = false;
			});
		}
	}

	public void startCalcServer(ActionEvent actionEvent) {
		if (!calcServerRunning) {
			calcServerRunning = true;
			threadPool.execute(() -> {
				calcServer = new CalcServer(calcClient);
				calcServer.open();
				calcServer.close();
				calcServerRunning = false;
			});
		}
	}

	public void startDataServer(ActionEvent actionEvent) {
		if (!dataServerRunning) {
			dataServerRunning = true;
			threadPool.execute(() -> {
				dataServer = new DataServer();
				dataServer.open();
				dataServer.close();
				dataServerRunning = false;
			});
		}
	}

	public void startInputClient(ActionEvent actionEvent) {
		if (!inputClientRunning) {
			inputClientRunning = true;
			threadPool.execute(() -> {
				inputClient = new InputClient();
				inputClient.open();
				inputClient.close();
				inputClientRunning = false;
			});
		}
	}
}
