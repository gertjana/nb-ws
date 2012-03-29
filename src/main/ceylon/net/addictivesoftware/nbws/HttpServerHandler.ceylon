import org.jboss.netty.channel{SimpleChannelUpstreamHandler, ChannelHandlerContext, 
						MessageEvent, Channel, ChannelFuture, ChannelFutureListener{CLOSE}}
import org.jboss.netty.handler.codec.http{HttpRequest, DefaultHttpResponse, HttpResponse}
import org.jboss.netty.handler.codec.http{HttpVersion{HTTP_1_1}, HttpResponseStatus}
import org.jboss.netty.buffer{ChannelBuffers{copiedBuffer}}
import java.nio.charset{Charset{charSetForName=forName}}
import org.jboss.netty.handler.codec.http{HttpHeaders{isKeepAlive}}
shared class HttpServerHandler() extends SimpleChannelUpstreamHandler() {
	

	shared actual void messageReceived(ChannelHandlerContext? ctx, MessageEvent? e) {
		if (exists e) {
			value request = e.message;
			if (is HttpRequest request) {
				HttpResponse response = DefaultHttpResponse(
											HttpVersion("HTTP", 1, 1, true), 
											HttpResponseStatus(200, "OK"));
		
				String responseText = "Retrieved: " + request.uri;
		        Channel ch = e.channel;
		        ch.write(response);	
		        
		        ChannelFuture writeFuture = ch.write(copiedBuffer(responseText, charSetForName("UTF-8")));
		        
				if (!isKeepAlive(request)) {
		            // Close the connection when the whole content is written out.
		            writeFuture.addListener(CLOSE);
		        }
		        
			}
		}
		
	}

}