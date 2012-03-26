import org.jboss.netty.handler.codec.http.HttpRequest;

public class Default {
    
    public static String handle(HttpRequest request) {
        return "Handling default (static) content for " + request.getUri();
    }  
    
    public static String refuse(HttpRequest request) {
        return "No Path defined for " + request.getMethod() + " " + request.getUri();
    }
}
