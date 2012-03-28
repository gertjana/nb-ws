import org.jboss.netty.handler.codec.http{HttpChunkAggregator,HttpRequestDecoder,HttpResponseEncoder}
import org.jboss.netty.handler.stream{ChunkedWriteHandler}
import org.jboss.netty.channel{ChannelPipeline,ChannelPipelineFactory,Channels{staticChannelPipeline=pipeline}}


shared class HttpServerPipelineFactory() satisfies ChannelPipelineFactory {
	shared actual ChannelPipeline pipeline = bottom;
	
	shared ChannelPipeline getPipeline() {
		ChannelPipeline pl = staticChannelPipeline();

        pipeline.addLast("decoder", HttpRequestDecoder());
        pipeline.addLast("aggregator", HttpChunkAggregator(65536));
        pipeline.addLast("encoder", HttpResponseEncoder());
        pipeline.addLast("chunkedWriter", ChunkedWriteHandler());

        //pipeline.addLast("handler", HttpServerHandler());
        return pl;		
	}
}