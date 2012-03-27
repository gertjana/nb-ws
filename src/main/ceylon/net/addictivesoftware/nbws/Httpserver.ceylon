import org.jboss.netty.bootstrap {ServerBootstrap}
import org.jboss.netty.channel.socket.nio {NioServerSocketChannelFactory}
import java.net {JInetSocketAddress=InetSocketAddress}
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
						
			ServerBootstrap bootstrap = ServerBootstrap(NioServerSocketChannelFactory(
				newCachedThreadPool(), 
				newCachedThreadPool()));
			
			//bootstrap.setPipelineFactory(HttpServerPipelineFactory());
			bootstrap.bind(JInetSocketAddress(port));
			
//			ServerBootstrap bootstrap = new ServerBootstrap(
//            new NioServerSocketChannelFactory(
//                Executors.newCachedThreadPool(),
//                Executors.newCachedThreadPool()));
//	        // Set up the event pipeline factory.
//	        bootstrap.setPipelineFactory(new HttpServerPipelineFactory());
//	
//	        // Bind and start to accept incoming connections.
//	        bootstrap.bind(new InetSocketAddress(port));
	        			
    	} else {
    		print("specify port as the only argument");
    	}
	}
}