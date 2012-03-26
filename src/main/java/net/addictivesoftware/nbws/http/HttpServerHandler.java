package net.addictivesoftware.nbws.http;

import net.addictivesoftware.nbws.Path;
import net.addictivesoftware.nbws.Routes;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.*;
import org.jboss.netty.handler.codec.frame.TooLongFrameException;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.util.CharsetUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import static org.jboss.netty.handler.codec.http.HttpHeaders.*;
import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.*;
import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static org.jboss.netty.handler.codec.http.HttpMethod.*;
import static org.jboss.netty.handler.codec.http.HttpResponseStatus.*;
import static org.jboss.netty.handler.codec.http.HttpVersion.*;
import static org.jboss.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class HttpServerHandler extends SimpleChannelUpstreamHandler {
    private Routes routes;

    public HttpServerHandler() {
        routes = new Routes("/routes");
    }
    
    
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        System.out.println("receiving request");
        HttpRequest request = (HttpRequest) e.getMessage();
        
        final String path = sanitizeUri(request.getUri());


        Path p = routes.getPathForUri(request.getMethod().toString(), request.getUri());
        String responseText = p.invoke(request, String.class);

        HttpResponse response = new DefaultHttpResponse(HTTP_1_1, OK);

        setContentLength(response, responseText.length());
        Channel ch = e.getChannel();

        //Write the initial line and the header.
        ch.write(response);

        // Write the content.
        ChannelFuture writeFuture = ch.write(ChannelBuffers.copiedBuffer(responseText, CharsetUtil.UTF_8));

        writeFuture.addListener(new ChannelFutureProgressListener() {
            public void operationComplete(ChannelFuture future) {
                System.out.println("Future completed");
            }

            public void operationProgressed(
                ChannelFuture future, long amount, long current, long total) {
                System.out.printf("%s: %d / %d (+%d)%n", path, current, total, amount);
            }
        });

        // Decide whether to close the connection or not.
        if (!isKeepAlive(request)) {
            // Close the connection when the whole content is written out.
            writeFuture.addListener(ChannelFutureListener.CLOSE);
        }
    }

     @Override
     public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
             throws Exception {
         Channel ch = e.getChannel();
         Throwable cause = e.getCause();
         if (cause instanceof TooLongFrameException) {
             sendError(ctx, BAD_REQUEST);
             return;
         }

         cause.printStackTrace();
         if (ch.isConnected()) {
             sendError(ctx, INTERNAL_SERVER_ERROR);
         }
     }

     private String sanitizeUri(String uri) {
         // Decode the path.
         try {
             uri = URLDecoder.decode(uri, "UTF-8");
         } catch (UnsupportedEncodingException e) {
             try {
                 uri = URLDecoder.decode(uri, "ISO-8859-1");
             } catch (UnsupportedEncodingException e1) {
                 throw new Error();
             }
         }

         return uri;
     }

     private void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
         HttpResponse response = new DefaultHttpResponse(HTTP_1_1, status);
         response.setHeader(CONTENT_TYPE, "text/plain; charset=UTF-8");
         response.setContent(ChannelBuffers.copiedBuffer(
                 "Failure: " + status.toString() + "\r\n",
                 CharsetUtil.UTF_8));

         // Close the connection as soon as the error message is sent.
         ctx.getChannel().write(response).addListener(ChannelFutureListener.CLOSE);
     }
}