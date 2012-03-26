package net.addictivesoftware.nbws;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestRoutes {

    @Test
    public void testInitializeRoutes() throws IOException {
        Routes r = new Routes("/routes");

        Path path = r.getPathForUri("GET", "/user/4");
        Assert.assertNotNull(path);
        Assert.assertEquals("UserApi.getUser", path.getExecutableMethod());

        path = r.getPathForUri("GET", "/static/test");
        Assert.assertNotNull(path);
        Assert.assertEquals("Default.handle", path.getExecutableMethod());

        path = r.getPathForUri("PUT", "/some/path/");
        Assert.assertNotNull(path);
        Assert.assertEquals("Default.refuse", path.getExecutableMethod());

        path = r.getPathForUri("POST", "/user/theo@test.com/Theo Tester");
        Assert.assertNotNull(path);
        Assert.assertEquals("UserApi.registerUser", path.getExecutableMethod());

    }

    @Test
    public void testPattern() {
        String pattern = "^/user/([a-zA-Z0-9%.]+)$";
        String uri = "/user/test%40.com";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(uri);
        Assert.assertTrue(m.matches());
    }
    
    @Test
    public void testInvokeMethod() {
        Routes r = new Routes("/routes");
        Path path = r.getPathForUri("GET", "/user/4");
        String result = path.invoke(new MockHttpRequest(), String.class);
        System.out.println(result);
        
        path = r.getPathForUri("POST", "/user/theo.tester@test.com/Theo Tester");
        result = path.invoke(new MockHttpRequest(), String.class);
        System.out.println(result);
        
    }
    
    
    
}
