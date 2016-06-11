/** class FileRead Belong to the webServer.utilities Package. **/
package webserver.utilities; 

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.*;

import webserver.main.WebServerMain;
import webserver.main.WebServerProject;


public class ServerFileRead 
{
    private static final int BUFFER_SIZE = 4096;
    
	private int nbRead;//Holds the number of bytes read
	private byte[] buffer;//Buffer contains data read from file.
	private String Directory_Name="";//Holds the current directory name. 
	private String MIME_TYPE=""; //MIME_TYPE is used to hold the Requested File mime type  
	private final int CGI_PROG=1;//This type must be used when the file is a Cgi Program.
	private final int PHP_PROG=2;//This type must be used when the file is a php file.
	private String mailto="vk423@mail.umkc.edu";//Hold the web server administrator e-mail  
	public static String saveFilePath;
	
	public static DataInputStream dIStream;
	
	public ServerFileRead(){
	nbRead=0;//initializing nbRead to zero
	buffer = new byte[1024];//The buffer can contain  1 KB 
	}
	
	/** Set the current directory name */
	public void setDirectory_Name(String Dir){
		
		Directory_Name=Dir;
	}
	/** Get the current directory name */
	public String getDirectory_Name(){
		return Directory_Name;
	}
	
	/**
	 * Method FileNotFoundMessage used to handle the ERROR 404 Not Found (occurs when a file or a directory
	 * doesn't exist under the web server
	 * @param file 
	 */
	
	void FileNotFoundMessage(OutputStream outStream,String HOST_NAME, File file)throws IOException{
		
		String proxyURL = "http://www.google.com"+"/"+WebServerProject.files;
	
		/*outStream.write(new String("<html>\r\n").getBytes());
		outStream.flush();
		outStream.write(new String("<Title>Redirecting to Another Server</Title>\r\n").getBytes());
		outStream.flush();
		outStream.write(new String("<body bgcolor='#999966'>\r\n").getBytes());
		outStream.flush();
		outStream.write(new String("<p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p>\r\n").getBytes());
		outStream.flush();
		outStream.write(new String("<div align='center'><center></p>\r\n").getBytes());
		outStream.flush();
		outStream.write(new String("Redirecting to another Server \r\n").getBytes());
		outStream.flush();
		outStream.write(new String("<table border='1' width='700' bgcolor='#FF0B0B'><TR>\r\n").getBytes());
		outStream.write(new String("<td align='center'><p align='center'><font color='#1E21DB' size='6'><strong>404 File Not Found</strong></font></p>"+
        "<p align='left'><font color='#FFFFFF'><strong>The Web Server cannot find the requested file or script"+
        " . check for your URL ,to be sure that your acces path is correct. Contact the Web Server administrator </font></p><p>&nbsp;</p></td></TR></Table>\r\n").getBytes());
		outStream.flush();
		outStream.write(new String("</center></div>\r\n").getBytes());
		outStream.flush();
		outStream.write(new String("</html>"+"\r\n").getBytes());
		outStream.flush();*/
		
		
		String dir = WebServerMain.SERVER_ROOT+"\\Download";
		String path = downloadFile(proxyURL, dir);
		
		FileInputStream fis = new FileInputStream(path);
		
		while((nbRead=fis.read(buffer,0,buffer.length))!=-1){
			
			outStream.write(buffer,0,nbRead);
			outStream.flush();
			
		}
		
		
	}
	
	/*
	 * 
	 * 
	 * 
	 */
	
