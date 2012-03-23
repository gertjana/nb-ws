import org.jboss.netty.handler.codec.http.HttpRequest;

public class UserApi {

    public static String getUser(HttpRequest request, String id) {
        return "getting user for id " + id;
    }
    
    public static String registerUser(HttpRequest request, String email, String name)  {
        return "registering " + name + " with email " + email;
    }

}
