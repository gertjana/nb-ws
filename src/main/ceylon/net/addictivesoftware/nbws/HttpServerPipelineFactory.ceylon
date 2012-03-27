import org.jboss.netty.handler.codec.http{HttpChunkAggregator,HttpRequestDecoder,HttpResponseEncoder}
import org.jboss.netty.handler.stream{ChunkedWriteHandler}
import org.jboss.netty.channel{ChannelPipeline,ChannelPipelineFactory,Channels}


class HttpServerPipelineFactory() satisfies ChannelPipelineFactory {
	shared actual ChannelPipeline pipeline = bottom;
	
	shared ChannelPipeline getPipeline() throws Exception {

		ChannelPipeline pipeline = Channels.pipeline();

        // Uncomment the following line if you want HTTPS
        //SSLEngine engine = SecureChatSslContextFactory.getServerContext().createSSLEngine();
        //engine.setUseClientMode(false);
        //pipeline.addLast("ssl", new SslHandler(engine));

        pipeline.addLast("decoder", HttpRequestDecoder());
        pipeline.addLast("aggregator", HttpChunkAggregator(65536));
        pipeline.addLast("encoder", HttpResponseEncoder());
        pipeline.addLast("chunkedWriter", ChunkedWriteHandler());

        //pipeline.addLast("handler", HttpServerHandler());
        return pipeline;		
	}

}