	 public static String downloadFile(String fileURL, String saveDir)
	             {
		 
		 
	        URL url;
			try {
				url = new URL(fileURL);
			
	        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
	        int responseCode = httpConn.getResponseCode();
	 
	        // always check HTTP response code first
	        if (responseCode == HttpURLConnection.HTTP_OK) {
	            String fileName = "";
	            String disposition = httpConn.getHeaderField("Content-Disposition");
	            String contentType = httpConn.getContentType();
	            int contentLength = httpConn.getContentLength();
	 
	            if (disposition != null) {
	                // extracts file name from header field
	                int index = disposition.indexOf("filename=");
	                if (index > 0) {
	                    fileName = disposition.substring(index + 10,
	                            disposition.length() - 1);
	                }
	            } else {
	                // extracts file name from URL
	                fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1,
	                        fileURL.length());
	            }
	 
	            System.out.println("Content-Type = " + contentType);
	            System.out.println("Content-Disposition = " + disposition);
	            System.out.println("Content-Length = " + contentLength);
	            System.out.println("fileName = " + fileName);
	 
	            // opens input stream from the HTTP connection
	            InputStream inputStream = httpConn.getInputStream();
	             saveFilePath = saveDir + File.separator + fileName;
	             
	            // opens an output stream to save into file
	            FileOutputStream outputStream = new FileOutputStream(saveFilePath);
	 
	            int bytesRead = -1;
	            byte[] buffer = new byte[BUFFER_SIZE];
	            while ((bytesRead = inputStream.read(buffer)) != -1) {
	                outputStream.write(buffer, 0, bytesRead);
	            }
	 
	            outputStream.close();
	            inputStream.close();
	 
	            System.out.println("File downloaded");
	        } else {
	            System.out.println("No file to download. Server replied HTTP code: " + responseCode);
	        }
	        httpConn.disconnect();
			} catch (IOException e) {
				System.out.println("Message:"+e.getLocalizedMessage());
				
				System.out.println("Mess"+e.getMessage());
			}
	        
