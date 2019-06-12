/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

/**
 *
 * @author MAX
 */
public class SFTP {
   
    private static final String user = "hola@allinmysolutionsla.com";
    private static final String host = "ftp.allinmysolutionsla.com";
    private static final Integer port = 22;
    private static final String pass = "maxeder";
 
    public static void main(String[] args) throws Exception{
        System.out.println("------------------- INICIO");
 
        JSch jsch = new JSch();
        Session session = jsch.getSession(user, host, port);
        UserInfo ui = new User(pass, null);
 
        session.setUserInfo(ui);
        session.setPassword(pass);
        session.connect();
 
        ChannelSftp ftp = (ChannelSftp)session.openChannel("sftp");
        ftp.connect();
 
        ftp.cd("/");
        System.out.println("Subiendo c:/el.txt ...");
        ftp.put("c:/el.txt", "el.txt");
 
        System.out.println("Archivos subidos.");
 
        ftp.exit();
        ftp.disconnect();
        session.disconnect();
 
        System.out.println("----------------- FIN");
    }

}
