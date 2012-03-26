package net.addictivesoftware.nbws;

import org.jboss.netty.handler.codec.http.HttpMethod;
import org.jboss.netty.handler.codec.http.HttpRequest;

import javax.tools.JavaFileManager;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Path {
    private HttpMethod httpMethod;
    private String path;
    private String executableMethod;
    private Pattern regexPattern;
    private LinkedList<Object> paramValues = new LinkedList<Object>();

    private static String paramFindPattern = "\\{([a-zA-Z]+)\\}";
    private static String paramValuePattern = "([a-zA-Z0-9%@.]+)";
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
        if (getHttpMethod().toString().equals(method)
               || getHttpMethod().toString().equals("*")) {

            Matcher m = this.regexPattern.matcher(uri);
            if (m.matches()) {
                paramValues.clear();
                for (int i=1;i<=m.groupCount();i++) {
                    String param = m.group(i);
                    paramValues.add(param);
                }
                return true;
            }
        }
        return false;
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
    
    public <T> T invoke(T t, HttpRequest request) {
        try {
            Class clazz = Class.forName(executableMethod.split(".")[0]);
            Method method = clazz.getMethod(executableMethod.split(".")[1], Object.class);

            paramValues.addFirst(request);

            return (T)method.invoke(null, paramValues);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String toString() {
        return "Path {" + this.getHttpMethod() + ", " + this.getPath() + ", " + this.getExecutableMethod() + ", " + this.paramValues + "}";
    }
}
