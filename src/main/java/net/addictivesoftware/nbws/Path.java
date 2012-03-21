package net.addictivesoftware.nbws;

import org.jboss.netty.handler.codec.http.HttpMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Path {
    private HttpMethod httpMethod;
    private String path;
    private String executableMethod;
    private String regex;
    private List<String> params = new ArrayList<String>();

    private static String paramFindPattern = "\\{([a-zA-Z]+)\\}";

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
        //TODO
        return false;
    }
    
    
    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod method) {
        this.httpMethod = httpMethod;
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
        while (m.find()) {
            params.add(path.substring(m.start(), m.end()));
        }

        path.replaceAll(paramFindPattern, paramFindPattern);
    }
}
