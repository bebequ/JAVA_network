import java.io.*;
import java.net.*;

public class ClientDemo {
	public static void main(String[] args) {
		System.out.println("ClientDemo!!");

		final String serverHost = "localhost";

		Socket socketOfClient = null;
		BufferedWriter os = null;
		BufferedReader is = null;
		BufferedReader br = null;
		try {
			socketOfClient = new Socket(serverHost, 9999);
			os = new BufferedWriter(new OutputStreamWriter(socketOfClient.getOutputStream()));

			is = new BufferedReader(new InputStreamReader(socketOfClient.getInputStream()));

			br = new BufferedReader(new InputStreamReader(System.in));

		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + serverHost);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to  " + serverHost);
			return;
		}

		try {
			while(true) {
				String s = br.readLine();
				
				os.write(s);
				os.newLine();
				os.flush();
				
				String responseLine = is.readLine();
				System.out.println("Server : " + responseLine);
				
				if(responseLine.indexOf("OK") != -1) { 
					break;
				}
			}
			os.close();
			is.close();

			socketOfClient.close();
			/*	
			while((responseLine = is.readLine()) != null) {
				System.out.println("Server: "+  responseLine);
				if(responseLine.indexOf("OK") != -1) {
					break;
				}
			}
			*/
		} catch (UnknownHostException e) {
			System.out.println("Trying to connect to unknown host: " + e);
		} catch (IOException e) {
			System.err.println("IOException: " + e);
		}
		System.out.println("ClientDemo end");
	}
}
