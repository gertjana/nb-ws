package net.addictivesoftware.nbws;

import org.jboss.netty.handler.codec.http.HttpMethod;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Path {
    private HttpMethod httpMethod;
    private String path;
    private String executableMethod;
    private Pattern regexPattern;
    private List<String> params = new ArrayList<String>();
    private List<String> paramValues = new ArrayList<String>();

    private static String paramFindPattern = "\\{([a-zA-Z]+)\\}";
    private static String paramValuePattern = "([a-zA-Z0-9]+)";
    private static String wildcardFindPattern = "\\*";
    private static String wildcardValuePattern = ".*";

    public Path(HttpMethod httpMethod, String path, String executableMethod) {
        setHttpMethod(httpMethod);
        setPath(path);
        setExecutableMethod(executableMethod);
    }

    public static Path parse(String line) {
        StringTokenizer st = new StringTokenizer(line, " \t");
        if (st.countTokens()==3) {
            return new Path(
                    new HttpMethod(st.nextToken()),
                    st.nextToken(),
                    st.nextToken()
                );
        }
        throw new IllegalArgumentException("not a valid route:" + line);
    }
    
    public boolean match(String method, String uri) {
        boolean result = false;
        if (method.equals("*")
                || getHttpMethod().toString().equals(method)
                || getHttpMethod().toString().equals("*")) {

            Matcher m = this.regexPattern.matcher(uri);
            return m.matches();
        }
        return result;
    }
    
    
    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod method) {
        this.httpMethod = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
        extractParamsAndRegEx(path);
    }

    public String getExecutableMethod() {
        return executableMethod;
    }

    public void setExecutableMethod(String executableMethod) {
        this.executableMethod = executableMethod;
    }
    
    private void extractParamsAndRegEx(String path) {
        Pattern p = Pattern.compile(paramFindPattern);
        Matcher m = p.matcher(path);
        path = "^" + m.replaceAll(paramValuePattern) + "$";

        p = Pattern.compile(wildcardFindPattern);
        m = p.matcher(path);
        path = m.replaceAll(wildcardValuePattern);
        
        regexPattern = Pattern.compile(path);
    }
    
    public <T> T invoke(T t) {
        try {
            Class clazz = Class.forName(executableMethod.split(".")[0]);
            Method method = clazz.getMethod(executableMethod.split(".")[1], Object.class);

            return (T)method.invoke(null, paramValues);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
