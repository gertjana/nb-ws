import org.jboss.netty.handler.codec.http{HttpChunkAggregator,HttpRequestDecoder,HttpResponseEncoder}
import org.jboss.netty.handler.stream{ChunkedWriteHandler}
import org.jboss.netty.channel{ChannelPipeline,ChannelPipelineFactory,Channels{staticChannelPipeline=pipeline}}

shared class HttpServerPipelineFactory() satisfies ChannelPipelineFactory {
	shared actual ChannelPipeline pipeline = staticChannelPipeline();
	
        pipeline.addLast("decoder", HttpRequestDecoder());
        pipeline.addLast("aggregator", HttpChunkAggregator(65536));
        pipeline.addLast("encoder", HttpResponseEncoder());
        pipeline.addLast("chunkedWriter", ChunkedWriteHandler());

        pipeline.addLast("handler", HttpServerHandler());

	shared ChannelPipeline getPipeline() {
		return pipeline;		
	}
}