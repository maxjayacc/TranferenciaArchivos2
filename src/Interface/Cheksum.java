package Interface;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

/**
 *
 * @author visel
 */
public class Cheksum {


    public void test_doc() {

        FTPClient client = new FTPClient();
        String ftp = "192.99.37.129"; 
        String user = "gps_ftp@manuelfiestasweb.com";
        String password = "Ftp*2017";
        FileInputStream fis = null;
        BufferedInputStream in = null;
        try {
            client.connect(ftp);
            client.enterLocalActiveMode();
            client.login(user, password);
            File file = new File("C:/read.txt");
            //Obtiene el CRC del archivo antes de subir al FTP
            String localCRC = Integer.toHexString((int) FileUtils.checksumCRC32(file)).toUpperCase();
            System.out.println(localCRC);
            
//            client.setFileType(FTP.BINARY_FILE_TYPE, FTP.BINARY_FILE_TYPE);
//            client.setFileTransferMode(FTP.BINARY_FILE_TYPE);
//            
//            client.setFileType(FTP.BINARY_FILE_TYPE, FTP.BINARY_FILE_TYPE);
//            client.setFileTransferMode(FTP.BINARY_FILE_TYPE); 
//            
//            String ruta = "C:/read.txt";
//            System.out.println(ruta + "esta");
//            fis = new FileInputStream(ruta);
//            in = new BufferedInputStream(fis);
//            String filename = "read.txt";
//            boolean succes =  client.storeFile(filename, fis);
//            System.out.println("Subio archivo " + succes);
            
            //Se conecta al FTP            
            //Descargar Archivo
//            String archivoRemoto = "/read.txt";
//            File downloadFile = new File("C:/Archivos/read.txt");
//            OutputStream outputStream1 = new BufferedOutputStream(new FileOutputStream(downloadFile));
//            boolean success = client.retrieveFile(archivoRemoto, outputStream1);
//            outputStream1.close();
// 
//            if (success) {
//                System.out.println("File #1 has been downloaded successfully.");
//            }
            
            File remotofile = new File("C:/Archivos/read.txt");
            String remotoCRC = Integer.toHexString((int) FileUtils.checksumCRC32(remotofile)).toUpperCase();
            System.out.println("REMOTO "  + remotoCRC);
            System.out.println("LOCAL "  + localCRC);
            
            if(remotoCRC.equals(localCRC)) {
                System.out.println("NO SE MODIFICO LA INTEGRIDAD DEL DOCUMENTO ");
            }else {
                System.out.println("SE MODIFICO EL DOCUMENTO");
            }
                    
                    
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}