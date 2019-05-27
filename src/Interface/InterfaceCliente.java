/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

/**
 *
 * @author MAX
 */
public class InterfaceCliente {
    String name;
    String file;
    public InterfaceCliente(){
   // this.name=name;
    //this.file=file;
    }
    public void Enviar(String name, String file, String protocolo, String puerto){
    FTPClient client = new FTPClient();
    JOptionPane.showMessageDialog(null, "Se enviará el archivo :\n"+name+"\n Protocolo:"+protocolo+"\n Puerto:"+puerto );
        // Datos para conectar al servidor FTP
        String ftp = "192.99.37.129"; 
        String user = "gps_ftp@manuelfiestasweb.com";
        String password = "Ftp*2017";

        try {
            // Conactando al servidor
            client.connect(ftp);
      
            // Logueado un usuario (true = pudo conectarse, false = no pudo
            // conectarse)
            boolean login = client.login(user, password);
            boolean storeFile = false;
            
            System.out.println("Autentificación: " + login);
            
            client.setFileType(FTP.BINARY_FILE_TYPE, FTP.BINARY_FILE_TYPE);
            client.setFileTransferMode(FTP.BINARY_FILE_TYPE);
          
         
            String filenamer = name;
            
            BufferedInputStream buffIn = null;
            InputStream fis = new FileInputStream(new File(file));
       
            buffIn = new BufferedInputStream(fis);
            
            //JOptionPane.showMessageDialog(null,"Puerto:"+ );
             
            client.enterLocalPassiveMode();
            // Guardando el archivo en el servidor
            storeFile = client.storeFile(filenamer, buffIn);
            System.out.println("Store: " + storeFile );
            System.out.println("protocolo: " +protocolo );
            System.out.println("puerto: " +puerto );
            
            if(storeFile)
            {
                
                JOptionPane.showMessageDialog(null, "Operación realizada correctamente");
            }
            else
            {
            
                JOptionPane.showMessageDialog(null, "Operación no realizada");
            }
            
            buffIn.close();
          
 
            // Cerrando sesión
            client.logout();
 
            // Desconectandose con el servidor
            client.disconnect();
 
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
            
        }
   }
    public void uploadFileToFTP(File file, boolean debug){
  
        String ftpServer = "192.99.37.129"; 
        String user = "gps_ftp@manuelfiestasweb.com";
        String password = "Ftp*2017";
        String location ="/";
        
        try {
  if(file.exists()){
   Socket socket=new Socket(ftpServer,21);
   BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
   BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
   bufferedWriter.write("USER "+user+"\r\n");
   bufferedWriter.flush();
   bufferedWriter.write("PASS "+password+"\r\n");
   bufferedWriter.flush();
   bufferedWriter.write("CWD "+location+"\r\n");
   bufferedWriter.flush();
   bufferedWriter.write("TYPE A\r\n");
   bufferedWriter.flush();
   bufferedWriter.write("PASV\r\n");
   bufferedWriter.flush();
   String response=null;
   while((response=bufferedReader.readLine())!=null){
    if(debug)
     System.out.println(response);
    if(response.startsWith("530")){
     System.err.println("Login aunthentication failed");
     break;
    }
    if(response.startsWith("227")){
         String address = null;  
                  int port = -1;  
                  int opening = response.indexOf('(');  
                  int closing = response.indexOf(')', opening + 1);  
                  if (closing > 0) {  
                      String dataLink = response.substring(opening + 1, closing);  
                      StringTokenizer tokenizer = new StringTokenizer(dataLink, ",");  
                      try {  
                          address = tokenizer.nextToken() + "." + tokenizer.nextToken() + "." + tokenizer.nextToken() + "." + tokenizer.nextToken();  
                          port = Integer.parseInt(tokenizer.nextToken()) * 256 + Integer.parseInt(tokenizer.nextToken());  
                      }  
                      catch (Exception e) {  
                          e.printStackTrace();
                      }
                      try{
                Socket transfer =new Socket(address,port);
             bufferedWriter.write("STOR "+file.getName()+"\r\n");
             bufferedWriter.flush();
                   response=bufferedReader.readLine();
                   if(debug)
                    System.out.println(response);
             if(response.startsWith("150")){
              FileInputStream fileInputStream=new FileInputStream(file);
              final int BUFFER_SIZE=1024;
              byte buffer[]=new byte[BUFFER_SIZE];
              int len=0,off=0;
              if(debug)
               System.out.println("uploading file...");
              while((len=fileInputStream.read(buffer))!=-1)
               transfer.getOutputStream().write(buffer, off, len);
              transfer.getOutputStream().flush();
              transfer.close();
              bufferedWriter.write("QUIT\r\n");
              bufferedWriter.flush();
              bufferedReader.close();
              socket.close();
              System.out.println("File sucessfully transferred!");
              break;
              }
                      }catch (Exception e) {
        System.err.println(e);
       }
                  }  
     }
    }
   }else{
    System.err.println(file+"no exist!");
   }
  } catch (MalformedURLException e) {
   e.printStackTrace();
  } catch (IOException e) {
   e.printStackTrace();
  }
 }

}