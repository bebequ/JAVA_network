import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerProgram {
	public static void main(String args[])  {
		System.out.println("Server Program!!");

		ServerSocket listener = null;
		String line;
		BufferedReader is;
		BufferedWriter os;
		Socket socketOfServer = null;
		
		try {
			listener = new ServerSocket(9999);
		} catch (IOException e) {
			System.out.println(e);
			System.exit(1);
		}

		try {
			System.out.println("Server is waiting to accept user...");

			socketOfServer = listener.accept();
			System.out.println("Accept a client!");

			is = new BufferedReader(new InputStreamReader(socketOfServer.getInputStream()));
			os = new BufferedWriter(new OutputStreamWriter(socketOfServer.getOutputStream()));
	
			while(true) {
				line = is.readLine();
				if (line.equals("QUIT")) {
					os.write("OK");
					os.newLine();
					os.flush();
					break;
				}
				System.out.println("Recieved : " +  line);
				
				os.write(">> " + line);
				os.newLine();
				os.flush();

			}
		} catch (IOException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		System.out.println("Server stopped!");
	}
}
