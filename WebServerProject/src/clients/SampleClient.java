/*
 * Class name contains in the package clients.
 */
package clients;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class SampleClient {

	public static void main(String[] args) {
		
		Socket socket;
		
		try {
			
			//Reading the hostname from the user
			System.out.println("Enter the hostname:");
			Scanner scannerHostName  = new Scanner(System.in);
			String hostname = scannerHostName.nextLine().trim();
			
			//Reading the port number from the user
			System.out.println("Enter the port number:");
			Scanner scannerPortNumber  = new Scanner(System.in);
			int port = Integer.parseInt(scannerHostName.nextLine().trim());
			
			
            InetAddress address = InetAddress.getByName(hostname);
           // socket = new Socket(address, port);
            
            System.out.println("Please enter the requested file to download");
            
            Scanner scanner = new Scanner(System.in);
			
            //Reading the requested file names from the 
			String input = scanner.nextLine();
			
			
			//Intializing the client socket
			Socket clientSocket = new Socket(hostname,port);
						
			//Send the message to the server
            OutputStream os = clientSocket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
			//Writing the data 
            bw.write(input);
            
            bw.flush();
            
            bw.close();
            
            System.out.println("Message sent to the server : "+input);
 
            //Get the return message from the server
            InputStream is = clientSocket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            //Receiving the message from the buffer
            String message = br.readLine();
            System.out.println("Message received from the server : " +message);
			
			
		} catch (IOException e) {
			System.out.println("IO Exception:"+e.getMessage());
		}
	}
}