	        return saveFilePath;
	    }
	
	/*
	 * Redirect to another server
	 */
	public DataInputStream redirectToAnotherServer(String files) throws IOException{
		
		
		String proxyURL = "www.google.com/";
		
		String finalURL = proxyURL+files;
		
		URL url = new URL(finalURL);
		
		BufferedReader breader = new BufferedReader(new InputStreamReader(url.openStream()));
		
		String temp = "";
		
		while ( (temp = breader.readLine()) != null) {
			System.out.println();
			
		}
		
		return null;
		
	/*	Socket client = new Socket("192.168.0.14",10183);
		
		System.out.println(files.toString());
		
		//Send the message to the server
		DataOutputStream dos = new DataOutputStream(client.getOutputStream());
		DataInputStream dis = new DataInputStream(client.getInputStream());
        OutputStreamWriter osw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(osw);
		//Writing the data 
		
		String filename = files;
		
		String requestingFormat = "GET /"+filename+" HTTP/1.1";
		
        byte[] b = requestingFormat.getBytes();
        //byte[] b = files.getBytes("UTF-8");
        //byte[] b = "GET /public/index.html HTTP/1.1".getBytes("UTF-8");
        dos.write(files.getBytes());
        
        dos.flush();
        
        dos.close();
       
        return dis;*/
        
        
        
		
		
	}

	/**
	 * Sending The 200 OK Response to the client = The Request Was accepted
	 */
	
	public void HttpResponseOKMessage(OutputStream OUT_HROK,File FILE_LENGTH,String FILE_MIME)throws IOException {
		
	 /**Test the file extension, then get the appropriate mime type **/ 
		
		ServerMIME mim=new ServerMIME();
		MIME_TYPE=mim.getMimes(FILE_MIME);
		
		/** Sending a Http Response of type
		 * 
		 * HTTP/1.x 200 OK + crlf
		 * Date : xx/xx/xxxx + crlf
		 * Server : serverName + crlf
		 * content-length : X bytes + crlf
		 * content-type : mime type + crlf
		 * 
		 * */
		OUT_HROK.write(new String("HTTP/1.1 200 OK\r\n").getBytes());
		OUT_HROK.flush();
		OUT_HROK.write(new String("Date: "+new Date().toString()+"\r\n").getBytes());
		OUT_HROK.flush();
		OUT_HROK.write(new String("Server: Venu Babu Kollar\n").getBytes());
		OUT_HROK.flush();
		OUT_HROK.write(new String("Accept-Ranges: bytes\r\n").getBytes());
		OUT_HROK.flush();
		OUT_HROK.write(new String("content-length: "+String.valueOf(FILE_LENGTH.length())+"\r\n").getBytes());
		OUT_HROK.flush();
		OUT_HROK.write(new String("Content-Type: "+MIME_TYPE+"\r\n").getBytes());
		OUT_HROK.flush();
		OUT_HROK.write(new String("\r\n").getBytes());
		OUT_HROK.flush();
	}
	
	/* Method isCgiProg test if the file is a cgi program **/
	public boolean isCgiProg(String FILE_MIME){
		
		int FILE_EXTENS_POS=(int)FILE_MIME.lastIndexOf(".");
		
		if(FILE_MIME.substring(FILE_EXTENS_POS+1,FILE_MIME.length()).equals("exe"))
			
			return true;
		else
			return false;
		
	}
	/* Method isPHPProg test if the file is a PHP Script **/
	public boolean isPHPProg(String FILE_MIME){
		
		int FILE_EXTENS_POS=(int)FILE_MIME.lastIndexOf(".");
		
		if(FILE_MIME.substring(FILE_EXTENS_POS+1,FILE_MIME.length()).equals("php"))
			
			return true;
		else
			return false;
		
	}
	
	/*
	 * Processing the CGI
	 */
	
	public void ProcessCgi(String CGI_PHPFile,OutputStream ToBrowser,int CGI_PHP )throws IOException{
	
	
	
	
	Runtime r=Runtime.getRuntime(); //creating an object Runtime by calling the getRuntime Method 
	String cgiContent="";
	Process p=null;//win32 process initialized to null
	
	switch(CGI_PHP){
	
	case CGI_PROG:
					p=r.exec(CGI_PHPFile);
					break;
	case PHP_PROG:
					p=r.exec("C:\\php\\php.exe "+CGI_PHPFile);
					break;
	}
	/*we redirect the program STDOUT  to a bufferedReader */
	
	BufferedReader brcgi=new BufferedReader(new InputStreamReader(p.getInputStream()));
	
	while((cgiContent=brcgi.readLine())!=null){
		
	/** we eliminate useless data generated by the program */	
		
		if(cgiContent.startsWith("Status")||
		   cgiContent.startsWith("Content")||
		   cgiContent.startsWith("X-Powered-By"))
		{
		
		ToBrowser.write("".getBytes());
		ToBrowser.flush();
		
		}else
		{
		//we send the data redirected from the program STDOUT to the client	
		ToBrowser.write((cgiContent+"\r\n").getBytes());
		ToBrowser.flush();
		}
	}

	p.destroy();//we destroy the process to free memory
			
	}
	/**
	 * 
	 * method fileReadFile used to either read file, execute cgi program or execute a php script
	 *
	 */
	public void fileReadFile(String File_Name,String HOST_NAME,OutputStream priw,String File_Mime_Type)throws IOException {
		
		File file=new File(File_Name);
		
		if(file==null){
			System.out.println("The File doesn't exist");
		}
		else
		{
		if(!file.exists()){
			/** Requested File doesn't exist => calling the method FileNotFound  **/
			this.FileNotFoundMessage(priw,HOST_NAME,file);			
		}
		else
		{
		if(file.canRead()){
			
		/**Testing if the file is a CGI program **/
		if(isCgiProg(File_Name)){
			
			/** calling the method ProcessCgi with option CGI_PROG **/
			this.ProcessCgi(File_Name,priw,CGI_PROG);
		}
		/**Testing if the file is a PHP file **/
		else if(isPHPProg(File_Name)){
			
			/** calling the method ProcessCgi with option PHP_PROG **/
			this.ProcessCgi(File_Name,priw,PHP_PROG);
		}
		else
		{
		/** case of a simple file => read it's content sent to the client **/	
		FileInputStream fich=new FileInputStream(File_Name);
		
		/** calling the method HttpResponseOKMessage which will be used for  */
		this.HttpResponseOKMessage(priw,file,File_Mime_Type);
		
		/**
		 * while is not end of file, method read store number of bytes equivalent to the 
		 * buffer length in buffer variable then it's content is sent by method write 
		 */
		while((nbRead=fich.read(buffer,0,buffer.length))!=-1){
		
			priw.write(buffer,0,nbRead);
			priw.flush();
			
		}
		fich.close(); //File already read must be closed
		}
		}
		}
		}
		
	}
	
	public String DirectoryToList(String Fold){
	
		/** Method DirectoryToList returns the name of the actual directory that will be listed*/
		
		StringTokenizer set=new StringTokenizer(Fold,"/");
		String ActualDirectory="";
		int cont=set.countTokens();
		
		if(!set.hasMoreTokens()){
			return Fold;
		}else
		{
		Vector DirectoryParse=new Vector();
		DirectoryParse.setSize(cont);
		
		try{
	
			for(int j=0;j<cont;j++){
	
			DirectoryParse.addElement(set.nextToken());
			}
			
		ActualDirectory=DirectoryParse.lastElement().toString();
		}catch(NoSuchElementException nsee){}
		
		return ActualDirectory;
		}
	}
	
	/*
	 *  Listing the file names in the directories
	 */
	public void Listdir(String directory,OutputStream pr,String HOST_NAME_LINK,String Icons_Path)throws IOException  {
		
		File DIR_FILE=new File(directory);
			
		String File_Separ_String=System.getProperty("file.separator");
		String ActualDir=this.DirectoryToList(this.getDirectory_Name());
		
		if(DIR_FILE.isDirectory()){
			
		pr.write(new String("<html><head><h1>"+HOST_NAME_LINK+"- /"+this.getDirectory_Name()+"</h1></head><body><HR><BR>").getBytes());
		
		String[] File_List=new String[DIR_FILE.list().length];
		
		File_List=DIR_FILE.list();
		
		//send The Heading => Name Last Modified Size
		
		pr.write(new String("<Table border=0>").getBytes());
		pr.write(new String("<TR>").getBytes());
		pr.write(new String("<TD width='50'> </TD>").getBytes());
		pr.write(new String("<TD><strong>Name</strong></TD>").getBytes());
		pr.write(new String("<TD align='center'><strong>Last Modified</strong></TD>").getBytes());
		pr.write(new String("<TD align='center' width='50'><strong>Size</strong></TD>").getBytes());
		pr.write(new String("</TR>").getBytes());
		
		for(int i=0;i<File_List.length;i++){
			
			pr.write(new String("<TR>").getBytes());
			
			if(new File(directory+File_Separ_String+File_List[i]).isDirectory()){
				
				//List sub-directories founded into the current directory
										
				if(File_List[i].equals("icons")){
					
				/** if the actual directory is icons , we send blank cells*/
				
				pr.write(new String("<TD width='50'></TD>").getBytes());
				pr.write(new String("<TD></TD>").getBytes());
				pr.write(new String("<TD align='center' width='300'></TD>").getBytes());
				pr.write(new String("<TD align='center' width='50'></TD>").getBytes());
				}
				else{
					/** Listing directories*/
				pr.write(new String("<TD width='50'><img src="+Icons_Path+"dir.gif width='22' height='22'></TD>").getBytes());
				pr.write(new String("<TD><a href='"+ActualDir+'/'+File_List[i]+"'>"+File_List[i]+"</a>"+"</TD>").getBytes());
				pr.write(new String("<TD align='center' width='300'>"+new Date(new File(directory+File_Separ_String+File_List[i]).lastModified()).toString()+"</TD>").getBytes());
				pr.write(new String("<TD align='center' width='50'><font color=red><strong>&lt;DIR&gt;</strong></font></TD>").getBytes());
				
				}//fin test
				
				}
				
			else{
				
				//Listing files founded into the directory
				
				if(this.isCgiProg(directory+File_Separ_String+File_List[i]))
				{
					pr.write(new String("<TD width='50'><img src="+Icons_Path+"Filecgi.gif width='22' height='22'></TD>").getBytes());
				}
				else
				{
					pr.write(new String("<TD width='50'><img src="+Icons_Path+"File.gif width='22' height='22'></TD>").getBytes());
				}
										
				pr.write(new String("<TD><a href='"+ActualDir+'/'+File_List[i]+"'>"+File_List[i]+"</a>"+"</TD>").getBytes());
				pr.write(new String("<TD  align='center' width='300'>"+new Date(new File(directory+File_Separ_String+File_List[i]).lastModified()).toString()+"</TD>").getBytes());			
				pr.write(new String("<TD align='center' width='50'><font color=red><strong>"+String.valueOf(new File(directory+File_Separ_String+File_List[i]).length())+"</font></strong></TD>").getBytes());	
				}
				pr.write(new String("</TR>").getBytes());
			
			}
		}
		pr.write(new String("</Table>"+"<Hr><BR>AssoudiWebServer1.5 at "+HOST_NAME_LINK+" port 21002</body></html>").getBytes());
		
		
	}
}//End of class ServerFileRead
	