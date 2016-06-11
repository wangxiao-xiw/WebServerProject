/** class FileRead Belong to the webServer.main Package. **/
package webserver.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import webserver.logs.logsError;
import webserver.utilities.ServerFileRead;

public class WebServerProject implements Runnable {

	private String Server_root_path = ""; // server root path
	private String Server_Home = ""; // server home page
	private String Server_Icons_Path = ""; // contains the server Icons Path
	private Socket socketServer; // Socket that will receive or send data
	private String RequestFromClient = ""; // contains the request String ex:
											// GET /someFile HTTP/1.1
	private String File_Name = ""; // contains the requested File
	private String DIR_HTTP = ""; // contains the name of current directory
	private String NOM_HOTE = "";// Holding host name
	private int position1 = 0;
	private int position2 = 0;
	private BufferedReader in;
	private PrintWriter out;

	public static String files;

	// constructor WebServerProject
	public WebServerProject(Socket s, String Srootpath, String Shomepage, String Sicons, String Slog)
			throws UnknownHostException, IOException {

		try {
			// initializing the instance variables
			Server_root_path = Srootpath;
			Server_Home = Shomepage;
			Server_Icons_Path = Sicons;

			socketServer = s;
			// receiving the data from the client request
			in = new BufferedReader(new InputStreamReader(socketServer.getInputStream()));
			// String to be sent by the server
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socketServer.getOutputStream())), true);

			NOM_HOTE = socketServer.getInetAddress().getLocalHost().toString();
			// multiple threads will be invoked while each connection
			// established
			run(); // invoking method run() to execute the thread code

		} catch (IndexOutOfBoundsException ie) {
			// handling Error :Bad Request

			logsError log = new logsError();
			// call LogEvent Method From Class log
			log.LogEvent(Slog, "BAD REQUEST", 2);

		}

	}

	public String DecodeURL(String pth) {
		/*
		 * DecodeURL Method used to Decode URL's having space in their body ex:
		 * /File%20Having%20space.htm = /File Having space.htm
		 */
		String pathWithSpace = "";

		// testing if the request file contain %20
		if (pth.indexOf("%20") == -1) {

			return pth; // returning the same file name
		}

		else {
			StringTokenizer set = new StringTokenizer(pth, "%20");

			int cont = set.countTokens();

			if (!set.hasMoreTokens()) {

				return pth;
			} else {
				try {

					for (int j = 0; j < cont; j++) {

						/*
						 * we build a string replacing %20 with space as the
						 * original File
						 */

						pathWithSpace += set.nextToken() + " ";
					}
				} catch (NoSuchElementException ne) {
				}

			}
			return pathWithSpace.substring(0, pathWithSpace.length() - 1);
		}
	}

	// Synchronized run method for multithreaded functionality which will
	// provide
	public synchronized void run() {

		try {
			// initializing in & out with String received from client and String
			// that will be sent to it
			in = new BufferedReader(new InputStreamReader(socketServer.getInputStream()));
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socketServer.getOutputStream())), true);

		} catch (IOException ioe) {
		}

		try {

			// Getting The System File Separator ex : on windows \ ,on linux /
			String FILE_SEPERATOR = System.getProperty("file.separator");

			// receiving the request string sent by client
			RequestFromClient = in.readLine();

			System.out.println("URL:" + RequestFromClient);

			position1 = RequestFromClient.indexOf("/") + 1;
			position2 = RequestFromClient.indexOf("HTTP") - 1;

			files = RequestFromClient.substring(position1, position2).replace('/', '\\');

			System.out.println("String filenameto redirect:" + files);
			// file_Name contain the path of requested file
			File_Name = Server_root_path + FILE_SEPERATOR
					+ RequestFromClient.substring(position1, position2).replace('/', '\\');

			// Decoding the file URL which will replaces %20 with spaces
			File_Name = this.DecodeURL(File_Name);

			// Setting the Name of actual Directory
			DIR_HTTP = RequestFromClient.substring(position1, position2).trim();

			System.out.println("[CLIENT REQUEST]: " + RequestFromClient);
			System.out.println("[REQUESTED FILE]: " + File_Name);
			System.out
					.println("*********************************************" + "**********************************\n");

			File f = new File(File_Name);

			// creating instance for ServerFileRead...
			ServerFileRead fl = new ServerFileRead();

			// setting the directory name
			fl.setDirectory_Name(DIR_HTTP);
			// testing if the requested file is a directory
			if (f.isDirectory()) {

				if (new File(File_Name + Server_Home).exists())
					// if the welcome page exists, then we will send its content
					fl.fileReadFile(File_Name + Server_Home, NOM_HOTE, socketServer.getOutputStream(),
							RequestFromClient.substring(position1, position2));
				else
					fl.Listdir(File_Name, socketServer.getOutputStream(), NOM_HOTE, Server_Icons_Path);
				// otherwise we list the contents of the directory
			} else {
				// if the requested file is a file we read its content and we
				// send it to the client browser
				fl.fileReadFile(File_Name, NOM_HOTE, socketServer.getOutputStream(),
						RequestFromClient.substring(position1, position2));
			}

		} catch (IOException ioe) {

		} finally {
			try {
				socketServer.close(); // we always close the socket to free the
										// bandwidth
			} catch (IOException ioe) {
			}
		}
	}
}// End class WebServerProject