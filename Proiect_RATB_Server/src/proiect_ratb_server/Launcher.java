package proiect_ratb_server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Launcher {

	private static ArrayList<Thread> fire = new ArrayList<Thread>();

	public static void main(String[] args) throws IOException {
		boolean wait = true;
		ServerSocket ss = new ServerSocket(1342);
		Socket socket = null;
		while (wait) {
			socket = ss.accept();
			Thread t = new Thread(new Fir(socket));
			fire.add(t);
			t.start();
		}

		socket.close();
		if (ss != null)
			ss.close();
	}
}
