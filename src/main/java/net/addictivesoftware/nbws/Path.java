package net.addictivesoftware.nbws;

import org.jboss.netty.handler.codec.http.HttpMethod;
import org.jboss.netty.handler.codec.http.HttpRequest;

import java.lang.reflect.Method;
import java.util.LinkedList;
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
    private static String paramValuePattern = "([a-zA-Z0-9@. ]+)";

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
    

    public <T> T invoke(HttpRequest request, Class<T> cls) {
        try {
            String[] executable = executableMethod.split("\\.", 2);
            Class[] paramTypes = new Class[paramValues.size()+1];

            paramTypes[0] = HttpRequest.class;
            int cnt=1;
            for (Object paramValue : paramValues) {
                paramTypes[cnt] = paramValue.getClass();
                cnt++;
            }
            
            paramValues.addFirst(request);


            Class clazz = Class.forName(executable[0]);
            Method method = clazz.getMethod(executable[1], paramTypes);
            return cls.cast(method.invoke(null, paramValues.toArray()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String toString() {
        return String.format("%s {%s, %s, %s, %s}",
                getClass().getSimpleName(),
                getHttpMethod(),
                getPath(),
                getExecutableMethod(),
                paramValues);
    }
}