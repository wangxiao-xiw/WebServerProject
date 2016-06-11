/**this class belong to the package webServer.logs **/
package webserver.logs;

import java.io.*;
import java.util.Date;

public class logsError
{
/** This type must be used to errors which may be fatal to the application. **/
private final int FATAL_ERROR=1;
 /** BAD REQUEST This ERROR must be used when a client send a bad request generally it's a telnet prog. **/
private final int BADREQUEST_ERROR=2;
/** Unhandled exceptions should meanwhile be traced. **/
private final int UNHANDLED_ERROR=3;
 /** A non-critical error. **/
private final int ERROR=4;

public void LogEvent(String LogFilePath,String Msg,int ErrorNumber)throws IOException{

//logEvent Method used to save the error occured in the server into a log file

//creating a new file instance 
File logFile=new File(LogFilePath);
BufferedWriter Bw=null;
Date logDate=new Date();

//Today contain the actual date
String ToDay=logDate.toLocaleString();

if(!logFile.exists()){
//if the file doesn't exist we create a new file
FileOutputStream fp=new FileOutputStream(LogFilePath);
}

if(logFile.canWrite()){
//creating an instance of FileWriter with the option append=true 
FileWriter fw=new FileWriter(LogFilePath,true);

Bw=new BufferedWriter(fw);

} 

switch(ErrorNumber){

//According to the ErrorNumber,a specific message is writed 
	
case FATAL_ERROR:
				Bw.write("["+ToDay+"] "+"[FATAL ERROR]"+"  "+Msg+"\r\n");
				break;
case BADREQUEST_ERROR:
				Bw.write("["+ToDay+"] "+"[REQUESTE ERROR]"+" "+Msg+"\r\n");
				break;
case UNHANDLED_ERROR:
				Bw.write("["+ToDay+"] "+"[UNHANDLED ERROR]"+" "+Msg+"\r\n");
				break;
case ERROR:
				Bw.write("["+ToDay+"] "+"[ERROR]"+" "+Msg+"\r\n");
				break;
}

Bw.close(); //closing File log

}
}//End class log