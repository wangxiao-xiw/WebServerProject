/** class FileRead Belong to the webServer.utilities Package. **/

package webserver.utilities; 

import java.util.*;
import java.lang.*;
import java.io.*;


public class ServerSettings {

private static String ICONS_PATH;
private static String SERVER_ROOT;
private static String WELCOME;
private static String PORT;
private static String LOG_PATH;

public void readConfigurationFile(String ConfFileName)throws IOException{

String str=""; /*String containing the content of each line of configuration File*/

File nets=new File(ConfFileName);
BufferedReader Bw=null;

if(!nets.exists()){
FileOutputStream fp=new FileOutputStream(ConfFileName);
}

if(nets.canRead()){

FileReader fw=new FileReader(nets);
Bw=new BufferedReader(fw);
} 
 
while((str=Bw.readLine())!=null)
{
	if(str.startsWith("Icons")){//Extract the value of ICONS
	
	StringTokenizer set=new StringTokenizer(str," ");
	
	int cont=set.countTokens();
	
	Vector vectDirectives=new Vector(cont);
	
	for(int i=0;i<cont;i++)
	{
		vectDirectives.addElement(set.nextToken());
	}
	ICONS_PATH=vectDirectives.lastElement().toString();
	}
else
	if(str.startsWith("ServerRoot")){//Extract the value of SERVER_ROOT
	
	StringTokenizer set=new StringTokenizer(str," ");
	
	int cont=set.countTokens();
	
	Vector vectDirectives=new Vector(cont);
	
	for(int i=0;i<cont;i++)
	{
		vectDirectives.addElement(set.nextToken());
	}
	SERVER_ROOT=vectDirectives.lastElement().toString();
	}	
else
	if(str.startsWith("Welcome")){//Extract the value of WELCOME
	
	StringTokenizer set=new StringTokenizer(str," ");
	
	int cont=set.countTokens();
	
	Vector vectDirectives=new Vector(cont);
	
	for(int i=0;i<cont;i++)
	{
		vectDirectives.addElement(set.nextToken());
	}
	WELCOME=vectDirectives.lastElement().toString();
	}
else
	if(str.startsWith("Port")){ //Extract the value of PORT
	
	StringTokenizer set=new StringTokenizer(str," ");
	
	int cont=set.countTokens();
	
	Vector vectDirectives=new Vector(cont);
	
	for(int i=0;i<cont;i++)
	{
		vectDirectives.addElement(set.nextToken());
	}
	PORT=vectDirectives.lastElement().toString();
	}
else
	if(str.startsWith("LogFile")){ //Extract the value of LOG_PATH
	
	StringTokenizer set=new StringTokenizer(str," ");
	
	int cont=set.countTokens();
	
	Vector vectDirectives=new Vector(cont);
	
	for(int i=0;i<cont;i++)
	{
		vectDirectives.addElement(set.nextToken());
	}
	LOG_PATH=vectDirectives.lastElement().toString();
	}
}

Bw.close(); //close the Configuration File 

}
		
	//return the value of ServerRoot directive
	public String getServerRoot(){
	return SERVER_ROOT;
	}
	//return the value of ServerPort directive
	public int getServerPort(){
	return Integer.parseInt(PORT);
	}
	//return the value of Welcome directive
	public String getServerHomePage(){
	return WELCOME;
	}
	//return the value of Icons directive
	public String getServerIcons(){
	return ICONS_PATH;
	}
	//return the value of LogFile directive
	public String getServerLog(){
	return LOG_PATH;
	}
}