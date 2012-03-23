import org.jboss.netty.handler.codec.http.HttpRequest;

public class UserApi {

    public static String getUser(HttpRequest request, String id) {
        return "getting user for id " + id;
    }
    
    public static String registerUser(HttpRequest request, String email, String name)  {
        return "registering " + name + " with email " + email;
    }
    
    public static String listUsers(HttpRequest request) {
        return "listing all users ...";
    }

    public static String newUser(HttpRequest request) {
        return "creating new user";
    }
    
    public static String updateUser(HttpRequest request, String id) {
        return "updating user with id " + id;
    }
    
    public static String deleteUser(HttpRequest request, String id) {
        return "deletin user with id " + id;
    }

}
