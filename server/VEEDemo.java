import java.io.*;

public class VEEDemo {
	public static void main(String argv[]) {
		VEEServer server = null;
		try {
			server = new VEEServer(9999);
		} catch (IOException e) {
			System.out.println(e);
		}
		server.start();


		try {
			server.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
