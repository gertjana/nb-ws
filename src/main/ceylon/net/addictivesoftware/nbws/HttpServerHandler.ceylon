import org.jboss.netty.channel{SimpleChannelUpstreamHandler, ChannelHandlerContext, 
						MessageEvent, Channel, ChannelFuture, ChannelFutureProgressListener, ChannelFutureListener{close=\iCLOSE}}
import org.jboss.netty.handler.codec.http{HttpRequest, DefaultHttpResponse, HttpResponse}
import org.jboss.netty.handler.codec.http{HttpVersion{http11=\iHTTP_1_1}, HttpResponseStatus{ok=\iOK}}
import org.jboss.netty.buffer{ChannelBuffers{copiedBuffer}}
import org.jboss.netty.handler.codec.http{HttpHeaders{isKeepAlive}}
import java.util { EventListener }

shared class HttpServerHandler() extends SimpleChannelUpstreamHandler() {

	shared actual void messageReceived(ChannelHandlerContext? ctx, MessageEvent? e) {
		if (exists e) {
			if (is HttpRequest request=e.message) {
				HttpResponse response = DefaultHttpResponse(http11, ok);
		
				String responseText = "Retrieved: " + request.uri;
				
		        Channel ch = e.channel;
		        ch.write(response);	
		        
		        ChannelFuture writeFuture = ch.write(copiedBuffer(responseText, "UTF-8"));
		        
		        
		        //EventListener cfpl = ChannelFutureProgressListener();
		        
		        //writeFuture.addListener(cfpl);

		        
		        
				//if (!isKeepAlive(request)) {
		            // Close the connection when the whole content is written out.
		            writeFuture.addListener(close);
		        //}
			}
		}
	}
}