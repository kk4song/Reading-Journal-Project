import java.util.HashMap;
public class UserAndPass {

    HashMap<String,String> logininfo = new HashMap<String,String>(); // Hashmap to store variables.

    UserAndPass(){
        logininfo.put("User","java");
        logininfo.put("Kelly", "java"); // Adding HashMap.
    }

    public HashMap getLoginInfo(){ // Get the login information.
        return logininfo;
    }

}
