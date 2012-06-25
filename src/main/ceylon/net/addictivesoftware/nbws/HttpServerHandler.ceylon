import java.lang { JavaStringBuffer=StringBuffer }

import org.jboss.netty.buffer { ChannelBuffers { copiedBuffer }, ChannelBuffer }
import org.jboss.netty.channel { SimpleChannelUpstreamHandler, ChannelHandlerContext, MessageEvent, Channel, ChannelFuture, ChannelFutureListener { close=CLOSE } }
import org.jboss.netty.handler.codec.http { HttpRequest, DefaultHttpResponse, HttpResponse, HttpVersion { http11=HTTP_1_1 }, HttpResponseStatus { ok=OK } }

shared class HttpServerHandler() extends SimpleChannelUpstreamHandler() {

	shared actual void messageReceived(ChannelHandlerContext? ctx, MessageEvent? e) {
		if (exists e) {
			if (is HttpRequest request=e.message) {
				HttpResponse response = DefaultHttpResponse(http11, ok);
		
			
				String responseText = "Retrieved: " + request.uri;
				//ChannelBuffer cb = copiedBuffer(responseText, "UTF-8");
				
		        Channel ch = e.channel;
		        ch.write(response);	
		        
		        //ChannelFuture writeFuture = ch.write(cb);
		        		        
				//if (!isKeepAlive(request)) {
		            // Close the connection when the whole content is written out.
		            //writeFuture.addListener(close);
		        //}
			}
		}
	}
}