package Interface;


import com.jcraft.jsch.UserInfo;
 
public class User implements UserInfo {
 
    public String password;
    public String passPhrase;
 
    public User (String password, String passPhrase) {
        this.password = password;
        this.passPhrase = passPhrase;
    }
 
    @Override
    public String getPassphrase() {
        return passPhrase;
    }
 
    @Override
    public String getPassword() {
        return password;
    }
 
    @Override
    public boolean promptPassphrase(String arg0) {
        return true;
    }
 
    @Override
    public boolean promptPassword(String arg0) {
        return false;
    }
 
    @Override
    public boolean promptYesNo(String arg0) {
        return true;
    }
 
    @Override
    public void showMessage(String arg0) {
        System.out.println("SUserInfo.showMessage()");
    }
}
