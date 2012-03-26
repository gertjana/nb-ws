package net.addictivesoftware.nbws;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.handler.codec.http.HttpMethod;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpVersion;

import java.util.List;
import java.util.Map;
import java.util.Set;


public class MockHttpRequest implements HttpRequest {
    public HttpMethod getMethod() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setMethod(HttpMethod httpMethod) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getUri() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setUri(String s) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getHeader(String s) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<String> getHeaders(String s) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<Map.Entry<String, String>> getHeaders() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean containsHeader(String s) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Set<String> getHeaderNames() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public HttpVersion getProtocolVersion() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setProtocolVersion(HttpVersion httpVersion) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public ChannelBuffer getContent() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setContent(ChannelBuffer channelBuffer) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void addHeader(String s, Object o) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setHeader(String s, Object o) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setHeader(String s, Iterable<?> objects) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void removeHeader(String s) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void clearHeaders() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public long getContentLength() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public long getContentLength(long l) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isChunked() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setChunked(boolean b) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isKeepAlive() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
