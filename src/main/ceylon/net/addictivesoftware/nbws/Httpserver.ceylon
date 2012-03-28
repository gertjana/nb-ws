import org.jboss.netty.bootstrap {JServerBootstrap=ServerBootstrap, Bootstrap}
import org.jboss.netty.channel.socket.nio {NioServerSocketChannelFactory}
import java.net {InetSocketAddress}
import java.util.concurrent {Executors{newCachedThreadPool}}
import net.addictivesoftware.nbws { HttpServerPipelineFactory }


class Httpserver() {
	shared variable Integer port := 9000;
	
	shared void run() {
		String[] args = process.arguments;
		if ((nonempty args) && args.size == 1) {
			Integer p = parseInteger(args.first ? "-1") ? -1;
			if (p != -1) {
				port := p;
			}
						
			JServerBootstrap bootstrap = JServerBootstrap(
				NioServerSocketChannelFactory(
					newCachedThreadPool(), 
					newCachedThreadPool()));
			
			bootstrap.pipelineFactory := HttpServerPipelineFactory();
			bootstrap.bind(InetSocketAddress(port));
				        			
    	} else {
    		print("specify port as the only argument");
    	}
	}
}