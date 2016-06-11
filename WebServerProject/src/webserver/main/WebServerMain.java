/** class FileRead Belong to the webServer.main Package. **/
package webserver.main;

import webserver.utilities.ServerSettings;

import java.io.*;
import java.net.*;
import java.util.*;

public class WebServerMain {

	// Defining WebServerMain variables

	private static int SERVER_PORT;
	public static String SERVER_ROOT;
	private static String SERVER_HOMEPAGE;
	private static String SERVER_ICONS;
	private static String SERVER_LOG;

	public void getServerProperties() throws IOException {

		ServerSettings setting = new ServerSettings();

		// Getting the user home for setting the userhome of the system
		String USER_HOME = System.getProperty("user.home");

		// Getting the file separator from the system to set the path of the
		// variable.
		String fileSeparator = System.getProperty("file.separator");

		// For getting the absolute path of the document which contains details
		// for configuring the server
		String configFileName = USER_HOME + fileSeparator + "WebServerpath" + fileSeparator + "configuartion"
				+ fileSeparator + "webserverconfig.txt";

		System.out.println("Reading config file path " + configFileName);

		// calling Method readConfigurationFile From class ServerSetting to get
		// the port number and other details.....
		setting.readConfigurationFile(configFileName);

		/*
		 * Set value of Variables => will be the arguments of ServerWeb
		 * Constructor
		 */

		// getting port number
		this.SERVER_PORT = setting.getServerPort();
		// getting server root for accessing web pages
		this.SERVER_ROOT = setting.getServerRoot();
		// getting server home page
		this.SERVER_HOMEPAGE = setting.getServerHomePage();
		// getting server icons
		this.SERVER_ICONS = setting.getServerIcons();
		// getting log files path
		this.SERVER_LOG = setting.getServerLog();

		/*
		 * this.SERVER_PORT= 10181;
		 * this.SERVER_ROOT="C:/WebServerConfigs/htdocs";
		 * this.SERVER_HOMEPAGE="index.htm"; this.SERVER_ICONS="/icons/";
		 * this.SERVER_LOG="C:/WebServerConfigs/log/log.txt";
		 */

	}

	public void GeneralInformation() {
		// Method GeneralInformations collecting some System Informations

		// Getting The System information from which user is Connected
		String USER_HOME = System.getProperty("user.home");
		String USER_NAME = System.getProperty("user.name");

		// Getting The Operating System information
		String OS_NAME = System.getProperty("os.name");
		String OS_ARCH = System.getProperty("os.arch");
		String OS_VERS = System.getProperty("os.version");

		System.out.println("[PLATFORM]");
		System.out.println("|_NAME=" + OS_NAME + "");
		System.out.println("|_VERSION=" + OS_VERS + "");
		System.out.println("|_ARCHITECTURE=" + OS_ARCH + "");

		System.out.println("\n");

		System.out.println("\n");

		System.out.println("SERVER STARTED...\n");

	}

	public static void main(String[] args) throws IOException {
		// creating an instance of type WebServerMain class to get few
		// parameters
		WebServerMain assosiatewebServer = new WebServerMain();
		assosiatewebServer.getServerProperties(); // setting up the details from
													// WebpageConfigfiles
		assosiatewebServer.GeneralInformation(); // Show General Information
													// about OS & USER

		try {
			// creating a new ServerSocket Listening on SERVER_PORT
			ServerSocket serversocket = new ServerSocket(SERVER_PORT);
			System.out.println("Server Port established:" + SERVER_PORT);

			try {
				while (true) {
					// returning an established socket via the ServerSocket
					// accept method
					Socket socks = serversocket.accept();

					try {
						System.out.println("[REMOTE HOST]: " + socks.getInetAddress().toString());
						System.out.println("[LISTENING ON PORT]: " + socks.getPort());

						// calling the WebServerProject constructor and to run
						// the method
						new WebServerProject(socks, SERVER_ROOT, SERVER_HOMEPAGE, SERVER_ICONS, SERVER_LOG);

					} catch (IOException e) {
						socks.close();// closing the socket
					}
				}
			} finally {
				serversocket.close(); // closing the server socket
			}
		} catch (BindException B) {
			// handling exception generated if the server is already running
			System.out.println("SERVER Already Running");
			System.exit(0);
		}

	}
}// End class WebServerMain