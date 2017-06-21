import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class VEEServer extends Thread {
	private Socket socketOfServer = null;
	private ServerSocket listener = null;
	private boolean isRunning = false;
	public VEEServer(int port ) throws IOException {
		System.out.println("Server Program!!");
		listener = new ServerSocket(port);
		listener.setSoTimeout(10000);
		isRunning  = true;
	}

	public void run() {	
		StringTokenizer st;
		String line;
		BufferedReader is;
		BufferedWriter os;
		while(isRunning) {
			System.out.println("Server is waiting to accept user...");
			try {
				socketOfServer = listener.accept();
				System.out.println("Accept a client!");

				is = new BufferedReader(new InputStreamReader(socketOfServer.getInputStream()));
				os = new BufferedWriter(new OutputStreamWriter(socketOfServer.getOutputStream()));
			} catch (IOException e) {
				System.out.println(e);
				continue;
			}

			while(true) {
				try {
					line = is.readLine();
					if (line.equals("QUIT")) {
						os.write("OK");
						os.newLine();
						os.flush();
						isRunning = false;
						break;
					}
					//System.out.println("Recieved : " +  line);
				
					st = new StringTokenizer(line);
				
					String cmd = st.nextToken();
					String tmp;
					switch(cmd)
					{
						case "-RDG":
						{
							tmp = st.nextToken();
							if(tmp.startsWith("0x")) tmp = tmp.substring(2);
							int chipid = Integer.parseInt( tmp, 16 );
												
							tmp = st.nextToken();
							if(tmp.startsWith("0x")) tmp = tmp.substring(2);
							int addr = Integer.parseInt(tmp, 16);
							System.out.println(String.format("I2C Write : %#02X %#02X", chipid, addr));		
						}
						break;
						default:
							System.out.println("Unknown Command : " + cmd);
						break;
					}		
				
					os.write("13");
					os.newLine();
					os.flush();
				} catch (IOException ie) {
					System.out.println(ie);
					ie.printStackTrace();
				} catch (NullPointerException ne) {
					//System.out.println("connet end : " + ne);
					//ne.printStackTrace();
					break;
				}
			}
		}
		System.out.println("Server End!");
	}
}
